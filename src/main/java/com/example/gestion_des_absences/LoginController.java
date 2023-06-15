package com.example.gestion_des_absences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;

public class LoginController{
        @FXML
        private Button button_connect;

        @FXML
        private Label button_x;

        @FXML
        private TextField username;

        @FXML
        private PasswordField password;



//        DATABASE TOOLS
        private Connection connect;
        private PreparedStatement prepare;
        private ResultSet result;

        private double x=0;
        private double y=0;


        @FXML
        void X_remove(MouseEvent event) {
            javafx.application.Platform.exit();
        }

        @FXML
        void seconnect_clicked(MouseEvent event) {
                loginAdmin();
        }

        public void loginAdmin(){
                String sql = "SELECT * FROM professeur WHERE id_prof = ? and password_prof = ?";
                connect = database.connectDB();
                try{
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1,username.getText());
                        prepare.setString(2,password.getText());

                        result = prepare.executeQuery();
                        Alert alert;
                        if(username.getText().isEmpty() || password.getText().isEmpty()){
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Veuilliez entrer votre identifiant and mot de passe ");
                                alert.showAndWait();
                        }
                        else{
                                if(result.next()){
                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Information Message");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Connexion réussite");
                                        alert.showAndWait();

                                        button_connect.getScene().getWindow().hide();
                                        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                                        Stage stage = new Stage();
                                        Scene scene = new Scene(root);

                                        root.setOnMousePressed((MouseEvent event) ->{
                                                x = event.getSceneX();
                                                y = event.getSceneY();
                                        });
                                        root.setOnMouseDragged((MouseEvent event) ->{
                                                stage.setX(event.getScreenX()-x);
                                                stage.setY(event.getScreenY()-y);
                                        });

                                        stage.initStyle(StageStyle.TRANSPARENT);
                                        stage.setScene(scene);
                                        stage.show();
                                }else {
                                        alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error Message");
                                        alert.setHeaderText(null);
                                        alert.setContentText("identifiant/mot de passe est incorrectes");
                                        alert.showAndWait();
                                }
                        }
                }catch(Exception e){e.printStackTrace();}
        }


}
