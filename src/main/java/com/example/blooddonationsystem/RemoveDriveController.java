////AHMAD CODE....USE TO APPROVE DATA IN VIEW ALL REQUESTS
package com.example.blooddonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RemoveDriveController implements Initializable {

    @FXML
    private TableView<AddDrive> DriveTable;

    @FXML
    private TableColumn<AddDrive, String> columnDriveName;

    @FXML
    private TableColumn<AddDrive, String> columnDriveLocation;

    @FXML
    private Button btnRemove;

    @FXML
    private Label Lb1;

    @FXML
    private Button btnBack;

    @FXML
    private Integer ngoID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void secondtable(){
        // Set up the columnDriveName to display the drive name
        columnDriveName.setCellValueFactory(new PropertyValueFactory<>("driveName"));

        // Set up the columnDriveLocation to display the drive location
        columnDriveLocation.setCellValueFactory(new PropertyValueFactory<>("driveLocation"));

        try {
            getDrives();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getDrives() throws SQLException {

        Connection conn = HelloApplication.getConnection(); // Establish your database connection

        String Query = "SELECT drive_name, city from " +
                "donationdrives JOIN locations ON donationdrives.location_id = locations.location_id"+
                " WHERE organizer_id = ?";

        PreparedStatement statement = conn.prepareStatement(Query);
        statement.setInt(1, ngoID);
        ResultSet resultSet = statement.executeQuery();
        List<AddDrive> drivesList = new ArrayList<>();

        while (resultSet.next()) {
            AddDrive drive = new AddDrive();
            drive.setDriveName(resultSet.getString("drive_name"));
            drive.setDriveLocation(resultSet.getString("city"));

            drivesList.add(drive);
        }

        DriveTable.getItems().clear();
        DriveTable.getItems().addAll(drivesList);

    }

    public void remove(ActionEvent event) throws SQLException {

        AddDrive SelectedDrive = DriveTable.getSelectionModel().getSelectedItem();

        if(SelectedDrive != null){

            removeItemfromDatabase(SelectedDrive);
            DriveTable.getItems().remove(SelectedDrive);

        }

        else{

            Lb1.setText("kindly select an item to remove.");

        }
    }

    public void removeItemfromDatabase(AddDrive item) throws SQLException {
        Connection conn = HelloApplication.getConnection(); // Establish your database connection

        PreparedStatement statement = conn.prepareStatement("DELETE FROM donationdrives where drive_name = ?");

        statement.setString(1, item.getDriveName());
        statement.executeUpdate();
    }

    @FXML
    public void setNgoID(Integer bankID){
        this.ngoID = bankID;
        System.out.println("removeDrives ngoID: "+ ngoID);
        secondtable();
    }


    public void GoBackToNGOMain(ActionEvent event)
    {
        // Load the ngoMain.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ngoMain.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        NgoMainController secondController = loader.getController();
        secondController.setNgoID(ngoID);
        // Get the stage information
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Scene scene = new Scene(root);

        // Set the new scene onto the stage
        stage.setScene(scene);
        stage.show();
    }
}
