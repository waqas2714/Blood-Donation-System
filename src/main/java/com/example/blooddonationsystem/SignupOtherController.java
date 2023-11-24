package com.example.blooddonationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SignupOtherController implements Initializable {
    @FXML
    private Button btnGoToLogin;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtWebsite;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtContact;
    @FXML
    private ChoiceBox<String> choiceBoxCity;
    @FXML
    private ChoiceBox<String> choiceBoxRole;
    @FXML
    private Label labelError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> bloodGroupOptions = FXCollections.observableArrayList(
                "Hospital", "NGO", "Blood Bank");
        choiceBoxRole.setItems(bloodGroupOptions);

        //Code for autocomplete
        List<String> cities = Cities.getCities();
        // Add more cities as needed

        txtCity.setOnKeyReleased(event -> {
            String enteredText = txtCity.getText().toLowerCase();

            List<String> suggestions = cities.stream()
                    .filter(city -> city.toLowerCase().startsWith(enteredText))
                    .collect(Collectors.toList());

            choiceBoxCity.setItems(FXCollections.observableArrayList(suggestions));
            choiceBoxCity.show(); // Show the choice box with suggestions
        });

        choiceBoxCity.setOnAction(event -> {
            String selectedCity = choiceBoxCity.getSelectionModel().getSelectedItem();
            if (selectedCity != null) {
                txtCity.setText(selectedCity); // Set txtCity value to the selected city
            }
        });
    }

    @FXML
    public void signup(ActionEvent e) {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String city = txtCity.getText();
        String contact = txtContact.getText();
        String website = txtWebsite.getText();
        String role = choiceBoxRole.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || city.isEmpty() || contact.isEmpty() || role == null) {
            labelError.setText("Please fill in all fields.");
            return;
        }

        // Check email syntax
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            labelError.setText("Invalid Email Address.");
            return;
        }

        // Check contact format
        if (!contact.matches("03\\d{2}-\\d{7}")) {
            labelError.setText("Invalid contact format. Use 03XX-XXXXXXX.");
            return;
        }


        try {
            Connection connection = HelloApplication.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    labelError.setText("Email already used");
                    return;
                }
            }

            List<String> cityList = Cities.getCities();
            if (!cityList.contains(city)) {
                labelError.setText("City should be chosen from the given cities.");
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            String tempRole = "";
            switch (role){
                case "Hospital" : {
                    tempRole = "2";
                    break;
                }
                case "NGO" : {
                    tempRole = "3";
                    break;
                }
                case "Blood Bank" : {
                    tempRole = "4";
                    break;
                }
            }

            //Getting location Id
            String getLocationIdQuery = "SELECT location_id FROM locations WHERE city = ?";
            PreparedStatement locationStatement = connection.prepareStatement(getLocationIdQuery);
            locationStatement.setString(1, city);
            ResultSet locationResult = locationStatement.executeQuery();

            int locationId;
            if (locationResult.next()) {
                locationId = locationResult.getInt("location_id");
            } else {
                labelError.setText("Invalid city.");
                return;
            }

            String insertQuery = "INSERT INTO users (username, password, email, role_id, location_id) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, tempRole);
            preparedStatement.setInt(5, locationId);

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int userId = -1;

            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1); // Retrieve the auto-generated user_id
                System.out.println("Generated User ID: " + userId);
            }

            // Insert data into the Donors table
            String tempTable = "";
            switch (role){
                case "Hospital" : {
                    tempTable = "hospitals";
                    break;
                }
                case "NGO" : {
                    tempTable = "ngos";
                    break;
                }
                case "Blood Bank" : {
                    tempTable = "bloodbanks";
                    break;
                }
            }

            String insertDonorQuery = "INSERT INTO " + tempTable + " (user_id, website) VALUES (?, ?)";
            PreparedStatement donorStatement = connection.prepareStatement(insertDonorQuery);
            donorStatement.setInt(1, userId);
            donorStatement.setString(2, website);

            donorStatement.executeUpdate();


            System.out.println("Signup Successful!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Signup Successful");
            alert.setHeaderText(null);
            alert.setContentText("Signed up successfully! Please login now.");

            // Show the dialogue
            alert.showAndWait();

            //Send the user to the login page
            GoToLogin(null);
        } catch (SQLException ex) {
            labelError.setText("Error signing up. Please try again.");
            ex.printStackTrace();
        }
    }


    @FXML
    private void GoToLogin(ActionEvent event) {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnGoToLogin.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    private void goToSignupMain(ActionEvent event) {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signupMain.fxml"));
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
