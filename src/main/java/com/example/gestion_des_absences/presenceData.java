package com.example.gestion_des_absences;

import javafx.scene.control.CheckBox;

import java.net.HttpCookie;

public class presenceData {
    private String name;
    private Integer apogee;
    private CheckBox checkPresence;
    private int idAbsence;
    private int nbtAbs;
    public presenceData(){
        super();
    }
    public presenceData(Integer apogee, String name) {
        super();
        this.name = name;
        this.apogee = apogee;
        this.checkPresence = new CheckBox();
        this.nbtAbs = nbtAbs;
    }

    public int getNbAbs() {
        return nbtAbs;
    }

    public void setNbAbs(int nbtAbs) {
        this.nbtAbs = nbtAbs;
    }

    public presenceData(int apogee, String name, int nbtAbs) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApogee() {
        return apogee;
    }
    public int getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(int idAbsence) {
        this.idAbsence = idAbsence;
    }

    public void setApogee(int apogee) {
        this.apogee = apogee;
    }

    public CheckBox getCheckPresence() {
        return checkPresence;
    }

    public void setCheckPresence(CheckBox checkPresence) {
        this.checkPresence = checkPresence;
    }


}
