
package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class viewStorage implements Initializable {

    @FXML
    public TableView<Map<String, String>> tableView;

    @FXML
    public TableColumn<Map<String, String>, String> bloodtypeBank;

    @FXML
    public TableColumn<Map<String, String>, String> quantityBank;

    @FXML
    private Button btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
    }


    private void initializeTable() {
        bloodtypeBank.setCellValueFactory(cellData -> {
            ObservableValue<String> observableValue = new SimpleStringProperty(cellData.getValue().get("bloodType"));
            return observableValue;
        });

        quantityBank.setCellValueFactory(cellData -> {
            ObservableValue<String> observableValue = new SimpleStringProperty(cellData.getValue().get("quantity"));
            return observableValue;
        });

        // Fetch data from the database
        ObservableList<Map<String, String>> inventoryData = getInventoryData();

        // Populate the TableView with data
        tableView.setItems(inventoryData);
    }

    private ObservableList<Map<String, String>> getInventoryData() {
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();

        // Replace the following JDBC code with your database connection and query
        try {
            int bloodbankid = 0;
            Connection connection = HelloApplication.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT blood_type, quantity FROM inventory where blood_bank_id = ?");
            preparedStatement.setInt(1, bloodbankid);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, String> item = new HashMap<>();
                item.put("blood_type", resultSet.getString("bloodtypeBank"));
                item.put("quantity", resultSet.getString("quantityBank"));
                data.add(item);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return data;
    }

    @FXML
    public void goToBack(ActionEvent event){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bankMain.fxml"));
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



