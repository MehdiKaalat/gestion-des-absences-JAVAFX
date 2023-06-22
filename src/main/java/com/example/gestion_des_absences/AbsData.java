package com.example.gestion_des_absences;

import javafx.scene.control.CheckBox;

public class AbsData {
    private int apogee;
    private String name;

    public CheckBox getCheckPresence() {
        return checkPresence;
    }

    public void setCheckPresence(CheckBox checkPresence) {
        this.checkPresence = checkPresence;
    }

    private CheckBox checkPresence;
    private int absenceCount;

    public AbsData(int apogee, String name, int absenceCount) {
        this.apogee = apogee;
        this.name = name;
        this.checkPresence = new CheckBox();
        this.absenceCount = absenceCount;
    }

    public int getApogee() {
        return apogee;
    }

    public String getName() {
        return name;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}