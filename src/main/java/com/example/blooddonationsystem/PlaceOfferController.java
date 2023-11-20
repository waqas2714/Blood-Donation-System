package com.example.blooddonationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaceOfferController implements Initializable {
    @FXML
    ListView<String> listViewBloodGroup;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

        // Create ObservableList from arrays
        ObservableList<String> bloodGroupList = FXCollections.observableArrayList(bloodGroups);

        // Set the ObservableLists to the ListViews
        listViewBloodGroup.setItems(bloodGroupList);
    }
}
