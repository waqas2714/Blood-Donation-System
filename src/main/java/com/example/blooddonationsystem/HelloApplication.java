package com.example.blooddonationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    private static Connection connection;

    @Override
    public void start(Stage stage) throws IOException {
        establishDBConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signupMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Blood Donation System");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            closeDBConnection();
        });

        stage.show();
    }


    private void establishDBConnection() {
        String url = "jdbc:mysql://dbs-project.mysql.database.azure.com:3306/blooddonationsystem";
        String user = "dbsadmin";
        String password = "AzurePassword1#";

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void closeDBConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        launch();
    }
}