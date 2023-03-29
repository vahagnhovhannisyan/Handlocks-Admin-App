package com.example.handlocksadminapp.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    String name;
    String description;
    int price;
    String type;
    String brand;
    String img_url;
    String documentId;

    public ProductModel() {
    }

    public ProductModel(String name, String description, int price, String type, String brand, String img_url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.brand = brand;
        this.img_url = img_url;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
