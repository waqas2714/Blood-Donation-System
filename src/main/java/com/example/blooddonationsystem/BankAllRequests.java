package com.example.blooddonationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BankAllRequests implements Initializable{
    @FXML
    private Button btnBack;
    @FXML
    private TableView<allBankRequests> table;
    @FXML
    private TableColumn<allBankRequests, Integer> tableColumnRequestId;
    @FXML
    private TableColumn<allBankRequests, Integer> tableColumnHospitalId;
    @FXML
    private TableColumn<allBankRequests, Integer> tableColumnAmount;
    @FXML
    private TableColumn<allBankRequests, String> tableColumnBloodType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        tableColumnHospitalId.setCellValueFactory(new PropertyValueFactory<>("hospitalId"));
        tableColumnBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        getAllOffers();
    }


    public void getAllOffers() {
        try {
            Connection conn = HelloApplication.getConnection();
            String sql = "CALL GetAllRequests()";

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

//            List<allPendingRequests> requestsList = new ArrayList<>();

            List<allBankRequests> requestsList = new ArrayList<>();
            ObservableList<allBankRequests> observableList = FXCollections.observableArrayList(requestsList);
            table.setItems(observableList);

            while (resultSet.next()) {
                allBankRequests request= new allBankRequests();
                request.setRequestId(resultSet.getInt("request_id"));
                request.setHospitalId(resultSet.getInt("hospital_id"));
                request.setQuantity(resultSet.getInt("quantity"));
                request.setBloodType(resultSet.getString("blood_type"));
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
    public void goToViewRequests(ActionEvent event){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewRequests.fxml"));
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
