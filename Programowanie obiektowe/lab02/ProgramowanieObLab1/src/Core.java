import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class Core extends JFrame {
    JLabel label;
    int diament = 0;
    int proba = 0;
    JTextArea txt;

    public Core(String playername) {
        JButton btn1 = new JButton("Kliknij na mnie");
        JButton btn2 = new JButton("A może tutaj?");
        label = new JLabel("         Prób: " + proba + " Diamenty: " + diament);
        txt = new JTextArea();
        setTitle("Bomba & Diament - Gracz " + playername + ", 20 prób ");
        txt.setEditable(false);

        add(btn1);
        add(btn2);
        add(label);
        add(txt);

        setSize(500, 500);
        setLayout(new GridLayout(2, 2, 5, 5));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleGameLogic(false); // Wywołanie z argumentem false
            }
        });
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleGameLogic(true); // Wywołanie z argumentem true
            }
        });
    }
    private void handleGameLogic(boolean increasedChance) { //metoda i funkcja
        Random rand = new Random();
        boolean randBool = rand.nextBoolean();
        // Zamiana wyniku losowania, jeśli przycisk drugi (btn2) został naciśnięty
        if (increasedChance) {
            randBool = !randBool;
        }
        // Sprawdzamy, czy to jest ostatnia próba (20)
        if (proba >= 20) { // po tej próbie będzie 20

            String endMessage = "Koniec gry! Twój wynik:\nDiamenty: " + diament + " Prób: " + proba +" \nJeśli chcesz grać dalej naciśnij przycisk";
            txt.setText(endMessage);
            label.setText("         Prób: " + proba + " Diamenty: " + diament);
            proba = 0;  // reset prób
            diament = 0;  // reset diamentów
            return; // zakończenie gry
        }
        if (randBool) {
            diament++;
            proba++;
            txt.setText("Brawo, udało się!\nMasz dużo szczęścia");
        } else {
            proba++;
            txt.setText("Niestety, nie tym razem\nSpróbuj ponownie");
        }
        label.setText("         Prób: " + proba + " Diamenty: " + diament);
    }
}
