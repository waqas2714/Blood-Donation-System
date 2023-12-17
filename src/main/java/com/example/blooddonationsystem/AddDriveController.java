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


    public void AddDriveFunc(ActionEvent event)
    {
        Connection con = HelloApplication.getConnection();
        String Location = txtFieldLoc.getText();
        LocalDate Date = DateBox.getValue();




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
