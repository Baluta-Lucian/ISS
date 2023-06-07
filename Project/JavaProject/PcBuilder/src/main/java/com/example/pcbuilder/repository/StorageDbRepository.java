package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.PowerSupply;
import com.example.pcbuilder.domain.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StorageDbRepository {
    private String url;

    private String username;

    private String password;

    public StorageDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Storage findOne(int id_entity){
        String sql = "SELECT * FROM storage where storage.id = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id_entity);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String size = resultSet.getString("size");
                String interf = resultSet.getString("interface");
                String readSpeed = resultSet.getString("readspeed");
                String writeSpeed = resultSet.getString("writespeed");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");

                Storage storage = new Storage(name, type, size, interf, readSpeed, writeSpeed, color, rgb, price, imageUrl);
                storage.setId(id);

                return storage;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Storage> findAll() {
        List<Storage> storages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from storage where storage.id not in (select pc.id_storage from pc)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String size = resultSet.getString("size");
                String interf = resultSet.getString("interface");
                String readSpeed = resultSet.getString("readspeed");
                String writeSpeed = resultSet.getString("writespeed");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");

                Storage storage = new Storage(name, type, size, interf, readSpeed, writeSpeed, color, rgb, price, imageUrl);
                storage.setId(id);
                storages.add(storage);
            }
            return storages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storages;


    }

    public void delete(Storage entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM storage WHERE storage.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
