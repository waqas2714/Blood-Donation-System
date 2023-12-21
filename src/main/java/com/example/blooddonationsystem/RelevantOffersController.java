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
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RelevantOffersController implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private TableView<relevantOffers> table;
    @FXML
    private TableColumn<relevantOffers, String> columnBloodBank;
    @FXML
    private TableColumn<relevantOffers, String> columnBloodType;
    @FXML
    private TableColumn<relevantOffers, Integer> columnQuantity;
    @FXML
    private TableColumn<relevantOffers, String> columnEmail;
    @FXML
    private TableColumn<relevantOffers, String> columnWebsite;
    @FXML
    private TableColumn<relevantOffers, Date> columnRequestDate;
    @FXML
    private TableColumn<relevantOffers, String> columnContact;
    @FXML
    private Integer hospitalId;

    @FXML
    public void setHospitalID(Integer bankID){
        this.hospitalId = bankID;
        System.out.println("relevant reqs hospID: "+ hospitalId);
        initializetable();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void initializetable(){

        columnBloodBank.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnWebsite.setCellValueFactory(new PropertyValueFactory<>("website"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        columnRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestedOn"));

        getOffers();
    }

    public void getOffers() {

            try {
                Connection conn = HelloApplication.getConnection(); // Establish your database connection

                String callProcedure = "{CALL GetBloodDonationRequests(?)}"; // Stored procedure call

                CallableStatement statement = conn.prepareCall(callProcedure);
                statement.setInt(1, hospitalId); // Set hospitalID parameter

                ResultSet resultSet = statement.executeQuery();

                List<relevantOffers> offersList = new ArrayList<>();

                while (resultSet.next()) {
                    String approvalState = resultSet.getString("approval_state");

                    if ("pending".equals(approvalState)) {
                        relevantOffers offer = new relevantOffers();
                        offer.setName(resultSet.getString("username"));
                        offer.setEmail(resultSet.getString("email"));
                        offer.setWebsite(resultSet.getString("website"));
                        offer.setBloodType(resultSet.getString("blood_type"));
                        offer.setQuantity(resultSet.getInt("quantity"));
                        offer.setContact(resultSet.getString("contact"));
                        offer.setRequestedOn(resultSet.getDate("request_date"));

                        offersList.add(offer);
                    }
                }

                // Assuming your TableView variable is named 'table'
                table.getItems().clear();
                table.getItems().addAll(offersList);

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
            hospitalMainController secondController = loader.getController();
            secondController.setHospitalID(hospitalId);

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
