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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SignupOtherController implements Initializable {
    @FXML
    private Button btnGoToLogin;
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
                }
            }
//
//
//            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//
//            String insertQuery = "INSERT INTO users (username, password, gender, email, role_id, bloodgroup, location) VALUES (?, ?, ?, ?, ?, ?, ?)";
//            preparedStatement = connection.prepareStatement(insertQuery);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, hashedPassword);
//            preparedStatement.setString(3, gender);
//            preparedStatement.setString(4, email);
//            preparedStatement.setString(5, "5");
//            preparedStatement.setString(6, bloodGroup);
//            preparedStatement.setString(7, city);
//
//            preparedStatement.executeUpdate();
//
//            System.out.println("Signup Successful!");
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Signup Successful");
//            alert.setHeaderText(null);
//            alert.setContentText("Signed up successfully! Please login now.");
//
//            // Show the dialogue
//            alert.showAndWait();
//
//            //Send the user to the login page
//            GoToLogin(null);
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

}
