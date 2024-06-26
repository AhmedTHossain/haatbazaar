package com.apptechbd.haatbazaar.models;

import java.time.Instant;

public class AdminAccount {
    private String id, name, owner, email, phone, address;
    private long created_on;

    public AdminAccount(String id, String name, String owner, String email, String phone, String address, long created_on) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.created_on = created_on;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCreated_on() {
        return created_on;
    }

    public void setCreated_on(long created_on) {
        this.created_on = created_on;
    }
}
