package com.example.vending;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Logic {
}

interface IProduct extends Comparable<IProduct> {
    String getName();
    double getPrice();
}

interface IDelivery {
    IProduct createProduct();
}

class Twix implements IProduct{
    private double price;
    private String name;

    public Twix(){
        this.price = 60;
        this.name = "Twix";
    }

    public double getPrice(){
        return this.price;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public int compareTo(IProduct product) {
        return getName().compareTo(product.getName());
    }
}

class Water implements IProduct{
    private double price;
    private String name;

    public Water(){
        this.price = 50;
        this.name = "Вода";
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public double getPrice(){
        return this.price;
    }

    @Override
    public int compareTo(IProduct product) {
        return getName().compareTo(product.getName());
    }
}

class Nuts implements IProduct{
    private double price;
    private String name;

    public Nuts(){
        this.price = 80;
        this.name = "Орешки";
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public double getPrice(){
        return this.price;
    }

    @Override
    public int compareTo(IProduct product){
        return getName().compareTo(product.getName());
    }
}

class RedBull implements IProduct{
    private double price;
    private String name;

    public RedBull(){
        this.price = 100;
        this.name = "RedBull";
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public double getPrice(){
        return this.price;
    }

    @Override
    public int compareTo(IProduct product){
        return getName().compareTo(product.getName());
    }
}

class Chips implements IProduct{
    private double price;
    private String name;

    public Chips(){
        this.price = 75;
        this.name = "Lays";
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public int compareTo(IProduct product) {
        return getName().compareTo(product.getName());
    }
}

class DeliveryTwix implements IDelivery{
    @Override
    public IProduct createProduct(){
        return new Twix();
    }
}

class DeliveryWater implements IDelivery{
    @Override
    public IProduct createProduct(){
        return new Water();
    }
}

class DeliveryNuts implements IDelivery{
    @Override
    public IProduct createProduct(){
        return new Nuts();
    }
}

class DeliveryRedBull implements IDelivery{
    @Override
    public IProduct createProduct(){
        return new RedBull();
    }
}

class DeliveryChips implements IDelivery{

    @Override
    public IProduct createProduct() {
        return new Chips();
    }
}

class Automat {
    Map<IProduct, Integer> snacks = new TreeMap<>();
    Map<IProduct, Integer> order = new TreeMap<>();
    double orderPrice;
    double profit;
    int id;

    public Automat(int id){
        this.id = id;
        this.orderPrice = 0;
        this.profit = 0;
    }

    void addProduct(IProduct product){
        int count = snacks.getOrDefault(product, 0);
        snacks.put(product, count + 1);
    }

    boolean addToOrder(IProduct product){
        int count = snacks.getOrDefault(product, 0);
        if (count > 0){
            snacks.put(product, count - 1);
            int orderCount = order.getOrDefault(product, 0);
            order.put(product, orderCount + 1);
            orderPrice += product.getPrice();
            return true;
        } else {
            return false;
        }
    }

    void payOrder(){
        this.profit += this.orderPrice;
        this.orderPrice = 0;
        this.order.clear();
    }

    @Override
    public String toString(){
        String res = "";

        TreeSet<IProduct> keys = new TreeSet<>(snacks.keySet());

        for (IProduct product : keys){
            res += product.getName() + " : " + snacks.get(product) + " штук \n";
        }

        return res;
    }
}