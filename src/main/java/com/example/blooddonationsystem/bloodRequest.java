package com.example.blooddonationsystem;

import java.util.Date;

public class bloodRequest {
    public String bloodType;
    public Integer amount;

    public Integer id;
    public Date deadlineDate;


    public void setDate(Date date) {
        this.deadlineDate = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public Integer getId() {
        return id;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
