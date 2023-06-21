package com.example.gestion_des_absences;

public class etudiantData {
    private String name;
    public Integer apogee;
    private String filiere;
    private String semestre;

    public etudiantData(){
        super();
    }
    public etudiantData(Integer apogee, String name, String filiere, String semestre) {
        super();
        this.name = name;
        this.apogee = apogee;
        this.filiere = filiere;
        this.semestre = semestre;
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

    public void setApogee(int apogee) {
        this.apogee = apogee;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }



}
