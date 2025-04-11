module com.example.przegladarka_zdjec {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.przegladarka_zdjec to javafx.fxml;
    exports com.example.przegladarka_zdjec;
}