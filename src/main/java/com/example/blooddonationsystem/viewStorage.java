
package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.sql.*;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class viewStorage implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private TableView<allStorageRequests> table;
    @FXML
    private TableColumn<allStorageRequests, String> tableColumnBloodType;
    @FXML
    private TableColumn<allStorageRequests, Integer> tableColumnAmount;

    @FXML
    private Integer bloodBankID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Leave this method empty for now
    }

    @FXML
    public void setBloodBankID(Integer bankID) {
        this.bloodBankID = bankID;

        // Now that bloodBankID is set, initialize the TableView and retrieve data
        initializeTableView();
        getInventory();
    }

    private void initializeTableView() {
        tableColumnBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    public void getInventory() {
        try {
            System.out.println("viewStorage bloodbankID: "+ bloodBankID);
            Connection conn = HelloApplication.getConnection();
            String sql = "CALL GetInventory(?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bloodBankID);


            ResultSet resultSet = statement.executeQuery();

//            List<allPendingRequests> requestsList = new ArrayList<>();

            List<allStorageRequests> requestsList = new ArrayList<>();
            ObservableList<allStorageRequests> observableList = FXCollections.observableArrayList(requestsList);
            table.setItems(observableList);

            while (resultSet.next()) {
                allStorageRequests request = new allStorageRequests();
                request.setBloodType(resultSet.getString("blood_type"));
                request.setQuantity(resultSet.getInt("quantity"));

                requestsList.add(request);
            }

            // Assuming your TableView variable is named 'table'
            table.getItems().clear();
            table.getItems().addAll(requestsList);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle potential exceptions more gracefully in your application
        }
    }

    @FXML
    public void goToBack(ActionEvent event){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bankMain.fxml"));
            Parent root = loader.load();
            bankMainController secondController = loader.getController();
            secondController.setBloodBankID(bloodBankID);
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



