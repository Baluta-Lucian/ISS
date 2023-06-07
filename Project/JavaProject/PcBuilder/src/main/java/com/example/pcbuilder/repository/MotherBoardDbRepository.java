package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Memory;
import com.example.pcbuilder.domain.MotherBoard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotherBoardDbRepository {
    private String url;

    private String username;

    private String password;

    public MotherBoardDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public MotherBoard findOne(int id_entity){
        String sql = "SELECT * FROM motherboard where motherboard.id = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id_entity);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cpuSocket = resultSet.getString("cpusocket");
                String chipSet = resultSet.getString("chipset");
                String memoryType = resultSet.getString("memorytype");
                String maxMemorySize = resultSet.getString("maxmemorysize");
                String frequencyMemory = resultSet.getString("frequencymemory");
                String dualChannel = resultSet.getString("dualchannel");
                int noUsbPorts = resultSet.getInt("nousbports");
                int m2Sockets = resultSet.getInt("m2sockets");
                int noSataPorts = resultSet.getInt("nosataports");
                float price = resultSet.getFloat("price");
                String color = resultSet.getString("color");
                String imageUrl = resultSet.getString("imageurl");
                MotherBoard motherBoard = new MotherBoard(name, cpuSocket, chipSet, memoryType, maxMemorySize, frequencyMemory, dualChannel, noUsbPorts, m2Sockets, noSataPorts, color, price, imageUrl);
                motherBoard.setId(id);

                return motherBoard;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<MotherBoard> findAll() {
        List<MotherBoard> motherBoards = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from motherboard where motherboard.id not in (select pc.id_mboard from pc)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cpuSocket = resultSet.getString("cpusocket");
                String chipSet = resultSet.getString("chipset");
                String memoryType = resultSet.getString("memorytype");
                String maxMemorySize = resultSet.getString("maxmemorysize");
                String frequencyMemory = resultSet.getString("frequencymemory");
                String dualChannel = resultSet.getString("dualchannel");
                int noUsbPorts = resultSet.getInt("nousbports");
                int m2Sockets = resultSet.getInt("m2sockets");
                int noSataPorts = resultSet.getInt("nosataports");
                float price = resultSet.getFloat("price");
                String color = resultSet.getString("color");
                String imageUrl = resultSet.getString("imageurl");
                MotherBoard motherBoard = new MotherBoard(name, cpuSocket, chipSet, memoryType, maxMemorySize, frequencyMemory, dualChannel, noUsbPorts, m2Sockets, noSataPorts, color, price, imageUrl);
                motherBoard.setId(id);
                motherBoards.add(motherBoard);
            }
            return motherBoards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return motherBoards;


    }

    public void delete(MotherBoard entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM motherboard WHERE motherboard.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
