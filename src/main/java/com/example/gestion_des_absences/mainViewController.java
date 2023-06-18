package com.example.gestion_des_absences;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;

import java.io.IOException;
import java.util.ResourceBundle;


public class mainViewController implements Initializable {

    @FXML // fx:id="a_propos"
    private AnchorPane a_propos; // Value injected by FXMLLoader

    @FXML // fx:id="accueil"
    private AnchorPane accueil; // Value injected by FXMLLoader

    @FXML // fx:id="btn_accueil"
    private Pane btn_accueil; // Value injected by FXMLLoader

    @FXML // fx:id="btn_exit"
    private Button btn_exit; // Value injected by FXMLLoader

    @FXML // fx:id="btn_hide"
    private Button btn_hide; // Value injected by FXMLLoader

    @FXML // fx:id="btn_liste_etu"
    private Pane btn_liste_etu; // Value injected by FXMLLoader

    @FXML // fx:id="btn_presence"
    private Pane btn_presence; // Value injected by FXMLLoader

    @FXML // fx:id="btn_propos"
    private Pane btn_propos; // Value injected by FXMLLoader

    @FXML // fx:id="liste_etu"
    private AnchorPane liste_etu; // Value injected by FXMLLoader

    @FXML // fx:id="presence"
    private AnchorPane presence; // Value injected by FXMLLoader

    @FXML
    private TextField addEtudiant_apogee;

    @FXML
    private ComboBox<?> addEtudiant_filiere;

    @FXML
    private TextField addEtudiant_name;

    @FXML
    private ComboBox<?> addEtudiant_semestre;

    @FXML
    private Button ajouter_btn;

    @FXML
    private TableView<etudiantData> etudiant_table;

    @FXML
    private TableColumn<etudiantData, Integer> apogee_column;

    @FXML
    private TableColumn<etudiantData, String> filiere_column;

    @FXML
    private TableColumn<etudiantData, String> name_column;

    @FXML
    private TableColumn<etudiantData, String> semestre_column;

    public ObservableList<etudiantData> data = FXCollections.observableArrayList();

    public void ViewEtudiant(){
        try{
            Connection connect = database.connectDB();
            String sql  = "SELECT * FROM etudiant";
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            while(rs.next()){
                data.add(new etudiantData(rs.getString(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
            connect.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
        name_column.setCellValueFactory(new PropertyValueFactory<etudiantData, String>("name"));
        apogee_column.setCellValueFactory(new PropertyValueFactory<etudiantData, Integer>("apogee"));
        filiere_column.setCellValueFactory(new PropertyValueFactory<etudiantData, String>("filiere"));
        etudiant_table.setItems(data);
    }


    @FXML
    void afficher_accueil(MouseEvent event) {
        accueil.setVisible(true);
        a_propos.setVisible(false);
        liste_etu.setVisible(false);
        presence.setVisible(false);
    }

    @FXML
    void afficher_liste_etu(MouseEvent event) {
        accueil.setVisible(false);
        a_propos.setVisible(false);
        liste_etu.setVisible(true);
        presence.setVisible(false);
    }

    @FXML
    void afficher_presence(MouseEvent event) {
        accueil.setVisible(false);
        a_propos.setVisible(false);
        liste_etu.setVisible(false);
        presence.setVisible(true);
    }

    @FXML
    void afficher_propos(MouseEvent event) {
        accueil.setVisible(false);
        a_propos.setVisible(true);
        liste_etu.setVisible(false);
        presence.setVisible(false);
    }

    @FXML
    void deconnecter_fct(MouseEvent event) throws IOException {
    }
    @FXML
    void exit_fct(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void hide_fct(MouseEvent event) {
        Stage stage = (Stage) btn_hide.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
