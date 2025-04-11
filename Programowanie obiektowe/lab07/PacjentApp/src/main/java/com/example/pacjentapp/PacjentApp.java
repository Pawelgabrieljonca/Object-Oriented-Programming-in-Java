package com.example.pacjentapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PacjentApp extends Application {

    // Kolekcja pacjentów
    private final List<Pacjent> pacjenci = new ArrayList<>();

    // Zmienna do generowania unikalnych ID dla pacjentów
    private int currentId = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Pola tekstowe do wprowadzania danych pacjenta
        TextField imieField = new TextField();
        imieField.setPromptText("Imię");

        TextField nazwiskoField = new TextField();
        nazwiskoField.setPromptText("Nazwisko");

        TextField wiekField = new TextField();
        wiekField.setPromptText("Wiek");

        // Przycisk dodawania pacjenta
        Button dodajButton = new Button("Dodaj do kolekcji");
        dodajButton.setOnAction(e -> {
            try {
                String imie = imieField.getText();
                String nazwisko = nazwiskoField.getText();
                int wiek = Integer.parseInt(wiekField.getText());

                Pacjent pacjent = new Pacjent(currentId++, imie, nazwisko, wiek);
                pacjenci.add(pacjent);

                // Czyścimy pola tekstowe
                imieField.clear();
                nazwiskoField.clear();
                wiekField.clear();

                System.out.println("Dodano pacjenta: " + pacjent.toJson());
            } catch (NumberFormatException ex) {
                System.err.println("Wiek musi być liczbą całkowitą!");
            }
        });

        // ChoiceBox do wyboru kryterium sortowania
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Nazwiska", "Imienia", "Wiek", "Kolejność dodania");
        choiceBox.setValue("Nazwiska");

        // Przycisk wyświetlania pacjentów
        Button wyswietlButton = new Button("Wyświetl pacjentów");
        wyswietlButton.setOnAction(e -> {
            List<Pacjent> sortedList = new ArrayList<>(pacjenci);

            String sortingCriteria = choiceBox.getValue();
            switch (sortingCriteria) {
                case "Nazwiska":
                    sortedList.sort(Comparator.comparing(Pacjent::getNazwisko));
                    printPatients("Nazwiska", sortedList);
                    break;
                case "Imienia":
                    sortedList.sort(Comparator.comparing(Pacjent::getImie));
                    printPatients("Imienia", sortedList);
                    break;
                case "Wiek":
                    sortedList.sort(Comparator.comparingInt(Pacjent::getWiek));
                    printPatients("Wiek", sortedList);
                    break;
                case "Kolejność dodania":
                    sortedList.sort(Comparator.comparingInt(Pacjent::getId));
                    printPatients("Kolejność dodania", sortedList);
                    break;
            }
        });

        // Układ GUI
        VBox root = new VBox(10, imieField, nazwiskoField, wiekField, dodajButton, choiceBox, wyswietlButton);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Dane Pacjenta");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metoda do wyświetlania pacjentów z tytułem sortowania
    private void printPatients(String sortingCriteria, List<Pacjent> patients) {
        System.out.println("======================================");
        switch (sortingCriteria) {
            case "Nazwiska":
                System.out.println("Sortowanie według nazwisk:");
                break;
            case "Imienia":
                System.out.println("Sortowanie według imion:");
                break;
            case "Wiek":
                System.out.println("Sortowanie według wieku:");
                break;
            case "Kolejność dodania":
                System.out.println("Sortowanie według kolejności dodania:");
                break;
            default:
                System.out.println("Nieznane kryterium sortowania:");
                break;
        }

        for (Pacjent pacjent : patients) {
            System.out.println(pacjent.toJson());
        }
        System.out.println("======================================");
    }
}

class Pacjent {
    private final int id; // Id pacjenta
    private String imie;
    private String nazwisko;
    private int wiek;

    public Pacjent(int id, String imie, String nazwisko, int wiek) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wiek = wiek;
    }
    public int getId() {
        return id;
    }
    public String getImie() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public int getWiek() {
        return wiek;
    }
    public void setWiek(int wiek) {
        this.wiek = wiek;
    }
    // Ręczne formatowanie JSON
    public String toJson() {
        return String.format("[\n \"id\": %d,\n \"imie\": \"%s\",\n \"nazwisko\": \"%s\",\n \"wiek\": %d\n]2123", id, imie, nazwisko, wiek);
    }
}
