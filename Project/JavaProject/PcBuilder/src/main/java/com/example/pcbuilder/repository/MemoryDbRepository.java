package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Cpu;
import com.example.pcbuilder.domain.CpuCooler;
import com.example.pcbuilder.domain.Memory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemoryDbRepository {

    private String url;

    private String username;

    private String password;

    public MemoryDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<Memory> findAll() {
        List<Memory> memories = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from memory where memory.id not in (select pc.id_memory from pc)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String size = resultSet.getString("size");
                String frequency = resultSet.getString("frequency");
                String modules = resultSet.getString("modules");
                String latency = resultSet.getString("latency");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");
                Memory memory = new Memory(name, type, size, frequency, modules, latency, color, rgb, price, imageUrl);
                memory.setId(id);
                memories.add(memory);
            }
            return memories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memories;


    }

    public void delete(Memory entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM memory WHERE memory.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Memory findOne(int id_entity){
        String sql = "SELECT * FROM memory where memory.id = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id_entity);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String size = resultSet.getString("size");
                String frequency = resultSet.getString("frequency");
                String modules = resultSet.getString("modules");
                String latency = resultSet.getString("latency");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");
                Memory memory = new Memory(name, type, size, frequency, modules, latency, color, rgb, price, imageUrl);
                memory.setId(id);

                return memory;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
