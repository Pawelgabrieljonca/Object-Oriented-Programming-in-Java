module com.example.pacjentapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pacjentapp to javafx.fxml;
    exports com.example.pacjentapp;
}