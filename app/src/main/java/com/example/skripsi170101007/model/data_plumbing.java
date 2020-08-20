package com.example.skripsi170101007.model;

public class data_plumbing {

    private String code;
    private String name;
    private String brand;
    private String capacity;
    private String location;
    private String maintenance;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    //variabel tambahan

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    //Membuat Konstuktor kosong untuk membaca data snapshot
    public data_plumbing(){
    }

    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    public data_plumbing(String code, String name, String brand, String capacity, String location, String maintenance) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.capacity = capacity;
        this.location = location;
        this.maintenance = maintenance;
    }
}


