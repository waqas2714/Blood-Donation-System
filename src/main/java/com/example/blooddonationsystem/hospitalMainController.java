package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class hospitalMainController {
    @FXML
    private Button btnPlaceRequest;
    @FXML
    private Button btnRemoveRequest;
    @FXML
    private Button btnOfferMatches;
    @FXML
    private Button btnMyRequests;
    @FXML
    private Integer hospitalID;
    @FXML
    private Button btnLogOut;

    @FXML
    public void setHospitalID(Integer bankID){
        this.hospitalID = bankID;
        System.out.println("main hospID: "+ hospitalID);
    }

    @FXML
    public void goToLogin(ActionEvent event){
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnLogOut.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void goToPage(ActionEvent event){
        Object source = event.getSource();
        String pageName = "";
        Button clickedButton;
        if (source instanceof Button) {
            // Cast the source to Button to access its properties
            clickedButton = (Button) source;

            // Get the ID of the clicked button
            String buttonId = clickedButton.getId();
            switch (buttonId){
                case "btnPlaceRequest":{
                    //pageName = "addOffer.fxml";
                    try {
                        // Load the login.fxml file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("addOffer.fxml"));
                        Parent root = loader.load();
                        PlaceOfferController secondController = loader.getController();
                        secondController.setHospitalID(hospitalID);
                        // Get the stage information
                        Stage stage = (Stage) clickedButton.getScene().getWindow();
                        Scene scene = new Scene(root);

                        // Set the new scene onto the stage
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace(); // Handle the exception appropriately
                    }
                    break;
                }
                case "btnRemoveRequest":{
                    //pageName = "removeRequests.fxml";
                    try {
                        // Load the login.fxml file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("removeRequests.fxml"));
                        Parent root = loader.load();
                        RemoveRequestsController secondController = loader.getController();
                        secondController.setHospitalID(hospitalID);
                        // Get the stage information
                        Stage stage = (Stage) clickedButton.getScene().getWindow();
                        Scene scene = new Scene(root);

                        // Set the new scene onto the stage
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace(); // Handle the exception appropriately
                    }
                    break;
                }
                case "btnOfferMatches":{
                    //pageName = "relevantOffers.fxml";
                    try {
                        // Load the login.fxml file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("relevantOffers.fxml"));
                        Parent root = loader.load();
                        RelevantOffersController secondController = loader.getController();
                        secondController.setHospitalID(hospitalID);
                        // Get the stage information
                        Stage stage = (Stage) clickedButton.getScene().getWindow();
                        Scene scene = new Scene(root);

                        // Set the new scene onto the stage
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace(); // Handle the exception appropriately
                    }
                    break;
                }
                case "btnMyRequests":{
                    //pageName = "allRequests.fxml";
                    try {
                        // Load the login.fxml file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("allRequests.fxml"));
                        Parent root = loader.load();
                        AllRequestsController secondController = loader.getController();
                        secondController.setHospitalID(hospitalID);
                        // Get the stage information
                        Stage stage = (Stage) clickedButton.getScene().getWindow();
                        Scene scene = new Scene(root);

                        // Set the new scene onto the stage
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace(); // Handle the exception appropriately
                    }
                    break;
                }
            }

        }
    }
}
