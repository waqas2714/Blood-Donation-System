package com.example.blooddonationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;


public class RemoveRequestsController implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRemoveRequest;
    @FXML
    private TableView<bloodRequest> table;
    @FXML
    private TableColumn<bloodRequest, String> columnBloodType;
    @FXML
    private TableColumn<bloodRequest, Integer> columnAmount;
    @FXML
    private TableColumn<bloodRequest, Integer> columnId;
    @FXML
    private TableColumn<bloodRequest, Date> columnDate;
    int hospitalId;

    {
        hospitalId = 1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("deadlineDate"));

        getData();
    }
    @FXML
    public void getData() {
        try {
            Connection connection = HelloApplication.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT blood_type, quantity, request_id, deadline_date FROM requestsbyhospitals WHERE hospital_id = ? AND approval_state = 'pending'");

            preparedStatement.setInt(1, hospitalId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<bloodRequest> dataList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String bloodType = resultSet.getString("blood_type");
                int amount = resultSet.getInt("quantity");
                int requestId = resultSet.getInt("request_id");
                Date deadlineDate = resultSet.getDate("deadline_date"); // Use getDate for Date values

                bloodRequest request = new bloodRequest();
                request.setBloodType(bloodType);
                request.setAmount(amount);
                request.setId(requestId);
                request.setDate(deadlineDate);

                dataList.add(request);
            }

            table.setItems(dataList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void remove(ActionEvent event){
        bloodRequest selectedRequest = table.getSelectionModel().getSelectedItem();
        if(selectedRequest == null) {
            // Display an error alert, no row selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No request selected.");
            alert.showAndWait();
            return;
        }

        int requestId = selectedRequest.getId();

        try {
            Connection connection = HelloApplication.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM requestsbyhospitals WHERE request_id = ?");

            preparedStatement.setInt(1, requestId);
            preparedStatement.executeUpdate();

            // Remove the selected row from the table
            table.getItems().remove(selectedRequest);

            getData();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception appropriately
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
