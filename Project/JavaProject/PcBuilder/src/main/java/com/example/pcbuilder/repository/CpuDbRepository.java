package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Cpu;
import com.example.pcbuilder.domain.Employee;
import com.example.pcbuilder.domain.Order;
import com.example.pcbuilder.domain.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.pcbuilder.domain.OrderStatus.valueOf;

public class CpuDbRepository {

    private String url;

    private String username;

    private String password;


    public CpuDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Cpu findOne(int id_entity){
        String sql = "SELECT * FROM cpu where cpu.id = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id_entity);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String frequency = resultSet.getString("frequency");
                String socket = resultSet.getString("socket");
                String cacheMemory = resultSet.getString("cachememory");
                int threads = resultSet.getInt("threads");
                int cores = resultSet.getInt("cores");
                int power = resultSet.getInt("power");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");
                Cpu cpu = new Cpu(name, frequency, socket, cacheMemory, threads, cores, power, color, rgb, price, imageUrl);
                cpu.setId(id);


                return cpu;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public List<Cpu> findAll() {
        List<Cpu> cpus = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from cpu where cpu.id not in (select pc.id_cpu from pc)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String frequency = resultSet.getString("frequency");
                String socket = resultSet.getString("socket");
                String cacheMemory = resultSet.getString("cachememory");
                int threads = resultSet.getInt("threads");
                int cores = resultSet.getInt("cores");
                int power = resultSet.getInt("power");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");
                Cpu cpu = new Cpu(name, frequency, socket, cacheMemory, threads, cores, power, color, rgb, price, imageUrl);
                cpu.setId(id);
                cpus.add(cpu);
            }
            return cpus;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cpus;


    }

    public void delete(Cpu entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM cpu WHERE cpu.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }








}
