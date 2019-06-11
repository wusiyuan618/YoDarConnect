package com.wusy.yodarconnect.bean;

import java.io.Serializable;

public class HostInfoBean implements Serializable {
    private String Command;
    private String Address;
    private String Model;
    private String iphoneId;
    private String NameId;
    private String Name;

    public String getCommand() {
        return Command;
    }

    public void setCommand(String command) {
        Command = command;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getIphoneId() {
        return iphoneId;
    }

    public void setIphoneId(String iphoneId) {
        this.iphoneId = iphoneId;
    }

    public String getNameId() {
        return NameId;
    }

    public void setNameId(String nameId) {
        NameId = nameId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
