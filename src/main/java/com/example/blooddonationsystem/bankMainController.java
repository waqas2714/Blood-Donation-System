package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class bankMainController implements Initializable {

    @FXML
    private Button btnViewRequestBank;
    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnViewStorageBank;
    @FXML
    private Integer bloodBankID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    public void setBloodBankID(Integer bankID){
        this.bloodBankID = bankID;
    }

    @FXML
    public void gotoViewRequest(ActionEvent e) {
        try {
            // Load the signupMain.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewRequests.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnViewRequestBank.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            error.printStackTrace(); // Handle the exception appropriately
        }
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
    @FXML
    public void gotoViewStorage(ActionEvent e) {
        try {
            // Load the signupMain.fxml file
            System.out.println("BANKMAIN bloodbankID: "+ bloodBankID);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewStorage.fxml"));
            Parent root = loader.load();
            viewStorage secondController = loader.getController();
            secondController.setBloodBankID(bloodBankID);
            // Get the stage information
            Stage stage = (Stage) btnViewStorageBank.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            error.printStackTrace(); // Handle the exception appropriately
        }
    }
}



