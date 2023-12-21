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

public class ReportBloodType implements Initializable{
    @FXML
    private Button btnBack;
    @FXML
    private TableView<bloodRequestData> table;
    @FXML
    private Integer bankid;
    @FXML
    private TableColumn<bloodRequestData, Integer> tableColumnRequestId;
    @FXML
    private TableColumn<bloodRequestData, Integer> tableColumnHospitalId;
    @FXML
    private TableColumn<bloodRequestData, Integer> tableColumnAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        tableColumnHospitalId.setCellValueFactory(new PropertyValueFactory<>("hospitalId"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }
    @FXML
    public void  setBankidID(Integer bankID){
        bankid = bankID;
        System.out.println("all requests bankID: "+ bankid);
    }

    public void getAllOffers(String entblood) {
        try {
            Connection conn = HelloApplication.getConnection();
            String sql = "CALL GetRequestsByBlood(?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entblood);

            ResultSet resultSet = statement.executeQuery();

//            List<allPendingRequests> requestsList = new ArrayList<>();

            List<bloodRequestData> requestsList = new ArrayList<>();
            ObservableList<bloodRequestData> observableList = FXCollections.observableArrayList(requestsList);
            table.setItems(observableList);

            while (resultSet.next()) {
                bloodRequestData request= new bloodRequestData();
                request.setRequestId(resultSet.getInt("request_id"));
                request.setHospitalId(resultSet.getInt("hospital_id"));
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
    public void goToViewRequests(ActionEvent event){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewRequests.fxml"));
            Parent root = loader.load();
            viewRequests second = loader.getController();
            second.setBankid(bankid);
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
