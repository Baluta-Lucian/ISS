package com.example.pcbuilder.service;

import com.example.pcbuilder.domain.*;
import com.example.pcbuilder.repository.*;
import com.example.pcbuilder.utils.events.*;
import com.example.pcbuilder.utils.observer.Observable;
import com.example.pcbuilder.utils.observer.Observer;
import com.google.common.base.Strings;

import java.util.*;

@SuppressWarnings("rawtypes")
public class Service implements Observable {

    private ClientDbRepository clientDbRepository;
    private EmployeeDbRepository employeeDbRepository;

    private OrderDbRepository orderDbRepository;

    private CpuDbRepository cpuDbRepository;

    private CpuCoolerDbRepository cpuCoolerDbRepository;

    private MemoryDbRepository memoryDbRepository;

    private MotherBoardDbRepository motherBoardDbRepository;

    private PowerSupplyDbRepository powerSupplyDbRepository;

    private StorageDbRepository storageDbRepository;

    private TowerDbRepository towerDbRepository;

    private VideoCardDbRepository videoCardDbRepository;

    private PcDbRepository pcDbRepository;


    private final Map<Class<? extends Event>, Observable> observers;

    @SuppressWarnings("rawtypes")
    public Service(ClientDbRepository clientDbRepository, EmployeeDbRepository employeeDbRepository, OrderDbRepository orderDbRepository, CpuDbRepository cpuDbRepository, CpuCoolerDbRepository cpuCoolerDbRepository, MemoryDbRepository memoryDbRepository, MotherBoardDbRepository motherBoardDbRepository, PowerSupplyDbRepository powerSupplyDbRepository, StorageDbRepository storageDbRepository, TowerDbRepository towerDbRepository, VideoCardDbRepository videoCardDbRepository, PcDbRepository pcDbRepository) {
        this.clientDbRepository = clientDbRepository;
        this.employeeDbRepository = employeeDbRepository;
        this.orderDbRepository = orderDbRepository;
        this.cpuDbRepository = cpuDbRepository;
        this.cpuCoolerDbRepository = cpuCoolerDbRepository;
        this.memoryDbRepository = memoryDbRepository;
        this.motherBoardDbRepository = motherBoardDbRepository;
        this.powerSupplyDbRepository = powerSupplyDbRepository;
        this.storageDbRepository = storageDbRepository;
        this.towerDbRepository = towerDbRepository;
        this.videoCardDbRepository = videoCardDbRepository;
        this.pcDbRepository = pcDbRepository;

        Map<Class<? extends Event>, Observable> temp  = new HashMap<>();
        //@TODO adauga Events
        temp.put(PlacedOrderEvent.class, new PlacedOrderObservable());
        temp.put(InProgressOrderEvent.class, new InProgressOrderObservable());
        temp.put(CancelOrderEvent.class, new CancelOrderObservable());
        temp.put(FinishedPcEvent.class, new FinishedPcObservable());
        observers = Collections.unmodifiableMap(temp);
    }



    public Employee findOneEmployee(String username){
        return employeeDbRepository.findOne(username);
    }

    public Client findOneClient(String username){
        return clientDbRepository.findOne(username);
    }

    public Client findOneClientByCnp(String CNP){
        return clientDbRepository.findOneByCNP(CNP);
    }

    public void addClient(Client client){
        clientDbRepository.save(client);
    }

    public List<Order> getAllPendingOrders(){
//        return orderDbRepository.findAllPending();
        List<Order> pendingOrders = new ArrayList<>();

        for(Order o : orderDbRepository.findAll()){
            if(o.getOrderStatus().equals(OrderStatus.Pending))
                pendingOrders.add(o);
        }
        return pendingOrders;
    }

    public List<Order> getAllClientOrders(Client client){
        List<Order> orders = new ArrayList<>();

        for(Order o : orderDbRepository.findAll()){
            if(o.getId_client() == client.getId())
                orders.add(o);
        }

        return orders;
    }

    public void updateOrderPendingToProgress(Order order){
        orderDbRepository.update(order);
        notifyObservers(new InProgressOrderEvent(order));
    }


    public Boolean existsOrder(String buildName){
        return orderDbRepository.findOne(buildName) != null;
    }


    public void saveOrder(Order order){
        //@TODO notify observers PlacedOrderEvent
        orderDbRepository.save(order);
        notifyObservers(new PlacedOrderEvent(order));
    }

    public List<Cpu> makeUniqueCpu(){
        List<Cpu> unique = new ArrayList<>();

        for(Cpu cpu : cpuDbRepository.findAll()){
            if(!unique.contains(cpu)){
                unique.add(cpu);
            }
        }
        return unique;
    }

    public List<CpuCooler> makeUniqueCpuCooler(){
        List<CpuCooler> unique = new ArrayList<>();

        for(CpuCooler cpuCooler: cpuCoolerDbRepository.findAll()){
            if(!unique.contains(cpuCooler)){
                unique.add(cpuCooler);
            }
        }
        return unique;
    }

    public List<Memory> makeUniqueMemory(){
        List<Memory> unique = new ArrayList<>();

        for(Memory entity : memoryDbRepository.findAll()){
            if(!unique.contains(entity)){
                unique.add(entity);
            }
        }
        return unique;
    }


    public List<MotherBoard> makeUniqueMotherBoard(){
        List<MotherBoard> unique = new ArrayList<>();

        for(MotherBoard entity : motherBoardDbRepository.findAll()){
            if(!unique.contains(entity)){
                unique.add(entity);
            }
        }
        return unique;
    }


    public List<PowerSupply> makeUniquePowerSupply(){
        List<PowerSupply> unique = new ArrayList<>();

        for(PowerSupply entity : powerSupplyDbRepository.findAll()){
            if(!unique.contains(entity)){
                unique.add(entity);
            }
        }
        return unique;
    }


