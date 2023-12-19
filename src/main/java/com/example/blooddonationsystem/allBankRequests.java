package com.example.blooddonationsystem;


public class allBankRequests {
    private Integer requestId;
    private Integer hospitalId;
    private Integer quantity;
    private String bloodType;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

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

    public Integer getHospitalId() {
        return this.hospitalId;
    }

    public void setHospitalId(Integer hospid) {
        this.hospitalId = hospid;
    }
}
