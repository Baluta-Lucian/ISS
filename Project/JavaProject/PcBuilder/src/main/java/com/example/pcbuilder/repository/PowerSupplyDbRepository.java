package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Memory;
import com.example.pcbuilder.domain.MotherBoard;
import com.example.pcbuilder.domain.PowerSupply;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PowerSupplyDbRepository {
    private String url;

    private String username;

    private String password;

    public PowerSupplyDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public PowerSupply findOne(int id_entity){
        String sql = "SELECT * FROM powersupply where powersupply.id = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id_entity);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String power = resultSet.getString("power");
                int nofans = resultSet.getInt("nofans");
                String modular = resultSet.getString("modular");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");
                PowerSupply powerSupply = new PowerSupply(name, power, nofans, modular, color, rgb, price, imageUrl);
                powerSupply.setId(id);

                return powerSupply;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<PowerSupply> findAll() {
        List<PowerSupply> powerSupplies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from powersupply where powersupply.id not in (select pc.id_powersupply from pc)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String power = resultSet.getString("power");
                int nofans = resultSet.getInt("nofans");
                String modular = resultSet.getString("modular");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");
                PowerSupply powerSupply = new PowerSupply(name, power, nofans, modular, color, rgb, price, imageUrl);
                powerSupply.setId(id);
                powerSupplies.add(powerSupply);
            }
            return powerSupplies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return powerSupplies;


    }

    public void delete(PowerSupply entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM powersupply WHERE powersupply.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
