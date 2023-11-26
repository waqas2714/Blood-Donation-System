package com.example.blooddonationsystem;

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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AllRequestsController implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private TableView<allHospitalRequests> table;
    @FXML
    private TableColumn<allHospitalRequests, Integer> tableColumnRequestId;
    @FXML
    private TableColumn<allHospitalRequests, String> tableColumnBloodType;
    @FXML
    private TableColumn<allHospitalRequests, Integer> tableColumnAmount;
    @FXML
    private TableColumn<allHospitalRequests, Date> tableColumnRequestDate;
    @FXML
    private TableColumn<allHospitalRequests, String> tableColumnStatus;
    private int hospitalId = 1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        tableColumnBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableColumnRequestDate.setCellValueFactory(new PropertyValueFactory<>("dateRequested"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        getAllOffers();
    }


    public void getAllOffers() {
        try {
            Connection conn = HelloApplication.getConnection();
            String sql = "SELECT request_id, blood_type, quantity, request_date, approval_state " +
                    "FROM requestsbyhospitals " +
                    "WHERE hospital_id = ? " +
                    "ORDER BY request_date DESC";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, hospitalId);

            ResultSet resultSet = statement.executeQuery();

            List<allHospitalRequests> requestsList = new ArrayList<>();

            while (resultSet.next()) {
                allHospitalRequests request = new allHospitalRequests();
                request.setRequestId(resultSet.getInt("request_id"));
                request.setBloodType(resultSet.getString("blood_type"));
                request.setAmount(resultSet.getInt("quantity"));
                request.setDateRequested(resultSet.getDate("request_date"));
                request.setStatus(resultSet.getString("approval_state"));

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
