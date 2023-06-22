package com.example.gestion_des_absences;

import javafx.scene.control.CheckBox;

import java.net.HttpCookie;

public class presenceData {
    private String name;
    private Integer apogee;
    private CheckBox checkPresence;
    private int idAbsence;
    public int nbtAbs;

    public presenceData(){
        super();
    }
    public presenceData(Integer apogee, String name) {
        super();
        this.name = name;
        this.apogee = apogee;
        this.checkPresence = new CheckBox();
    }

    //nb absence

    public int getNbtAbs() {
        return nbtAbs;
    }

    public void setNbtAbs(int nbtAbs) {
        this.nbtAbs = nbtAbs;
    }

    // name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //id absence
    public int getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(int idAbsence) {
        this.idAbsence = idAbsence;
    }
    // apogee
    public int getApogee() {
        return apogee;
    }
    public void setApogee(int apogee) {
        this.apogee = apogee;
    }

    //checkbox

    public CheckBox getCheckPresence() {
        return checkPresence;
    }

    public void setCheckPresence(CheckBox checkPresence) {
        this.checkPresence = checkPresence;
    }



}
