package com.example.blooddonationsystem;


public class allPendingRequests {
    private Integer requestId;
    private String bloodType;
    private Integer quantity;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getAmount() {
        return quantity;
    }

    public void setAmount(Integer amount) {
        this.quantity = amount;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
