package com.example.blooddonationsystem;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddDriveController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxCity;

    @FXML
    private Button btnAddDrive;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtFieldLoc;

    @FXML
    private TextField txtFieldName;

    @FXML
    private Label PrmtLbl;

    @FXML
    private DatePicker DateBox;
    @FXML
    private DatePicker EndDateBox;
    private static int D_id, Loc_id, Org_id;
    private static String D_name;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Code for autocomplete
        List<String> cities = Cities.getCities();
        // Add more cities as needed

        txtFieldLoc.setOnKeyReleased(event -> {
            String enteredText = txtFieldLoc.getText().toLowerCase();

            List<String> suggestions = cities.stream()
                    .filter(city -> city.toLowerCase().startsWith(enteredText))
                    .collect(Collectors.toList());

            choiceBoxCity.setItems(FXCollections.observableArrayList(suggestions));
            choiceBoxCity.show(); // Show the choice box with suggestions
        });

        choiceBoxCity.setOnAction(event -> {
            String selectedCity = choiceBoxCity.getSelectionModel().getSelectedItem();
            if (selectedCity != null) {
                txtFieldLoc.setText(selectedCity); // Set txtCity value to the selected city
            }
        });
    }
    public static void setValues( String driveName, int id2, int id3) // call this function and send values
    {
        D_name = driveName;
        Loc_id = id2; // Location ID
        Org_id = id3; // Organization ID
    }

    public void AddDriveFunc(ActionEvent event) throws SQLException {

        Connection con = HelloApplication.getConnection();
        String Location = txtFieldLoc.getText().toLowerCase();
        LocalDate Date = DateBox.getValue();
        LocalDate endDate = DateBox.getValue();
        String Name = txtFieldName.getText();
        if(Name == null || Name.isEmpty()){
            PrmtLbl.setText("Please Enter Valid Name.");
            return;
        }
        if(Date.isBefore(LocalDate.now())){
            PrmtLbl.setText("Please Enter Valid Start Date");
            return;
        }
        if(endDate.isBefore(Date)){
            PrmtLbl.setText("End Date cannot be before Start Date.");
            return;
        }
        String Query2 = "SELECT location_id from locations where city = ?";
        PreparedStatement stmt = con.prepareStatement(Query2);
        stmt.setString(1, Location);
        ResultSet set1 = stmt.executeQuery();
        if (set1.next()) {
            int loc_id_test = set1.getInt("location_id");

            String Query = "INSERT INTO donationdrives ( drive_name, start_date, end_date, location_id, organizer_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement1 = con.prepareStatement(Query);

            statement1.setString(1, Name);
            statement1.setDate(2, java.sql.Date.valueOf(Date));
            statement1.setDate(3, java.sql.Date.valueOf(endDate));
            statement1.setInt(4, loc_id_test);
            statement1.setInt(5, 2);


            statement1.executeUpdate();


            DateBox.setValue(null);
            EndDateBox.setValue(null);
            PrmtLbl.setText("");
            txtFieldLoc.setText("");
            txtFieldName.setText("");
        }
        else {
            PrmtLbl.setText("Please Enter Correct Location.");
        }
    }


    public void GoBackToNGOMain(ActionEvent event)
    {
        // Load the ngoMain.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ngoMain.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the stage information
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Scene scene = new Scene(root);

        // Set the new scene onto the stage
        stage.setScene(scene);
        stage.show();
    }


}
