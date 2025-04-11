package com.example.rodziny_wyjatkow;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sprawdzanie statusu zamówienia");

        // Tworzenie elementów interfejsu
        Label label = new Label("Wprowadź status zamówienia ('fulfilled') i status czasu (np. 'on time', 'exceeded'):");
        TextField statusField = new TextField();
        TextField timeField = new TextField();
        Button checkButton = new Button("Sprawdź zamówienie");
        Label resultLabel = new Label();

        // Obsługa przycisku
        checkButton.setOnAction(e -> {
            String status = statusField.getText().trim();
            String timeStatus = timeField.getText().trim();
            Order order = new Order(status, timeStatus);

            try {
                order.checkOrder(); // Wywołanie metody
                resultLabel.setText("Zamówienie zostało pomyślnie zrealizowane.");
            } catch (UnfulfilledOrderException ex) {
                showAlert("Błąd: Zamówienie nie zostało zrealizowane!", ex.getMessage());
                resultLabel.setText("Błąd: " + ex.getMessage());
            } catch (OrderTimeExceededException ex) {
                showAlert("Błąd: Przekroczono czas realizacji zamówienia!", ex.getMessage());
                resultLabel.setText("Błąd: " + ex.getMessage());
            } catch (OrderProcessingException ex) {
                showAlert("Ogólny błąd przetwarzania zamówienia!", ex.getMessage());
                resultLabel.setText("Błąd: " + ex.getMessage());
            }
        });

        // Układ interfejsu
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(label, statusField, timeField, checkButton, resultLabel);

        // Scena i pokazanie okna
        Scene scene = new Scene(layout, 500, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metoda do wyświetlania komunikatów błędu
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd zamówienia");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
