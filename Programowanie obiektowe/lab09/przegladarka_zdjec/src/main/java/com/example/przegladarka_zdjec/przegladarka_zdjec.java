package com.example.przegladarka_zdjec;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class przegladarka_zdjec extends Application {

    private int currentImageIndex = 0; // Indeks aktualnie wyświetlanego obrazu
    private List<Image> images = new ArrayList<>(); // Lista obrazów
    private List<String> imageNames = new ArrayList<>(); // Lista nazw obrazów

    @Override
    public void start(Stage primaryStage) {
        // Wczytanie obrazów do listy
        loadImages();

        // Tworzenie komponentów JavaFX
        ImageView imageView = new ImageView();
        Label imageLabel = new Label(); // Etykieta do wyświetlania nazwy obrazu
        Button button = new Button("Wyświetl");

        // Wyświetlenie pierwszego obrazu od razu po uruchomieniu
        if (!images.isEmpty()) {
            imageView.setImage(images.get(currentImageIndex));
            imageLabel.setText(imageNames.get(currentImageIndex)); // Ustawienie nazwy obrazu
        }

        // Ustawienie akcji dla przycisku
        button.setOnAction(event -> {
            if (!images.isEmpty()) {
                // Wyświetlanie kolejnego obrazu
                currentImageIndex = (currentImageIndex + 1) % images.size(); // Cykl po indeksach
                imageView.setImage(images.get(currentImageIndex));
                imageLabel.setText(imageNames.get(currentImageIndex)); // Zaktualizowanie nazwy obrazu
            }
        });

        // Ustawienia ImageView
        imageView.setFitWidth(300);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        // Układ aplikacji
        VBox root = new VBox(10, imageLabel, imageView, button);
        root.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Tworzenie sceny
        Scene scene = new Scene(root, 400, 500);

        // Ustawienia okna
        primaryStage.setTitle("Przeglądarka obrazów");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void loadImages() {
        // Ścieżki do plików graficznych
        String[] imagePaths = {
                "picture1.jpg",
                "picture2.jpg",
                "picture3.jpg",
                "picture4.jpg"
        };

        // Wczytanie obrazów do listy
        for (String path : imagePaths) {
            File file = new File(path);
            if (file.exists()) {
                images.add(new Image(file.toURI().toString()));
                imageNames.add(file.getName()); // Dodanie nazwy pliku do listy nazw
            } else {
                System.out.println("Plik nie istnieje: " + path);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
