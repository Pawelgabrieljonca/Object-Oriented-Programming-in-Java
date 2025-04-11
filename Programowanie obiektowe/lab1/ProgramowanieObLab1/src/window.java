import javax.swing.JButton;  // Import klasy JButton do tworzenia przycisków
import javax.swing.JFrame;   // Import klasy JFrame do tworzenia okien
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;  // Import klasy ActionEvent do obsługi zdarzeń
import java.awt.event.ActionListener; // Import interfejsu ActionListener do obsługi zdarzeń akcji
// Klasa window dziedziczy po JFrame i implementuje ActionListener
public class window extends JFrame implements ActionListener {
    JButton btn;
    JLabel label;
    int licz;
    window() {
        btn = new JButton("Wstecz");
        label = new JLabel("Zobacz teraz");
        btn.addActionListener(this); // Dodanie nasłuchiwacza akcji do przycisku, aby reagować na kliknięcia
        add(btn);
        add(label);
        setLayout(new FlowLayout());
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    // Metoda actionPerformed, wywoływana, gdy zdarzenie akcji (np. kliknięcie) wystąpi
    public void actionPerformed(ActionEvent e) {
        label.setText("Hello world!");
        System.out.println("Hello world!"); // Wydrukowanie tekstu "Hello world!" w konsoli
    }
}
