package com.example.grakolkokrzyzyk;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
public class HelloController {
    @FXML
    private Label welcomeText; // Etykieta z informacją o stanie gry
    @FXML
    private Button[][] boardButtons = new Button[3][3]; // Przyciski planszy 3x3

    private boolean isXTurn = true; // Określa, czy ruch należy do gracza X

    @FXML
    private void initialize() {
        boardButtons[0][0] = button00; boardButtons[0][1] = button01; boardButtons[0][2] = button02; // Inicjalizacja wiersza 1
        boardButtons[1][0] = button10; boardButtons[1][1] = button11; boardButtons[1][2] = button12; // Inicjalizacja wiersza 2
        boardButtons[2][0] = button20; boardButtons[2][1] = button21; boardButtons[2][2] = button22; // Inicjalizacja wiersza 3

        for (int i = 0; i < 3; i++) { // Ustawienie akcji dla przycisków
            for (int j = 0; j < 3; j++) {
                int r = i, c = j;
                boardButtons[i][j].setOnAction(event -> handleMove(r, c)); // Obsługa kliknięcia
            }
        }
        updateTurnLabel(); // Wyświetl gracza, który zaczyna
    }
    private void handleMove(int row, int col) {
        if (!boardButtons[row][col].getText().isEmpty()) return; // Ignoruj, jeśli pole zajęte

        boardButtons[row][col].setText(isXTurn ? "X" : "O"); // Ustaw X lub O
        boardButtons[row][col].setDisable(true); // Zablokuj pole po użyciu

        if (checkWin()) { // Sprawdzenie wygranej
            welcomeText.setText("Gracz " + (isXTurn ? "X" : "O") + " wygrał!");
            disableBoard(); // Zablokowanie planszy po wygranej
        } else if (isBoardFull()) { // Sprawdzenie remisu
            welcomeText.setText("Remis!");
        } else {
            isXTurn = !isXTurn; // Zmiana gracza
            updateTurnLabel(); // Aktualizacja informacji o graczu
        }
    }
    private void updateTurnLabel() {
        welcomeText.setText("Ruch gracza " + (isXTurn ? "X" : "O")); // Wyświetl aktualnego gracza
    }
    private boolean checkWin() {
        for (int i = 0; i < 3; i++) { // Sprawdzenie wygranej w wierszach i kolumnach
            if (checkCombination(boardButtons[i][0], boardButtons[i][1], boardButtons[i][2]) ||
                    checkCombination(boardButtons[0][i], boardButtons[1][i], boardButtons[2][i])) {
                return true;
            }
        }
        return checkCombination(boardButtons[0][0], boardButtons[1][1], boardButtons[2][2]) || // Sprawdzenie przekątnych
                checkCombination(boardButtons[0][2], boardButtons[1][1], boardButtons[2][0]);
    }
    private boolean checkCombination(Button b1, Button b2, Button b3) {
        return !b1.getText().isEmpty() && b1.getText().equals(b2.getText()) && b2.getText().equals(b3.getText()); // Sprawdź, czy pola są takie same
    }
    private boolean isBoardFull() {
        for (Button[] row : boardButtons) { // Sprawdzenie, czy wszystkie pola są zajęte
            for (Button button : row) {
                if (button.getText().isEmpty()) return false;
            }
        }
        return true;
    }
    private void disableBoard() {
        for (Button[] row : boardButtons) { // Zablokuj wszystkie przyciski
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }
    @FXML
    private void resetGame() {
        for (int i = 0; i < 3; i++) { // Reset planszy
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j].setText(""); // Usuń tekst
                boardButtons[i][j].setDisable(false); // Odblokuj przycisk
            }
        }
        isXTurn = true; // Resetuj kolejność graczy
        updateTurnLabel(); // Wyświetl komunikat startowy
    }
    // Przyciski planszy
    @FXML private Button button00, button01, button02, button10, button11, button12, button20, button21, button22;
}
