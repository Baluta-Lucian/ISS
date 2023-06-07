package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Tower;
import com.example.pcbuilder.domain.VideoCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoCardDbRepository {
    private String url;

    private String username;

    private String password;

    public VideoCardDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public VideoCard findOne(int id_entity){
        String sql = "SELECT * FROM videocard where videocard.id = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id_entity);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String slot = resultSet.getString("slot");
                String chipSet = resultSet.getString("chipset");
                String maxResolution = resultSet.getString("maxresolution");
                String size = resultSet.getString("size");
                String type = resultSet.getString("type");
                String frequency = resultSet.getString("frequency");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");

                VideoCard videoCard = new VideoCard(name, slot, chipSet, maxResolution, size, type, frequency, color, rgb, price, imageUrl);
                videoCard.setId(id);

                return videoCard;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<VideoCard> findAll() {
        List<VideoCard> videoCards = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from videocard where videocard.id not in (select pc.id_videocard from pc)");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String slot = resultSet.getString("slot");
                String chipSet = resultSet.getString("chipset");
                String maxResolution = resultSet.getString("maxresolution");
                String size = resultSet.getString("size");
                String type = resultSet.getString("type");
                String frequency = resultSet.getString("frequency");
                String color = resultSet.getString("color");
                Boolean rgb = resultSet.getBoolean("rgb");
                float price = resultSet.getFloat("price");
                String imageUrl = resultSet.getString("imageurl");

                VideoCard videoCard = new VideoCard(name, slot, chipSet, maxResolution, size, type, frequency, color, rgb, price, imageUrl);
                videoCard.setId(id);
                videoCards.add(videoCard);
            }
            return videoCards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videoCards;


    }

    public void delete(VideoCard entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM videocard WHERE videocard.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
