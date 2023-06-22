package com.example.gestion_des_absences;

import javafx.scene.control.CheckBox;

public class presenceDataWithNbtAbs extends presenceData {
    public int nbtAbs;
    private CheckBox checkPresence;

    public presenceDataWithNbtAbs(Integer apogee, String name, int nbtAbs) {
        super(apogee, name);
        this.checkPresence = new CheckBox();
        this.nbtAbs = nbtAbs;
    }

    public int getNbtAbs() {
        return nbtAbs;
    }

    public void setNbtAbs(int nbtAbs) {
        this.nbtAbs = nbtAbs;
    }

    //checkbox

    public CheckBox getCheckPresence() {
        return checkPresence;
    }

    public void setCheckPresence(CheckBox checkPresence) {
        this.checkPresence = checkPresence;
    }

}
