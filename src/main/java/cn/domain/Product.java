package cn.domain;

import java.util.Date;

public class Product {
    private int id;
    private String name;
    private double price;
    private Date produceTime;
    private String madeFactory;

    public Product() {
    }

    public Product(int id, String name, double price, Date produceTime, String madeFactory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.produceTime = produceTime;
        this.madeFactory = madeFactory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public String getMadeFactory() {
        return madeFactory;
    }

    public void setMadeFactory(String madeFactory) {
        this.madeFactory = madeFactory;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", produceTime=" + produceTime +
                ", madeFactory='" + madeFactory + '\'' +
                '}';
    }
}
