package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Client;
import com.example.pcbuilder.domain.Employee;
import com.example.pcbuilder.domain.Order;
import com.example.pcbuilder.domain.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.pcbuilder.domain.OrderStatus.*;

public class OrderDbRepository {
    private String url;

    private String username;

    private String password;

    public OrderDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void save(Order entity) {
        if(entity == null)
            return;

        String sql = "INSERT INTO orders(budget, description, buildname, status, id_client) values (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setFloat(1, entity.getBudget());
            ps.setString(2, entity.getDescription());
            ps.setString(3, entity.getBuildName());
            ps.setString(4, entity.getOrderStatus().toString());
            ps.setInt(5, entity.getId_client());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from orders");
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Sunt in resultNext");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                float budget = resultSet.getFloat("budget");
                String description = resultSet.getString("description");
                String buildName = resultSet.getString("buildname");
                OrderStatus orderStatus = valueOf(resultSet.getString("status"));
                int id_client = resultSet.getInt("id_client");
                Order order = new Order(budget, description, buildName, orderStatus, id_client);
                order.setId(id);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;


    }

    public Order findOne(String buildName){
        if (buildName == null)
            return null;
        String sql = "SELECT * FROM orders where orders.buildname = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, buildName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                float budget = resultSet.getFloat("budget");
                String description = resultSet.getString("description");
                String buildname = resultSet.getString("buildname");
                OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("status"));
                int id_client = resultSet.getInt("id_client");
                Order order = new Order(budget, description, buildname, orderStatus, id_client);
                order.setId(id);


                return order;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }



    public void update(Order entity) {
        if(entity == null)
            return;

        String sql = "UPDATE orders SET status = ?, budget = ? where orders.buildname = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getOrderStatus().toString());
            ps.setFloat(2, entity.getBudget());
            ps.setString(3, entity.getBuildName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public  void delete(Order entity){
        if(entity == null)
            return;

        String sql = "DELETE FROM orders WHERE orders.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