    public List<Storage> makeUniqueStorage(){
        List<Storage> unique = new ArrayList<>();

        for(Storage entity : storageDbRepository.findAll()){
            if(!unique.contains(entity)){
                unique.add(entity);
            }
        }
        return unique;
    }


    public List<Tower> makeUniqueTower(){
        List<Tower> unique = new ArrayList<>();

        for(Tower entity : towerDbRepository.findAll()){
            if(!unique.contains(entity)){
                unique.add(entity);
            }
        }
        return unique;
    }

    public void savePC(PC pc){
        Order order = orderDbRepository.findOne(pc.getBuildName());
        pc.setId_client(order.getId_client());
        this.pcDbRepository.save(pc);
        order.setOrderStatus(OrderStatus.Finished);
        updateOrderPendingToProgress(order);
        notifyObservers(new FinishedPcEvent(pc, orderDbRepository.findOne(pc.getBuildName()).getId_client()));

    }

    public List<VideoCard> makeUniqueVideoCard(){
        List<VideoCard> unique = new ArrayList<>();

        for(VideoCard entity : videoCardDbRepository.findAll()){
            if(!unique.contains(entity)){
                unique.add(entity);
            }
        }
        return unique;
    }


    public void cancelOrder(Order order, String reason){

        if(Strings.isNullOrEmpty(reason)) {
            CancelOrderEvent cancelOrderEvent = new CancelOrderEvent(order.getBuildName(), reason);
            orderDbRepository.delete(order);
            notifyObservers(cancelOrderEvent);
        }
        else{
            CancelOrderEvent cancelOrderEvent = new CancelOrderEvent(order.getBuildName());
            orderDbRepository.delete(order);
            notifyObservers(cancelOrderEvent);
        }

    }


    @SuppressWarnings("unchecked")
    @Override
    public void addObserver(Observer e) {

        observers.get(PlacedOrderEvent.class).addObserver(e);
        observers.get(InProgressOrderEvent.class).addObserver(e);
        observers.get(CancelOrderEvent.class).addObserver(e);
        observers.get(FinishedPcEvent.class).addObserver(e);

    }

    @Override
    public void removeObserver(Observer e) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void notifyObservers(Event t) {
        if(observers.containsKey(t.getClass())){
            observers.get(t.getClass()).notifyObservers(t);
        }else {
            throw new IllegalArgumentException("Event not supported");
        }
    }

    public List<PC> getAllPc(int id) {
        List<PC> pcs = new ArrayList<>();
        for(PC pc : pcDbRepository.findAll()){
            if(pc.getId_client() == id)
                pcs.add(pc);
        }
        return pcs;
    }

    public void cancelOrderClient(String buildName) {
        Order order = orderDbRepository.findOne(buildName);
        PC pc = pcDbRepository.findOne(buildName);
        if(pc != null)
        {
            CancelOrderEvent cancelOrderEvent =new CancelOrderEvent(buildName, Boolean.TRUE);
            orderDbRepository.delete(order);
            pcDbRepository.delete(pc);
            notifyObservers(cancelOrderEvent);
        }
        else {
            CancelOrderEvent cancelOrderEvent = new CancelOrderEvent(buildName, Boolean.FALSE);
            orderDbRepository.delete(order);
            notifyObservers(cancelOrderEvent);
        }
    }

    public void acceptOrder(String buildName) {
        Order order = orderDbRepository.findOne(buildName);
        PC pc = pcDbRepository.findOne(buildName);
        order.setOrderStatus(OrderStatus.Accepted);
        order.setBudget(pc.getTotalPrice());
        pc.setStatus("Accepted");
        orderDbRepository.update(order);
        pcDbRepository.update(pc);
    }

    private class PlacedOrderObservable implements Observable<PlacedOrderEvent>{

        private List<Observer<PlacedOrderEvent>> observers = new ArrayList<>();
        @Override
        public void addObserver(Observer<PlacedOrderEvent> e) {
            observers.add(e);
        }

        @Override
        public void removeObserver(Observer<PlacedOrderEvent> e) {

        }

        @Override
        public void notifyObservers(PlacedOrderEvent t) {
            observers.stream().forEach(x -> x.update(t));
        }
    }

    private class InProgressOrderObservable implements Observable<InProgressOrderEvent>{

        private List<Observer<InProgressOrderEvent>> observers = new ArrayList<>();
        @Override
        public void addObserver(Observer<InProgressOrderEvent> e) {
            observers.add(e);
        }

        @Override
        public void removeObserver(Observer<InProgressOrderEvent> e) {

        }

        @Override
        public void notifyObservers(InProgressOrderEvent t) {
            observers.stream().forEach(x -> x.update(t));
        }
    }

    private class CancelOrderObservable implements Observable<CancelOrderEvent>{

        private List<Observer<CancelOrderEvent>> observers = new ArrayList<>();
        @Override
        public void addObserver(Observer<CancelOrderEvent> e) {
            observers.add(e);
        }

        @Override
        public void removeObserver(Observer<CancelOrderEvent> e) {

        }

        @Override
        public void notifyObservers(CancelOrderEvent t) {
            observers.stream().forEach(x -> x.update(t));
        }
    }

    private class FinishedPcObservable implements Observable<FinishedPcEvent>{

        private List<Observer<FinishedPcEvent>> observers = new ArrayList<>();
        @Override
        public void addObserver(Observer<FinishedPcEvent> e) {
            observers.add(e);
        }

        @Override
        public void removeObserver(Observer<FinishedPcEvent> e) {

        }

        @Override
        public void notifyObservers(FinishedPcEvent t) {
            observers.stream().forEach(x -> x.update(t));
        }
    }
}
