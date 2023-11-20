package com.example.blooddonationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application {
    private Connection connection;

    @Override
    public void start(Stage stage) throws IOException {
//        establishDBConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("removeDrive.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);

//        stage.setOnCloseRequest(event -> {
//            closeDBConnection();
//        });

        stage.show();
    }

    private void establishDBConnection() {
        String dbHost = "192.168.100.9";
        String dbName = "blooddonationsystem";
        String username = "dbsadmin";
        String password = "dbssucks";
        String url = "jdbc:mysql://" + dbHost + ":3306" + "/" + dbName;

        try {
            connection = DriverManager.getConnection(url, username, password);
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

    public static void main(String[] args) {
        launch();
    }
}
