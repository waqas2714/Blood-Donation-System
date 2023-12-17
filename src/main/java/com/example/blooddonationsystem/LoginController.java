package com.example.blooddonationsystem;

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
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btnSignupLogin;

    @FXML
    private Button btnLoginLogin;

    @FXML
    private TextField txtEmailLogin;

    @FXML
    private TextField txtPasswordLogin;
    @FXML
    private Label labelError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void login(ActionEvent e) {
        String email = txtEmailLogin.getText();
        String password = txtPasswordLogin.getText();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        // Check email syntax
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            System.out.println("Invalid Email Address.");
            return;
        }

        try {
            Connection connection = HelloApplication.getConnection();

            // Check if email is present in the database
            PreparedStatement emailCheckStatement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?");
            emailCheckStatement.setString(1, email);
            ResultSet emailCheckResult = emailCheckStatement.executeQuery();

            if (emailCheckResult.next()) {
                int count = emailCheckResult.getInt(1);

                if (count > 0) {
                    // Email is present in the database, check for the password
                    PreparedStatement passwordStatement = connection.prepareStatement("SELECT password, role_id FROM users WHERE email = ?");
                    passwordStatement.setString(1, email);
                    ResultSet passwordResult = passwordStatement.executeQuery();

                    if (passwordResult.next()) {
                        // Password exists in the database
                        String storedPassword = passwordResult.getString("password");

                        // Check if entered password is correct
                        if (BCrypt.checkpw(password, storedPassword)) {
                            // Password is correct
                            System.out.println("Login Successful!");

                            // Retrieve role ID and load corresponding FXML
                            String storedRoleId = passwordResult.getString("role_id");
                            loadFXMLForRole(storedRoleId);
                        } else {
                            // Password is incorrect
                            System.out.println("Password is incorrect");
                        }
                    }
                } else {
                    // Email not found in the database
                    System.out.println("Email not found");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error logging in. Please try again.");
            ex.printStackTrace();
        }
    }

    private void loadFXMLForRole(String roleId) {
        switch (roleId) {
            case "1":
                // Load donorMain.fxml for role 1 (Donor)
                loadFXML("donorMain.fxml");
                break;
            case "2":
                // Load hospitalMain.fxml for role 2 (Hospital)
                loadFXML("hospitalMain.fxml");
                break;
            case "3":
                // Load ngoMain.fxml for role 3 (NGO)
                loadFXML("ngoMain.fxml");
                break;
            case "4":
                // Load bankMain.fxml for role 4 (Blood Bank)
                loadFXML("bankMain.fxml");
                break;
            default:
                // Handle unexpected role ID
                System.out.println("Unexpected role ID");
                break;
        }
    }

    @FXML
    private void GoToSignup(ActionEvent event) {
        try {
            // Load the signupMain.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signupMain.fxml"));
            Parent root = loader.load();

            // Get the stage information
            Stage stage = (Stage) btnSignupLogin.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene onto the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    private void loadFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            Stage stage = (Stage) btnLoginLogin.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}

