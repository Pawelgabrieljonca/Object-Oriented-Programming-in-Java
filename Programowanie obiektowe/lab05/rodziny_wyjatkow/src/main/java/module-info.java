module com.example.rodziny_wyjatkow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rodziny_wyjatkow to javafx.fxml;
    exports com.example.rodziny_wyjatkow;
}