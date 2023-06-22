package com.example.gestion_des_absences;

import javafx.scene.control.CheckBox;

public class nbAb {
    private String name;
    private Integer apogee;
    private CheckBox checkPresence;
    private int idAbsence;

    private int nbtAbs;

    public nbAb(){
        super();
    }
    public nbAb(Integer apogee, String name, int nbtAbs) {
        super();
        this.name = name;
        this.apogee = apogee;
        this.checkPresence = new CheckBox();
        this.nbtAbs = nbtAbs;
    }

    //nb absence
    public int getNbAbs() {
        return nbtAbs;
    }

    public void setNbAbs(int nbtAbs) {
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
