package com.example.blooddonationsystem;


public class bloodRequestData {
    private Integer requestId;
    private Integer hospitalId;
    private Integer quantity;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
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
