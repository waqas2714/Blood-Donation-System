package com.example.blooddonationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML
    private ListView<String> listBloodGroup;
    @FXML
    private ListView<String> listRole;
    @FXML
    private Button btnSignupSignup;
    @FXML
    private Button btnLoginSignup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        String[] Role = {"Hospital", "Donor", "Blood Bank"};

        // Create ObservableList from arrays
        ObservableList<String> bloodGroupList = FXCollections.observableArrayList(bloodGroups);
        ObservableList<String> roleList = FXCollections.observableArrayList(Role);

        // Set the ObservableLists to the ListViews
        listBloodGroup.setItems(bloodGroupList);
        listRole.setItems(roleList);
    }

    @FXML
    private void GoToLogin(ActionEvent event) {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnLoginSignup.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
