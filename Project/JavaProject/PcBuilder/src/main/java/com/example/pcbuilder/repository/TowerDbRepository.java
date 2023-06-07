package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Storage;
import com.example.pcbuilder.domain.Tower;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TowerDbRepository {
    private String url;

    private String username;

    private String password;

    public TowerDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Tower findOne(int id_entity){
        String sql = "SELECT * FROM tower where tower.id = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id_entity);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int noFans = resultSet.getInt("nofans");
                int includedFans = resultSet.getInt("includedfans");
                String type = resultSet.getString("type");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");

                Tower tower = new Tower(name, noFans, includedFans, type, color, rgb, price, imageUrl);
                tower.setId(id);

                return tower;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Tower> findAll() {
        List<Tower> towers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from tower where tower.id not in (select pc.id_tower from pc)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int noFans = resultSet.getInt("nofans");
                int includedFans = resultSet.getInt("includedfans");
                String type = resultSet.getString("type");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");

                Tower tower = new Tower(name, noFans, includedFans, type, color, rgb, price, imageUrl);
                tower.setId(id);
                towers.add(tower);
            }
            return towers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return towers;


    }

    public void delete(Tower entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM tower WHERE tower.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
