/*package com.example.blooddonationsystem;
import com.example.blooddonationsystem.RequestData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ReportHospital {
    @FXML
    private TableView<RequestData> hospitalReport;

    @FXML
    private TableColumn<RequestData, String> bloodType;

    @FXML
    private TableColumn<RequestData, Integer> quantity;

    @FXML
    private TableColumn<RequestData, Integer> requestID;
    @FXML
    private Button btnBack;

    int hospitalId;

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    // Getter method to retrieve the hospital ID if needed
    public int getHospitalId() {
        return this.hospitalId;
    }
    /*@FXML
    public void initialize() {
        // Call the method when the controller is initialized
        getAllPendingOffers();
    }
    @FXML
    public void getAllPendingOffers() {
        // Clear existing items in the TableView
        hospitalReport.getItems().clear();

        try {
            // Call the stored procedure to get requests by hospitals
            List<RequestData> requestDataList = getRequestsByHospitals(hospitalId);
            requestDataList.forEach(data -> System.out.println(data.getBloodType() + ", " + data.getQuantity() + ", " + data.getRequestID()));
            // Populate the TableView with the retrieved data
            ObservableList<RequestData> data = FXCollections.observableArrayList(requestDataList);
            hospitalReport.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
/*
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
        bloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        getAllOffers();
        }


public void getAllOffers() {
        try {
        Connection conn = HelloApplication.getConnection();
        String sql = "CALL GetRequestsByHospitals(?);";

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

private List<RequestData> getRequestsByHospitals(int hospitalId) throws SQLException {
        List<RequestData> requestDataList = new ArrayList<>();

        try (Connection connection = HelloApplication.getConnection()) {
        String query = "CALL GetRequestsByHospitals(?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, hospitalId);

        try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
        String bloodType = resultSet.getString("blood_type");
        int quantity = resultSet.getInt("quantity");
        int requestID = resultSet.getInt("request_id");

        RequestData requestData = new RequestData(bloodType, quantity, requestID);
        requestDataList.add(requestData);
        }
        }
        }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return requestDataList;
        }


        }
        */



/*   @FXML////////////////////////////////////////////
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
    }*////////////////////////////////////




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
    int hospitalId;

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    // Getter method to retrieve the hospital ID if needed
    public int getHospitalId() {
        return this.hospitalId;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        tableColumnBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        getAllOffers();
    }


    public void getAllOffers() {
        try {
            Connection conn = HelloApplication.getConnection();
            String sql = "CALL GetRequestsByHospitals(?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, hospitalId);

            ResultSet resultSet = statement.executeQuery();

            List<allPendingRequests> requestsList = new ArrayList<>();

            while (resultSet.next()) {
                allPendingRequests request = new allPendingRequests();
                request.setRequestId(resultSet.getInt("request_id"));
                request.setBloodType(resultSet.getString("blood_type"));
                request.setAmount(resultSet.getInt("quantity"));

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

