package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Cpu;
import com.example.pcbuilder.domain.CpuCooler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CpuCoolerDbRepository {

    private String url;

    private String username;

    private String password;

    public CpuCoolerDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<CpuCooler> findAll() {
        List<CpuCooler> cpuCoolers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from cpucooler where cpucooler.id not in (select pc.id_cpucooler from pc)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String socket = resultSet.getString("socket");
                String coolingType = resultSet.getString("coolingtype");
                int rpm = resultSet.getInt("rpm");
                String cfm = resultSet.getString("cfm");
                float db = resultSet.getFloat("db");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");
                CpuCooler cpuCooler = new CpuCooler(name, socket, coolingType, rpm, cfm, db, color, rgb, price, imageUrl);
                cpuCooler.setId(id);
                cpuCoolers.add(cpuCooler);
            }
            return cpuCoolers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cpuCoolers;


    }

    public void delete(CpuCooler entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM cpucooler WHERE cpucooler.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public CpuCooler findOne(int id_entity){
        String sql = "SELECT * FROM cpucooler where cpucooler.id = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id_entity);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String socket = resultSet.getString("socket");
                String coolingType = resultSet.getString("coolingtype");
                int rpm = resultSet.getInt("rpm");
                String cfm = resultSet.getString("cfm");
                float db = resultSet.getFloat("db");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");
                CpuCooler cpuCooler = new CpuCooler(name, socket, coolingType, rpm, cfm, db, color, rgb, price, imageUrl);
                cpuCooler.setId(id);

                return cpuCooler;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
