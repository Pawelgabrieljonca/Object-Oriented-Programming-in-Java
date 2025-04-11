module com.example.grakolkokrzyzyk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grakolkokrzyzyk to javafx.fxml;
    exports com.example.grakolkokrzyzyk;
}