package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddDriveController {

    @FXML
    private Button btnAddDrive;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtFieldLoc;

    @FXML
    private DatePicker DateBox;
    @FXML
    private DatePicker EndDateBox;
    private static int D_id, Loc_id, Org_id;
    private static String D_name;

    public static void setValues(int id1, String driveName, int id2, int id3) // call this function and send values
    {
        D_id = id1; // drive_id
        D_name = driveName;
        Loc_id = id2; // Location ID
        Org_id = id3; // Organization ID
    }

    public void AddDriveFunc(ActionEvent event) throws SQLException {

        Connection con = HelloApplication.getConnection();
        String Location = txtFieldLoc.getText();
        LocalDate Date = DateBox.getValue();
        LocalDate endDate = DateBox.getValue();

        String Query = "INSERT INTO donationdrives (drive_id, drive_name, start_date, end_date, location_id, organizer_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement1 = con.prepareStatement(Query);

        statement1.setInt(1, D_id);
        statement1.setString(2, D_name);
        statement1.setDate(3, java.sql.Date.valueOf(Date));
        statement1.setDate(4, java.sql.Date.valueOf(endDate));
        statement1.setInt(5, Loc_id);
        statement1.setInt(6, Org_id);


        statement1.executeUpdate();


        DateBox.setValue(null);
        EndDateBox.setValue(null);



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
