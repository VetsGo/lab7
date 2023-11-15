package org.example;

import java.util.Comparator;

public class Product implements Comparable<Product> {
    private Integer id;
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Comparator<Product> nameComparator() {
        return Comparator.comparing(product -> product.name);
    }

    public static Comparator<Product> stockComparator() {
        return Comparator.comparingInt(product -> product.stock);
    }

    public static Comparator<Product> priceComparator() {
        return Comparator.comparingDouble(product -> product.price);
    }

    @Override
    public int compareTo(Product other) {
        return Double.compare(other.price, this.price);
    }

    @Override
    public String toString() {
        return "Product " + id + " [" +
                "name: " + name +
                ", price=" + price +
                ", stock=" + stock +
                ']';
    }
    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}