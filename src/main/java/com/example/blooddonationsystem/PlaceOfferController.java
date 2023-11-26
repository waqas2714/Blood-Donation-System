package com.example.blooddonationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PlaceOfferController implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private ChoiceBox<String> choiceBoxBloodType;
    @FXML
    private TextField txtAmount;
    @FXML
    private DatePicker datePickerNeededBefore;
    private int hospital_id;

    public PlaceOfferController() {
        hospital_id = 1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        // Convert array to an ObservableList
        ObservableList<String> bloodGroupList = FXCollections.observableArrayList(bloodGroups);

        // Set items in the ChoiceBox
        choiceBoxBloodType.setItems(bloodGroupList);

    }


    @FXML
    public void place(ActionEvent event) {
        String bloodType = choiceBoxBloodType.getValue();
        int amount;
        LocalDate dateNeededBefore = datePickerNeededBefore.getValue();
        LocalDate currentDate = LocalDate.now();

        try {
            amount = Integer.parseInt(txtAmount.getText());

            if (amount >= 80 && amount <= 360) {
                if (!dateNeededBefore.isBefore(currentDate)) {
                    Connection connection = HelloApplication.getConnection();
                    // Prepare the SQL query
                    String query = "INSERT INTO RequestsByHospitals (hospital_id, blood_type, quantity, request_date, deadline_date, approval_state) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    // Set values to the query parameters
                    preparedStatement.setInt(1, hospital_id); // Assuming hospital_id is set elsewhere
                    preparedStatement.setString(2, bloodType);
                    preparedStatement.setInt(3, amount);
                    preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // Set current date for request_date
                    preparedStatement.setDate(5, java.sql.Date.valueOf(dateNeededBefore)); // Set dateNeededBefore for deadline_date
                    preparedStatement.setString(6, "pending"); // Initial state is 'pending'

                    // Execute the query
                    preparedStatement.executeUpdate();

                    // Alert for successful order placement
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Order placed successfully!");
                    alert.showAndWait();

                    txtAmount.setText("");
                    datePickerNeededBefore.setValue(null);
                    choiceBoxBloodType.setValue(null);
                } else {
                    // Alert for choosing a past or today's date
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please choose a date in the future!");
                    alert.showAndWait();
                }
            } else {
                // Alert for amount out of range
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter an amount between 80 and 360!");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            // Alert for invalid amount format
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle potential SQL exception (e.g., show error message)
        }
    }

    @FXML
    public void goToHospitalMain(ActionEvent event){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hospitalMain.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}



