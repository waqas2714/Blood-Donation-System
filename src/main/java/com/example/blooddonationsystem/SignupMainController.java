package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SignupMainController {
    @FXML
    private Button btnDonor;
    @FXML
    private Button btnOther;
    @FXML
    private Button btnGoToLogin;

    @FXML
    public void gotToDonor(ActionEvent e){
            try {
                // Load the login.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("signupDonor.fxml"));
                Parent root = loader.load();

                // Get the stage information
                Stage stage = (Stage) btnDonor.getScene().getWindow();
                Scene scene = new Scene(root);

                // Set the new scene onto the stage
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace(); // Handle the exception appropriately
            }
        }

    @FXML
    public void goToOther(ActionEvent e){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signupOther.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnOther.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    private void GoToLogin(ActionEvent event) {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnGoToLogin.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


}
