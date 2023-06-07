package com.example.csstester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CpuDbRepository {

    private String url = "jdbc:postgresql://localhost:5432/pcbuilder";
    private String username = "postgres";
    private String password = "CripiFace2";


    public CpuDbRepository() {

    }

    public List<Cpu> findAll() {
        List<Cpu> cpus = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from cpu");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println("Sunt in resultNext");
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

    public List<Cpu> makeUnique(){
        List<Cpu> unique = new ArrayList<>();

        for(Cpu cpu : findAll()){
            if(!unique.contains(cpu)){
                unique.add(cpu);
            }
        }
        return unique;
    }

}
