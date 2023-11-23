module com.example.blooddonationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens com.example.blooddonationsystem to javafx.fxml;
    exports com.example.blooddonationsystem;
}