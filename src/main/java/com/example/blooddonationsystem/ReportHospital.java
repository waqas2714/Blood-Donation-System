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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportHospital implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private TableView<allPendingRequests> table;
    @FXML
    private TableColumn<allPendingRequests, Integer> tableColumnRequestId;
    @FXML
    private TableColumn<allPendingRequests, String> tableColumnBloodType;
    @FXML
    private TableColumn<allPendingRequests, Integer> tableColumnAmount;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        tableColumnBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }



    public void getAllOffers(int hospitalId) {
        try {
            Connection conn = HelloApplication.getConnection();
            String sql = "CALL GetRequestsByHospitals(?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, hospitalId);

            ResultSet resultSet = statement.executeQuery();

//            List<allPendingRequests> requestsList = new ArrayList<>();

            List<allPendingRequests> requestsList = new ArrayList<>();
            ObservableList<allPendingRequests> observableList = FXCollections.observableArrayList(requestsList);
            table.setItems(observableList);

            while (resultSet.next()) {
                allPendingRequests request = new allPendingRequests();
                request.setRequestId(resultSet.getInt("request_id"));
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

 ///add in back sql
 @FXML
 public void goToViewRequests(ActionEvent event) {
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

