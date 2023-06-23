package com.example.gestion_des_absences;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

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

    @FXML // fx:id="btn_hide"
    private Button btn_hide; // Value injected by FXMLLoader

    @FXML // fx:id="liste_etu"
    private AnchorPane liste_etu; // Value injected by FXMLLoader

    @FXML // fx:id="presence"
    private AnchorPane presence; // Value injected by FXMLLoader

    @FXML
    private Pane btn_accueil;
    @FXML
    private Pane btn_liste_etu;
    @FXML
    private Pane btn_presence;

    @FXML
    private Pane btn_propos;

    @FXML
    private TextField addEtudiant_apogee;

    @FXML
    private ComboBox<String> addEtudiant_filiere;

    @FXML
    private TextField addEtudiant_name;

    @FXML
    private ComboBox<String> addEtudiant_semestre;


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
    private TableColumn<presenceData, String> presence_name_col;

    @FXML
    private TableColumn<presenceData, Integer> presence_nbtAbs_col;

    @FXML
    private TableColumn<presenceData, CheckBox> presence_present_col;

    @FXML
    private TableColumn<presenceData, Integer> presence_apogee_col;

    @FXML
    private Label nb_absence;

    @FXML
    private Label nb_classe;

    @FXML
    private Label nb_etudiant;

    @FXML
    private Label nb_filiere;

    @FXML
    private Label nom_prof;

    @FXML
    private Label IDProf_label;

    @FXML
    private Label emailProf_label;

    @FXML
    private Label nomProf_label;

    @FXML
    private Label deconnecter_btn;

    String usernameLogin = DataHolder.getUsername();
    public ObservableList<etudiantData> data = FXCollections.observableArrayList();
    public ObservableList<presenceData> data2 = FXCollections.observableArrayList();

    public void ViewEtudiant(){
        try{
            Connection connect = database.connectDB();
            String sql  = "SELECT DISTINCT etudiant.apogee, etudiant.name, filiere.nom_filiere, semestre.name_semestre FROM etudiant JOIN filiere ON etudiant.id_filiere = filiere.id_filiere JOIN semestre ON etudiant.id_semestre = semestre.id_semestre JOIN module ON module.id_filiere = etudiant.id_filiere AND module.id_semestre = etudiant.id_semestre WHERE module.id_prof= '"+usernameLogin+"'";
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            // filiere
            String SqlFiliere = "SELECT DISTINCT(filiere.nom_filiere) FROM filiere JOIN module ON module.id_filiere = filiere.id_filiere WHERE module.id_prof = '"+usernameLogin+"'";
            PreparedStatement stat2 = connect.prepareStatement(SqlFiliere);
            ResultSet rs2 = stat2.executeQuery();

            ObservableList dataFiliere = FXCollections.observableArrayList();
            while (rs2.next()){
                dataFiliere.add(new String((rs2.getString(1))));
            }
            addEtudiant_filiere.setItems(dataFiliere);

            // semestre

            String SqlSemestre = "SELECT DISTINCT(semestre.name_semestre) FROM semestre JOIN module ON module.id_semestre = semestre.id_semestre WHERE module.id_prof = '"+usernameLogin+"'";
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
        }catch (Exception e) {
            e.printStackTrace();
            // Display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite");
            alert.setContentText("Une erreur s'est produite lors de suppression des données. Veuillez réessayer.");
            alert.showAndWait();
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
                showMessageDialog(null, "Apogee spécifiée n'existe pas. Impossible de mettre à jour.");
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
                data.clear();
                ViewEtudiant();
                addEtudiant_apogee.clear();
                addEtudiant_name.clear();
                addEtudiant_filiere.setValue(null);
                addEtudiant_semestre.setValue(null);
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite");
            alert.setContentText("Une erreur s'est produite lors de modification des données. Veuillez réessayer.");
            alert.showAndWait();
        }
    }
    public void ViewEtudiant_presence() {
        try {
            Connection connect = database.connectDB();
            String sql = "SELECT DISTINCT etudiant.apogee, etudiant.name, etudiant.id_filiere FROM etudiant JOIN module ON etudiant.id_semestre = module.id_semestre AND etudiant.id_filiere = module.id_filiere WHERE module.id_prof = '"+usernameLogin+"'";
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            ObservableList dataAbs = FXCollections.observableArrayList();
            while (rs.next()) {
                int apogee = rs.getInt(1);
                String name = rs.getString(2);
                data2.add(new presenceData(apogee, name)); // Updated presenceData constructor
            }

            presence_name_col.setCellValueFactory(new PropertyValueFactory<presenceData, String>("name"));
            presence_apogee_col.setCellValueFactory(new PropertyValueFactory<presenceData, Integer>("apogee"));
            presence_present_col.setCellValueFactory(new PropertyValueFactory<presenceData, CheckBox>("checkPresence"));
            presene_table.setItems(data2);

            // filiere
            String SqlFiliere = "SELECT DISTINCT(filiere.nom_filiere) FROM filiere JOIN module ON module.id_filiere = filiere.id_filiere WHERE module.id_prof = '"+usernameLogin+"'";
            PreparedStatement stat2 = connect.prepareStatement(SqlFiliere);
            ResultSet rs2 = stat2.executeQuery();
            ObservableList dataFiliere = FXCollections.observableArrayList();
            while (rs2.next()) {
                dataFiliere.add(rs2.getString(1));
            }
            presence_filiere.setItems(dataFiliere);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void handleFiliereSelection(Connection connect) {
        String selectedFiliere = presence_filiere.getValue();
        // Clear previous module selections
        presence_module.getItems().clear();

        try {
            // Prepare the SQL statement to retrieve modules based on filiere
            String sqlModule = "SELECT nom_module FROM module WHERE id_filiere = (SELECT id_filiere FROM filiere WHERE nom_filiere = ?) AND id_prof = '"+usernameLogin+"'";
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
    private void handleModuleSelection(Connection connect) {
        String selectedFiliere = presence_filiere.getValue();
        String selectedModule = presence_module.getValue();
        try {
            String sql = """
                SELECT etudiant.apogee, etudiant.name, COUNT(absence.id_abs) as nbtAbs
                FROM etudiant
                LEFT JOIN module ON etudiant.id_semestre = module.id_semestre
                LEFT JOIN absence ON etudiant.apogee = absence.apogee AND module.id_module = absence.id_module
                LEFT JOIN filiere ON etudiant.id_filiere = filiere.id_filiere
                WHERE module.nom_module = ?
                AND filiere.nom_filiere = ?
                GROUP BY etudiant.apogee, etudiant.name;
                """;

            PreparedStatement stat = connect.prepareStatement(sql);
            stat.setString(1, selectedModule);
            stat.setString(2, selectedFiliere);
            ResultSet rs = stat.executeQuery();
            ObservableList<presenceData> data = FXCollections.observableArrayList();
            while (rs.next()) {
                int apogee = rs.getInt("apogee");
                String name = rs.getString("name");
                int nbtAbs = rs.getInt("nbtAbs");
                data.add(new presenceDataWithNbtAbs(apogee, name, nbtAbs));
            }
            presence_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            presence_apogee_col.setCellValueFactory(new PropertyValueFactory<>("apogee"));
            presence_present_col.setCellValueFactory(new PropertyValueFactory<>("checkPresence"));
            presence_nbtAbs_col.setCellValueFactory(new PropertyValueFactory<>("nbtAbs"));
            presence_nbtAbs_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            presene_table.setItems(data);
            LocalDate selectedDate = presence_date.getValue();
            if (selectedDate != null) {
                retrieveSelectedCheckboxes(selectedDate);
            }

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
            PreparedStatement insertStatement = connection.prepareStatement(sqlInsert);

            String sqlDelete = "DELETE FROM absence WHERE id_abs = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete);

            for (presenceData item : data) {
                CheckBox checkBox = item.getCheckPresence(); // Get the checkbox from the data item
                int apogee = item.getApogee();
                int moduleId = getModuleId(selectedModule, connection);

                if (checkBox.isSelected()) {
                    // Check if the absence record already exists
                    String sqlCheck = "SELECT id_abs FROM absence WHERE apogee = ? AND date_abs = ? AND id_module = ?";
                    PreparedStatement checkStatement = connection.prepareStatement(sqlCheck);

                    checkStatement.setInt(1, apogee);
                    checkStatement.setDate(2, java.sql.Date.valueOf(selectedDate));
                    checkStatement.setInt(3, moduleId);

                    ResultSet resultSet = checkStatement.executeQuery();

                    if (!resultSet.next()) {
                        // Insert the absence record only if it doesn't already exist
                        insertStatement.setDate(1, java.sql.Date.valueOf(selectedDate));
                        insertStatement.setInt(2, apogee);
                        insertStatement.setInt(3, moduleId);
                        insertStatement.executeUpdate();
                    }

                    resultSet.close();
                    checkStatement.close();
                } else {
                    // Delete the absence record
                    String sqlCheck = "SELECT id_abs FROM absence WHERE apogee = ? AND date_abs = ? AND id_module = ?";
                    PreparedStatement checkStatement = connection.prepareStatement(sqlCheck);

                    checkStatement.setInt(1, apogee);
                    checkStatement.setDate(2, java.sql.Date.valueOf(selectedDate));
                    checkStatement.setInt(3, moduleId);

                    ResultSet resultSet = checkStatement.executeQuery();

                    if (resultSet.next()) {
                        int absenceId = resultSet.getInt("id_abs");
                        deleteStatement.setInt(1, absenceId);
                        deleteStatement.executeUpdate();
                    }

                    resultSet.close();
                    checkStatement.close();
                }
            }
            connection.commit();
            data.clear();
            handleModuleSelection(connection);
            insertStatement.close();
            deleteStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite");
            alert.setContentText("Une erreur s'est produite lors de l'enregistrement des données. Veuillez réessayer.");
            alert.showAndWait();
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
        fct_nbEtudiant();
        fct_nbClasse();
        fct_nbFiliere();
        fct_nbAbsence();
        btn_accueil.getStyleClass().add("btn_accueil");
        btn_presence.getStyleClass().remove("label-clicked");
        btn_liste_etu.getStyleClass().remove("label-clicked");
        btn_propos.getStyleClass().remove("label-clicked");

    }

    @FXML
    void afficher_liste_etu(MouseEvent event) {
        accueil.setVisible(false);
        a_propos.setVisible(false);
        liste_etu.setVisible(true);
        presence.setVisible(false);
        btn_accueil.getStyleClass().remove("btn_accueil");
        btn_presence.getStyleClass().remove("label-clicked");
        btn_liste_etu.getStyleClass().add("label-clicked");
        btn_propos.getStyleClass().remove("label-clicked");
    }

    @FXML
    void afficher_presence(MouseEvent event) {
        accueil.setVisible(false);
        a_propos.setVisible(false);
        liste_etu.setVisible(false);
        presence.setVisible(true);
        ViewEtudiant_presence();
        btn_accueil.getStyleClass().remove("btn_accueil");
        btn_presence.getStyleClass().add("label-clicked");
        btn_liste_etu.getStyleClass().remove("label-clicked");
        btn_propos.getStyleClass().remove("label-clicked");
    }

    @FXML
    void afficher_propos(MouseEvent event) {
        accueil.setVisible(false);
        a_propos.setVisible(true);
        liste_etu.setVisible(false);
        presence.setVisible(false);
        btn_accueil.getStyleClass().remove("btn_accueil");
        btn_presence.getStyleClass().remove("label-clicked");
        btn_liste_etu.getStyleClass().remove("label-clicked");
        btn_propos.getStyleClass().add("label-clicked");
    }
    @FXML
    void deconnecter_fct(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) deconnecter_btn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        currentStage.hide();

        // Make the new stage draggable
        final double initialX = event.getSceneX();
        final double initialY = event.getSceneY();

        root.setOnMousePressed((MouseEvent mouseEvent) -> {
            final double xOffset = mouseEvent.getSceneX() - initialX;
            final double yOffset = mouseEvent.getSceneY() - initialY;
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });

        // Optional: Close the current stage after a slight delay
        Platform.runLater(() -> currentStage.close());
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
    void fct_nbEtudiant(){
        Connection connect = database.connectDB();
        String sql = "SELECT count(DISTINCT etudiant.apogee) AS nb_etu FROM etudiant JOIN module ON module.id_filiere = etudiant.id_filiere JOIN semestre ON module.id_semestre = etudiant.id_semestre JOIN professeur ON module.id_prof = professeur.id_prof WHERE professeur.id_prof = '"+usernameLogin+"'";
        try {
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(); // Execute the query and get the result set
            if (rs.next()) {
                String nb_Etu = rs.getString("nb_etu"); // Get the value of the "nb_etu"
                nb_etudiant.setText(nb_Etu); // Set the value to the label "nb_Etu"
            }
            rs.close(); // Close the result set
            stat.close(); // Close the prepared statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        void fct_nbClasse(){
        Connection connect = database.connectDB();
        String sql = "SELECT COUNT(DISTINCT(id_module)) AS nb_mod FROM module WHERE id_prof = '"+usernameLogin+"'";
        try {
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(); // Execute the query and get the result set
            if (rs.next()) {
                String nb_Classe = rs.getString("nb_mod"); // Get the value of the "nb_etu"
                nb_classe.setText(nb_Classe); // Set the value to the label "nb_Etu"
            }
            rs.close(); // Close the result set
            stat.close(); // Close the prepared statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void fct_nomProf() {
        Connection connect = database.connectDB();
        String sql = "SELECT name_prof FROM professeur WHERE id_prof = ?";
        try {
            PreparedStatement stat = connect.prepareStatement(sql);
            stat.setString(1, usernameLogin);
            ResultSet rs = stat.executeQuery(); // Execute the query and get the result set
            if (rs.next()) {
                String nameProf = rs.getString("name_prof"); // Get the value of the "name_prof" column
                nom_prof.setText(nameProf); // Set the value to the label "nom_prof"
            }
            rs.close(); // Close the result set
            stat.close(); // Close the prepared statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void fct_nbFiliere() {
        Connection connect = database.connectDB();
        String sql = "SELECT COUNT(DISTINCT(filiere.id_filiere)) AS nb_f FROM filiere JOIN module on module.id_filiere = filiere.id_filiere JOIN professeur on professeur.id_prof = module.id_prof WHERE professeur.id_prof = '"+usernameLogin+"'";
        try {
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(); // Execute the query and get the result set
            if (rs.next()) {
                int nbFiliere = rs.getInt("nb_f"); // Get the value of the "nb_f" column
                nb_filiere.setText(String.valueOf(nbFiliere)); // Set the value to the label "nom_prof"
            }
            rs.close(); // Close the result set
            stat.close(); // Close the prepared statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void fct_nbAbsence() {
        Connection connect = database.connectDB();
        String sql = "SELECT COUNT(DISTINCT(absence.id_abs)) AS nb_abs FROM absence JOIN module on module.id_module = absence.id_module JOIN professeur on professeur.id_prof = module.id_prof WHERE professeur.id_prof = '"+usernameLogin+"'";
        try {
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(); // Execute the query and get the result set
            if (rs.next()) {
                int nbAbsence = rs.getInt("nb_abs"); // Get the value of the "nb_f" column
                nb_absence.setText(String.valueOf(nbAbsence)); // Set the value to the label "nom_prof"
            }
            rs.close(); // Close the result set
            stat.close(); // Close the prepared statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void fct_apropos() {
        Connection connect = database.connectDB();
        String sql = "SELECT id_prof, name_prof, email_prof FROM professeur WHERE professeur.id_prof = '"+usernameLogin+"'";
        try {
            PreparedStatement stat = connect.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(); // Execute the query and get the result set
            if (rs.next()) {
                int idProf = rs.getInt("id_prof");
                IDProf_label.setText(String.valueOf(idProf));

                String nomProf = rs.getString("name_prof");
                nomProf_label.setText(String.valueOf(nomProf));

                String emailProf = rs.getString("email_prof");
                emailProf_label.setText(String.valueOf(emailProf));
            }
            rs.close(); // Close the result set
            stat.close(); // Close the prepared statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connect = database.connectDB();
        ViewEtudiant();
        ViewEtudiant_presence();
        SelectedRow();
        // Add event handler for filiere selection
        presence_filiere.setOnAction(event -> handleFiliereSelection(connect));
        presence_module.setOnAction(event -> handleModuleSelection(connect));
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

        fct_nomProf();
        fct_nbEtudiant();
        fct_nbClasse();
        fct_nbFiliere();
        fct_nbAbsence();
        fct_apropos();
    }
}
