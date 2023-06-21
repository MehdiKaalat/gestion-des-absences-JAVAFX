package com.example.gestion_des_absences;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.showMessageDialog;


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
    private ComboBox<String> addEtudiant_filiere;

    @FXML
    private TextField addEtudiant_name;

    @FXML
    private ComboBox<String> addEtudiant_semestre;

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

    @FXML
    private DatePicker presence_date;

    @FXML
    private ComboBox<String> presence_filiere;

    @FXML
    private ComboBox<String> presence_module;

    @FXML
    private TextField presence_rechercher;

    @FXML
    private TableView<presenceData> presene_table;

    @FXML
    private TableColumn<etudiantData, String> actions_column;

    @FXML
    private TableColumn<presenceData, String> presence_name_col;

    @FXML
    private TableColumn<presenceData, Integer> presence_nbtAbs_col;

    @FXML
    private TableColumn<presenceData, CheckBox> presence_present_col;

    @FXML
    private TableColumn<presenceData, Integer> presence_apogee_col;

    String usernameLogin = DataHolder.getUsername();
    public ObservableList<etudiantData> data = FXCollections.observableArrayList();
    public ObservableList<presenceData> data2 = FXCollections.observableArrayList();

    public void ViewEtudiant(){
        try{
            Connection connect = database.connectDB();
            String sql  = """
                    SELECT DISTINCT etudiant.apogee, etudiant.name, filiere.nom_filiere, semestre.name_semestre
                    FROM etudiant
                    JOIN filiere ON etudiant.id_filiere = filiere.id_filiere
                    JOIN semestre ON etudiant.id_semestre = semestre.id_semestre
                    """;
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            // filiere
            String SqlFiliere = "SELECT nom_filiere FROM filiere";
            PreparedStatement stat2 = connect.prepareStatement(SqlFiliere);
            ResultSet rs2 = stat2.executeQuery();

            ObservableList dataFiliere = FXCollections.observableArrayList();
            while (rs2.next()){
                dataFiliere.add(new String((rs2.getString(1))));
            }
            addEtudiant_filiere.setItems(dataFiliere);

            // semestre

            String SqlSemestre = "SELECT name_semestre FROM semestre";
            PreparedStatement stat3 = connect.prepareStatement(SqlSemestre);
            ResultSet rs3 = stat3.executeQuery();

            ObservableList dataSemestre = FXCollections.observableArrayList();
            while (rs3.next()){
                dataSemestre.add(new String((rs3.getString(1))));
            }
            addEtudiant_semestre.setItems(dataSemestre);

            while(rs.next()){
                data.add(new etudiantData(rs.getInt(1),
                        rs.getString(2),
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
        semestre_column.setCellValueFactory(new PropertyValueFactory<etudiantData, String>("semestre"));
        etudiant_table.setItems(data);
    }

    @FXML
    void addEtudiantToDatabase(MouseEvent event) {
        try {
            String apogeeValue = addEtudiant_apogee.getText();
            String nameValue = addEtudiant_name.getText();
            String filiereValue = addEtudiant_filiere.getValue().toString();
            String semestreValue = addEtudiant_semestre.getValue();

            Connection connect = database.connectDB();

            String etudiantQuery = "INSERT INTO etudiant (apogee, name, id_filiere, id_semestre) " +
                    "VALUES (?, ?, (SELECT id_filiere FROM filiere WHERE nom_filiere = ?), " +
                    "(SELECT id_semestre FROM semestre WHERE name_semestre = ?))";
            PreparedStatement statement = connect.prepareStatement(etudiantQuery);
            statement.setString(1, apogeeValue);
            statement.setString(2, nameValue);
            statement.setString(3, filiereValue);
            statement.setString(4, semestreValue);
            statement.executeUpdate();
            statement.close();
            System.out.println("Data added to the database.");
            data.clear();
            ViewEtudiant();
            addEtudiant_apogee.clear();
            addEtudiant_name.clear();
            addEtudiant_filiere.setValue(null);
            addEtudiant_semestre.setValue(null);
            connect.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void SelectedRow() {
        etudiant_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Get the selected item from the TableView
                etudiantData selectedEtudiant = etudiant_table.getSelectionModel().getSelectedItem();

                // Populate the data into the text fields and combo boxes
                addEtudiant_apogee.setText(String.valueOf(selectedEtudiant.getApogee()));
                addEtudiant_name.setText(selectedEtudiant.getName());
                addEtudiant_filiere.getSelectionModel().select(selectedEtudiant.getFiliere());
                addEtudiant_semestre.getSelectionModel().select(selectedEtudiant.getSemestre());
            }
        });

    }
    @FXML
    void SupprimerEtudiant(MouseEvent event) {
        Connection connect = database.connectDB();
        String sql  = "DELETE FROM etudiant where apogee = ?";
        try{
            PreparedStatement stat = connect.prepareStatement(sql);
            String selectedValue = addEtudiant_apogee.getText();
            stat.setString(1,selectedValue);
            stat.execute();
            showMessageDialog(null,"Voulez-vous vraiment supprimer cet étudiant ?");
            data.clear();
            ViewEtudiant();
            addEtudiant_apogee.clear();
            addEtudiant_name.clear();
            addEtudiant_filiere.setValue(null);
            addEtudiant_semestre.setValue(null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void ModifierEtudiant(MouseEvent event) {
        try {
            String apogeeValue = addEtudiant_apogee.getText();
            String nameValue = addEtudiant_name.getText();
            String filiereValue = addEtudiant_filiere.getValue().toString();
            String semestreValue = addEtudiant_semestre.getValue();

            Connection connect = database.connectDB();

            // Check if the apogee value exists in the table before updating
            String checkQuery = "SELECT COUNT(*) FROM etudiant WHERE apogee = ?";
            PreparedStatement checkStatement = connect.prepareStatement(checkQuery);
            checkStatement.setString(1, apogeeValue);
            ResultSet checkResult = checkStatement.executeQuery();
            checkResult.next();
            int count = checkResult.getInt(1);
            checkStatement.close();

            if (count == 0) {
                // The apogee value doesn't exist, show an error message
                showMessageDialog(null,"Apogee spécifiée n'existe pas. Impossible de mettre à jour.");
            } else {
                // The apogee value exists, proceed with the update
                String etudiantQuery = "UPDATE etudiant " +
                        "SET apogee = ?, name = ?, id_filiere = (SELECT id_filiere FROM filiere WHERE nom_filiere = ?), " +
                        "id_semestre = (SELECT id_semestre FROM semestre WHERE name_semestre = ?) " +
                        "WHERE apogee = ?";
                PreparedStatement statement = connect.prepareStatement(etudiantQuery);
                statement.setString(1, apogeeValue);
                statement.setString(2, nameValue);
                statement.setString(3, filiereValue);
                statement.setString(4, semestreValue);
                statement.setString(5, apogeeValue); // Set the value for the fifth parameter

                statement.executeUpdate();
                statement.close();
                System.out.println("Data added to the database.");
                data.clear();
                ViewEtudiant();
                addEtudiant_apogee.clear();
                addEtudiant_name.clear();
                addEtudiant_filiere.setValue(null);
                addEtudiant_semestre.setValue(null);
            }

            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ViewEtudiant_presence(){
        try{
            Connection connect = database.connectDB();
            String sql  = """
                    SELECT DISTINCT apogee,name
                    FROM etudiant
                    """;
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            while(rs.next()){
                data2.add(new presenceData(rs.getInt(1),
                        rs.getString(2)));
            }
            presence_name_col.setCellValueFactory(new PropertyValueFactory<presenceData, String>("name"));
            presence_apogee_col.setCellValueFactory(new PropertyValueFactory<presenceData, Integer>("apogee"));
            presence_present_col.setCellValueFactory(new PropertyValueFactory<presenceData, CheckBox>("checkPresence"));
            presene_table.setItems(data2);
            // filiere
            String SqlFiliere = "SELECT nom_filiere FROM filiere";
            PreparedStatement stat2 = connect.prepareStatement(SqlFiliere);
            ResultSet rs2 = stat2.executeQuery();
            ObservableList dataFiliere = FXCollections.observableArrayList();
            while (rs2.next()){
                dataFiliere.add(new String((rs2.getString(1))));
            }
            presence_filiere.setItems(dataFiliere);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void handleFiliereSelection(Connection connect) {
        String selectedFiliere = presence_filiere.getValue();
        // Clear previous module selections
        presence_module.getItems().clear();

        try {
            // Prepare the SQL statement to retrieve modules based on filiere
            String sqlModule = "SELECT nom_module FROM module WHERE id_filiere = (SELECT id_filiere FROM filiere WHERE nom_filiere = ?)";
            PreparedStatement stat3 = connect.prepareStatement(sqlModule);
            stat3.setString(1, selectedFiliere);
            ResultSet rs3 = stat3.executeQuery();
            ObservableList<String> dataModule = FXCollections.observableArrayList();
            while (rs3.next()) {
                dataModule.add(rs3.getString(1));
            }

            String sql = "SELECT apogee, name " +
                    "FROM etudiant  " +
                    "WHERE id_filiere = (SELECT id_filiere FROM filiere WHERE nom_filiere = ?)";

            PreparedStatement stat = connect.prepareStatement(sql);
            stat.setString(1, selectedFiliere);
            ResultSet rs = stat.executeQuery();
            ObservableList<presenceData> filteredData = FXCollections.observableArrayList();
            while (rs.next()) {
                filteredData.add(new presenceData(rs.getInt(1), rs.getString(2)));
            }
            data2.clear();
            presence_name_col.setCellValueFactory(new PropertyValueFactory<presenceData, String>("name"));
            presence_apogee_col.setCellValueFactory(new PropertyValueFactory<presenceData, Integer>("apogee"));
            presence_present_col.setCellValueFactory(new PropertyValueFactory<presenceData, CheckBox>("checkPresence"));
            presene_table.setItems(filteredData);

            // Set the result to the module list
            presence_module.setItems(dataModule);
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void rechercher_etudiant(KeyEvent event) {
        try {
            Connection connection = database.connectDB();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT apogee, name FROM etudiant");

            while (resultSet.next()) {
                int apogee = resultSet.getInt("apogee");
                String name = resultSet.getString("name");

                data2.add(new presenceData(apogee, name));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String searchInput = presence_rechercher.getText().trim().toLowerCase();

        ObservableList<presenceData> filteredData = FXCollections.observableArrayList();

        for (presenceData etudiant : data2) {
            if (String.valueOf(etudiant.getApogee()).contains(searchInput)
                    || etudiant.getName().toLowerCase().contains(searchInput)) {
                filteredData.add(etudiant);
            }
        }
        data2.clear();
        presene_table.setItems(filteredData);
    }

    @FXML
    void enregistrer_table(MouseEvent event) {
        LocalDate selectedDate = presence_date.getValue();
        String selectedModule = presence_module.getValue();
        TableView<presenceData> tableView = presene_table;
        ObservableList<presenceData> data = tableView.getItems();

        try {
            Connection connection = database.connectDB();
            connection.setAutoCommit(false); // Disable auto-commit

            String sqlInsert = "INSERT INTO absence (date_abs, apogee, id_module) VALUES (?, ?, ?)";
            String sqlCheck = "SELECT id_abs FROM absence WHERE apogee = ? AND date_abs = ? AND id_module = ?";
            PreparedStatement insertStatement = connection.prepareStatement(sqlInsert);
            PreparedStatement checkStatement = connection.prepareStatement(sqlCheck);

            for (presenceData item : data) {
                CheckBox checkBox = item.getCheckPresence(); // Get the checkbox from the data item
                int apogee = item.getApogee();
                int moduleId = getModuleId(selectedModule, connection);

                if (checkBox.isSelected()) {
                    // Check if the absence record already exists
                    checkStatement.setInt(1, apogee);
                    checkStatement.setDate(2, java.sql.Date.valueOf(selectedDate));
                    checkStatement.setInt(3, moduleId);
                    ResultSet resultSet = checkStatement.executeQuery();

                    if (!resultSet.next()) {
                        // Insert the absence record
                        insertStatement.setDate(1, java.sql.Date.valueOf(selectedDate));
                        insertStatement.setInt(2, apogee);
                        insertStatement.setInt(3, moduleId);
                        insertStatement.executeUpdate();
                    }

                    resultSet.close();
                }
            }

            connection.commit(); // Commit the transaction
            insertStatement.close();
            checkStatement.close();
            connection.close();

            // Optional: Show a success message or perform any other desired action after saving

        } catch (SQLException e) {
            e.printStackTrace();
            // Optional: Show an error message or perform any other desired action in case of an exception
        }
    }




    private int getModuleId(String moduleName, Connection connection) throws SQLException {
        String sql = "SELECT id_module FROM module WHERE nom_module = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, moduleName);
        ResultSet resultSet = statement.executeQuery();

        int moduleId = -1;
        if (resultSet.next()) {
            moduleId = resultSet.getInt("id_module");
        }

        resultSet.close();
        statement.close();

        return moduleId;
    }

    @FXML
    void retrieveSelectedCheckboxes(LocalDate selectedDate) {
        String selectedModule = presence_module.getValue();
        TableView<presenceData> tableView = presene_table;
        ObservableList<presenceData> data = tableView.getItems();

        try {
            Connection connection = database.connectDB();
            String sqlSelectModuleId = "SELECT id_module FROM module WHERE nom_module = ?";
            PreparedStatement selectModuleIdStatement = connection.prepareStatement(sqlSelectModuleId);
            selectModuleIdStatement.setString(1, selectedModule);
            ResultSet moduleResultSet = selectModuleIdStatement.executeQuery();

            int moduleId = -1; // Default value if module not found
            if (moduleResultSet.next()) {
                moduleId = moduleResultSet.getInt("id_module");
            }

            moduleResultSet.close();
            selectModuleIdStatement.close();

            String sqlSelect = "SELECT apogee FROM absence WHERE date_abs = ? AND id_module = ?";
            PreparedStatement selectStatement = connection.prepareStatement(sqlSelect);
            selectStatement.setDate(1, java.sql.Date.valueOf(selectedDate));
            selectStatement.setInt(2, moduleId);
            ResultSet resultSet = selectStatement.executeQuery();

            List<Integer> apogeeList = new ArrayList<>();
            while (resultSet.next()) {
                int apogee = resultSet.getInt("apogee");
                apogeeList.add(apogee);
            }

            resultSet.close();
            selectStatement.close();
            connection.close();

            for (presenceData item : data) {
                CheckBox checkBox = item.getCheckPresence(); // Get the checkbox from the data item
                int apogee = item.getApogee();

                if (apogeeList.contains(apogee)) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Optional: Show an error message or perform any other desired action in case of an exception
        }
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
        Connection connect = database.connectDB();
        ViewEtudiant();
        SelectedRow();
        ViewEtudiant_presence();
        // Add event handler for filiere selection
        presence_filiere.setOnAction(event -> handleFiliereSelection(connect));
        presence_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                retrieveSelectedCheckboxes(newValue);
            }
        });
        // Add a listener to the presence_module ComboBox value property
        presence_module.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate selectedDate = presence_date.getValue();
            if (selectedDate != null) {
                retrieveSelectedCheckboxes(selectedDate);
            }
        });
    }
}
