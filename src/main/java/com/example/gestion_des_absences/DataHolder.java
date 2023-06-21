package com.example.gestion_des_absences;

public class DataHolder {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DataHolder.username = username;
    }
}
