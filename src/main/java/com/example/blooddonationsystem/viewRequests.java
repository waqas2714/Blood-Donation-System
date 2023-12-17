package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class viewRequests {
    @FXML
    private Button btnBloodTypeBank;
    @FXML
    private Button btnHospitalBank;
    @FXML
    private Button btnLocationBank;
    @FXML
    private Button btnBackBank;

    @FXML
    public void gotToHospital(ActionEvent e){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reportHospital.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnHospitalBank.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle the exception appropriately
        }
    }
    @FXML
    public void gotToBack(ActionEvent e){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewRequests.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnBackBank.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    public void goToLocation(ActionEvent e){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reportLocation.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnLocationBank.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    private void goToBlood(ActionEvent event) {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reportBloodType.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnBloodTypeBank.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
