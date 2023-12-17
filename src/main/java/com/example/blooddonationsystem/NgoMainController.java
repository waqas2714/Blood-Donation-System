package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NgoMainController {


    @FXML
    private Button BtnAddDrive;

    @FXML
    private Button BtnRemoveDrive;


    public void GoToAddDrive(ActionEvent event)
    {
        // Load the AddDrive.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addDrive.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the stage information
        Stage stage = (Stage) BtnAddDrive.getScene().getWindow();
        Scene scene = new Scene(root);

        // Set the new scene onto the stage
        stage.setScene(scene);
        stage.show();
    }

    public void GoToRemoveDrive(ActionEvent event)
    {
        // Load the ngoMain.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("removeDrive.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the stage information
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); //BtnRemoveDrive.getScene().getWindow();
        Scene scene = new Scene(root);

        // Set the new scene onto the stage
        stage.setScene(scene);
        stage.show();
    }


}
