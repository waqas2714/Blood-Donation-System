package com.example.blooddonationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    private Connection connection;

    @Override
    public void start(Stage stage) throws IOException {
        establishDBConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("removeDrive.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            closeDBConnection();
        });

        stage.show();
    }

    private void establishDBConnection() {
        String url = "jdbc:mysql://dbs-project.mysql.database.azure.com:3306/test";
//        String url = "jdbc:mysql://192.168.1.7:3306/testdb";
        String user = "dbsadmin";
        String password = "AzurePassword1#";

        try {
            // Establish the connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM names");

            // Iterate through the result set and create User objects
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                System.out.println(name);
            }

//             Close connections
            resultSet.close();
            statement.close();
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

    public static void main(String[] args) {
        launch();
    }
}