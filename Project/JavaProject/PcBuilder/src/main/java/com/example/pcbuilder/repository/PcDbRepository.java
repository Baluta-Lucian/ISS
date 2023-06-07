package com.example.pcbuilder.repository;

import com.example.pcbuilder.domain.Cpu;
import com.example.pcbuilder.domain.Order;
import com.example.pcbuilder.domain.OrderStatus;
import com.example.pcbuilder.domain.PC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.pcbuilder.domain.OrderStatus.valueOf;

public class PcDbRepository {

    private String url;

    private String username;

    private String password;

    private CpuDbRepository cpuDbRepository;
    private CpuCoolerDbRepository cpuCoolerDbRepository;
    private MemoryDbRepository memoryDbRepository;
    private MotherBoardDbRepository motherBoardDbRepository;
    private PowerSupplyDbRepository powerSupplyDbRepository;
    private StorageDbRepository storageDbRepository;
    private TowerDbRepository towerDbRepository;
    private VideoCardDbRepository videoCardDbRepository;

    public PcDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public PcDbRepository(String url, String username, String password, CpuDbRepository cpuDbRepository, CpuCoolerDbRepository cpuCoolerDbRepository, MemoryDbRepository memoryDbRepository, MotherBoardDbRepository motherBoardDbRepository, PowerSupplyDbRepository powerSupplyDbRepository, StorageDbRepository storageDbRepository, TowerDbRepository towerDbRepository, VideoCardDbRepository videoCardDbRepository) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.cpuDbRepository = cpuDbRepository;
        this.cpuCoolerDbRepository = cpuCoolerDbRepository;
        this.memoryDbRepository = memoryDbRepository;
        this.motherBoardDbRepository = motherBoardDbRepository;
        this.powerSupplyDbRepository = powerSupplyDbRepository;
        this.storageDbRepository = storageDbRepository;
        this.towerDbRepository = towerDbRepository;
        this.videoCardDbRepository = videoCardDbRepository;
    }

    public void save(PC entity) {
        if(entity == null)
            return;

        String sql = "INSERT INTO pc(id_cpu, id_cpucooler, id_memory, id_mboard, id_powersupply, id_storage, id_tower, id_videocard, totalprice, buildname, id_client, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getCpu().getId());
            ps.setInt(2, entity.getCpuCooler().getId());
            ps.setInt(3, entity.getMemory().getId());
            ps.setInt(4, entity.getMotherBoard().getId());
            ps.setInt(5, entity.getPowerSupply().getId());
            ps.setInt(6, entity.getStorage().getId());
            ps.setInt(7, entity.getTower().getId());
            ps.setInt(8, entity.getVideoCard().getId());
            ps.setFloat(9, entity.getTotalPrice());
            ps.setString(10, entity.getBuildName());
            ps.setInt(11, entity.getId_client());
            ps.setString(12, entity.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<PC> findAll() {
        List<PC> pcs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from pc");
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Sunt in resultNext");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_cpu = resultSet.getInt("id_cpu");
                int id_cpucooler = resultSet.getInt("id_cpucooler");
                int id_memory = resultSet.getInt("id_memory");
                int id_mboard = resultSet.getInt("id_mboard");
                int id_powersupply = resultSet.getInt("id_powersupply");
                int id_storage = resultSet.getInt("id_storage");
                int id_tower = resultSet.getInt("id_tower");
                int id_videocard = resultSet.getInt("id_videocard");
                float totalprice = resultSet.getFloat("totalprice");
                String buildName = resultSet.getString("buildname");
                int id_client = resultSet.getInt("id_client");
                String status = resultSet.getString("status");

                PC pc = new PC();
                pc.setBuildName(buildName);
                pc.setCpu(cpuDbRepository.findOne(id_cpu));
                pc.setCpuCooler(cpuCoolerDbRepository.findOne(id_cpucooler));
                pc.setMemory(memoryDbRepository.findOne(id_memory));
                pc.setMotherBoard(motherBoardDbRepository.findOne(id_mboard));
                pc.setPowerSupply(powerSupplyDbRepository.findOne(id_powersupply));
                pc.setStorage(storageDbRepository.findOne(id_storage));
                pc.setTower(towerDbRepository.findOne(id_tower));
                pc.setVideoCard(videoCardDbRepository.findOne(id_videocard));
                pc.setId_client(id_client);
                pc.setId(id);
                pc.setStatus(status);

                pcs.add(pc);
            }
            return pcs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pcs;


    }


    public void delete(PC entity) {
        if(entity == null)
            return;

        String sql = "DELETE FROM pc WHERE pc.id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public PC findOne(String buildName){
        if (buildName == null)
            return null;
        String sql = "SELECT * FROM pc where pc.buildname = ?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, buildName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                int id_cpu = resultSet.getInt("id_cpu");
                int id_cpucooler = resultSet.getInt("id_cpucooler");
                int id_memory = resultSet.getInt("id_memory");
                int id_mboard = resultSet.getInt("id_mboard");
                int id_powersupply = resultSet.getInt("id_powersupply");
                int id_storage = resultSet.getInt("id_storage");
                int id_tower = resultSet.getInt("id_tower");
                int id_videocard = resultSet.getInt("id_videocard");
                float totalprice = resultSet.getFloat("totalprice");
                String buildNm = resultSet.getString("buildname");
                int id_client = resultSet.getInt("id_client");
                String status = resultSet.getString("status");

                PC pc = new PC();
                pc.setBuildName(buildNm);
                pc.setCpu(cpuDbRepository.findOne(id_cpu));
                pc.setCpuCooler(cpuCoolerDbRepository.findOne(id_cpucooler));
                pc.setMemory(memoryDbRepository.findOne(id_memory));
                pc.setMotherBoard(motherBoardDbRepository.findOne(id_mboard));
                pc.setPowerSupply(powerSupplyDbRepository.findOne(id_powersupply));
                pc.setStorage(storageDbRepository.findOne(id_storage));
                pc.setTower(towerDbRepository.findOne(id_tower));
                pc.setVideoCard(videoCardDbRepository.findOne(id_videocard));
                pc.setId_client(id_client);
                pc.setId(id);
                pc.setStatus(status);


                return pc;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void update(PC entity) {
        if(entity == null)
            return;

        String sql = "UPDATE pc SET status = ? where pc.buildname = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getStatus());
            ps.setString(2, entity.getBuildName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
