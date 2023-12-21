package com.example.blooddonationsystem;


public class allStorageRequests {

    private Integer quantity;
    private String bloodType;

    public String getBloodType(){
        return this.bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}