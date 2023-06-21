package com.example.gestion_des_absences;

import javafx.scene.control.CheckBox;

import java.net.HttpCookie;

public class presenceData {
    private String name;
    private static Integer apogee;
    private static CheckBox checkPresence;
    private int idAbsence;
    public presenceData(){
        super();
    }
    public presenceData(Integer apogee, String name) {
        super();
        this.name = name;
        this.apogee = apogee;
        this.checkPresence = new CheckBox();
    }

    public presenceData(int apogee, String name, boolean isPresent) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getApogee() {
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

    public static CheckBox getCheckPresence() {
        return checkPresence;
    }

    public void setCheckPresence(CheckBox checkPresence) {
        this.checkPresence = checkPresence;
    }


}
