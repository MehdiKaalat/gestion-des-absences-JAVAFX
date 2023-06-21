module com.example.gestion_des_absences {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.gestion_des_absences to javafx.fxml;
    exports com.example.gestion_des_absences;
}