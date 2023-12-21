package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;
public class viewRequests {
    @FXML
    private Button btnBloodTypeBank;
    @FXML
    private Button btnHospitalBank;
    @FXML
    private Button btnBackBank;
    @FXML
    private Button  btnAllRequests;
    @FXML
    private Integer bankid;
private static Integer hospID;
private static String selectedBloodType;
    public static Integer getHospID(){
        return hospID;
    }

    @FXML
    public void setBankid(Integer bankID){
        bankid = bankID;
    }
    public static List<Integer> getDistinctHospitalIds() throws SQLException {
        List<Integer> hospitalIds = new ArrayList<>();

        try {
            Connection connection = HelloApplication.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("CALL GetDistinctHospitalIds()");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int hospitalId = resultSet.getInt("hospital_id");
                hospitalIds.add(hospitalId);
            }
        }catch (SQLException e) {
            // Handle SQLException, log the error, or throw a custom exception
            e.printStackTrace(); // For demonstration purposes; you might want to log the error instead
        }
        return hospitalIds;

    }
    // Helper method to validate the entered hospital ID
    public static boolean isValidHospitalId(String enteredHospitalId) {
        try {
            int hospitalId = Integer.parseInt(enteredHospitalId);

            // Retrieve valid hospital IDs from the database
            List<Integer> validHospitalIds = getDistinctHospitalIds();

            // Check if the entered hospital ID is in the list of valid IDs
            return validHospitalIds.contains(hospitalId);

        } catch (NumberFormatException | SQLException e) {
            // Handle the exception (e.g., log or show an error message)
            return false;
        }
    }
    @FXML
    private void gotToHospital(ActionEvent e) {
        // Show a prompt for hospital ID
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Enter Hospital ID");
        alert.setHeaderText(null);
        alert.setContentText("Please enter the Hospital ID:");

        TextField hospitalIdField = new TextField();
        hospitalIdField.setPromptText("Hospital ID");
        alert.getDialogPane().setContent(hospitalIdField);

        // Wait for the user to enter the ID or press Cancel
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            String enteredHospitalId = hospitalIdField.getText();

            // Verify the entered hospital ID and proceed if valid
            if (isValidHospitalId(enteredHospitalId)) {
                // Load the reportHospital.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("reportHospital.fxml"));
                try {
                    Parent root = loader.load();
                    ReportHospital reportHospitalController = loader.getController();
                    hospID = Integer.parseInt(enteredHospitalId);
                    reportHospitalController.getAllOffers(hospID);
                    reportHospitalController.setBankidID(bankid);

                    // Get the stage information
                    Stage stage = (Stage) btnHospitalBank.getScene().getWindow();
                    Scene scene = new Scene(root);

                    // Set the new scene onto the stage
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace(); // Handle the exception appropriately
                }
            }else {
                // Show an error message for an invalid hospital ID
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Hospital ID");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("The entered Hospital ID is not valid.");
                errorAlert.showAndWait();
            }
        }
    }
    @FXML
    public void gotToBack(ActionEvent e){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bankMain.fxml"));
            Parent root = loader.load();
            bankMainController second = loader.getController();
            second.setBloodBankID(bankid);

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
    public void gotToAll(ActionEvent e){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bankAllRequests.fxml"));
            Parent root = loader.load();
            BankAllRequests secondController = loader.getController();
            secondController.setBankidID(bankid);
            // Get the stage information
            Stage stage = (Stage) btnAllRequests.getScene().getWindow();
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
            // Create a ChoiceBox with blood types
            ChoiceBox<String> bloodTypeChoiceBox = new ChoiceBox<>();
            bloodTypeChoiceBox.getItems().addAll(
                    "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
            );

            // Create a GridPane to layout the components
            GridPane gridPane = new GridPane();
            gridPane.add(new Label("Select Blood Type:"), 0, 0);
            gridPane.add(bloodTypeChoiceBox, 1, 0);

            // Create an Alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Blood Type Selection");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(gridPane);

            // Show the alert and get the user's choice
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    selectedBloodType = bloodTypeChoiceBox.getValue();
                    System.out.println("Selected Blood Type: " + selectedBloodType);///function to pass blood
                }
            });


            FXMLLoader loader = new FXMLLoader(getClass().getResource("reportBloodType.fxml"));
            try {

                Parent root = loader.load();

                // Get the controller instance from the FXMLLoader
                ReportBloodType reportBloodTypeController = loader.getController();
                reportBloodTypeController.setBankidID(bankid);
                reportBloodTypeController.getAllOffers(selectedBloodType);

                // Get the stage information
                Stage stage = (Stage) btnBloodTypeBank.getScene().getWindow();
                Scene scene = new Scene(root);

                // Set the new scene onto the stage
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace(); // Handle the exception appropriately
            }

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
