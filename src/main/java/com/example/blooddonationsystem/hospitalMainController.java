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
                    pageName = "addOffer.fxml";
                    break;
                }
                case "btnRemoveRequest":{
                    pageName = "removeRequests.fxml";
                    break;
                }
                case "btnOfferMatches":{
                    pageName = "relevantOffers.fxml";
                    break;
                }
                case "btnMyRequests":{
                    pageName = "allRequests.fxml";
                    break;
                }
            }

            try {
                // Load the login.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
                Parent root = loader.load();

                // Get the stage information
                Stage stage = (Stage) clickedButton.getScene().getWindow();
                Scene scene = new Scene(root);

                // Set the new scene onto the stage
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

        }
    }
}
