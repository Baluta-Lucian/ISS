package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Client;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;

public class ClientDbRepository {
    private String url;

    private String username;

    private String password;

    public ClientDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void save(Client entity) {
        if(entity == null)
            return;

        String sql = "INSERT INTO clienti(firstname, lastname, address, cnp, phone, username, password) values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getAddress());
            ps.setString(4, entity.getCNP());
            ps.setString(5, entity.getPhone());
            ps.setString(6, entity.getUsername());
            ps.setString(7, entity.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Client findOne(String uName) {
        if (uName == null)
            return null;
        String sql = "SELECT * FROM clienti where clienti.username = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, uName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                String cnp = resultSet.getString("cnp");
                String phone = resultSet.getString("phone");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Client client = new Client(firstName, lastName, address, cnp, phone, username, password);
                client.setId(id);


                return client;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Client findOneByCNP(String CNP) {
        if (CNP == null)
            return null;
        String sql = "SELECT * FROM clienti where clienti.cnp = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, CNP);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                String cnp = resultSet.getString("cnp");
                String phone = resultSet.getString("phone");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Client client = new Client(firstName, lastName, address, cnp, phone, username, password);
                client.setId(id);


                return client;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Client> findAll() {
        List<Client> clienti = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from clienti");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                String cnp = resultSet.getString("cnp");
                String phone = resultSet.getString("phone");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Client client = new Client(firstName, lastName, address, cnp, phone, username, password);
                client.setId(id);
                clienti.add(client);
            }
            return clienti;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienti;


    }


}
