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
    @FXML
    private Integer ngoID;

    @FXML
    private Button btnLogOut;
    @FXML
    public void setNgoID(Integer bankID){
        this.ngoID = bankID;
        System.out.println("ngoMain ngoID: "+ ngoID);
    }

    @FXML
    public void goToLogin(ActionEvent event){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnLogOut.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    public void GoToAddDrive(ActionEvent event)
    {
        // Load the AddDrive.fxml file
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addDrive.fxml"));
        //add object of AddDrives
        Parent root = loader.load();
        AddDriveController secondController = loader.getController();
        secondController.setNgoID(ngoID);

        // Get the stage information
        Stage stage = (Stage) BtnAddDrive.getScene().getWindow();
        Scene scene = new Scene(root);

        // Set the new scene onto the stage
        stage.setScene(scene);
        stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GoToRemoveDrive(ActionEvent event)
    {
        // Load the ngoMain.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("removeDrive.fxml"));
            Parent root = loader.load();
            RemoveDriveController secondcontroller = loader.getController();
            secondcontroller.setNgoID(ngoID);


            // Get the stage information
            Stage stage = (Stage) BtnRemoveDrive.getScene().getWindow(); //(Stage) ((Node)event.getSource()).getScene().getWindow()
            Scene scene = new Scene(root);

            // Set the new scene onto the stage

            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}