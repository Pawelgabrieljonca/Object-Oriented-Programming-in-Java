import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class Server extends JFrame {
    private JTextArea displayArea;
    private Map<String, String> questions = new LinkedHashMap<>(); // Zachowuje kolejność pytań
    private int currentQuestionIndex = 1;
    private ServerSocket serverSocket;
    private BlockingQueue<ClientAnswer> answersQueue = new LinkedBlockingQueue<>();
    private boolean gameFinished = false;

    // Klasa do przechowywania odpowiedzi klienta wraz z informacjami o nich
    private static class ClientAnswer {
        String nick;
        String answer;
        String ipAddress;

        public ClientAnswer(String nick, String answer, String ipAddress) {
            this.nick = nick;
            this.answer = answer;
            this.ipAddress = ipAddress;
        }
    }

    public Server() {
        super("QUIZ SERVER");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        loadQuestions("questions.txt");
        startServer();

        setVisible(true);
    }

    private void loadQuestions(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    questions.put(parts[0].trim(), parts[1].trim());
                }
            }
            displayMessage("Wczytano " + questions.size() + " pytań z pliku.");
        } catch (IOException e) {
            displayMessage("Błąd wczytywania pytań: " + e.getMessage());
        }
    }

    private void startServer() {
        // Uruchom wątek producenta
        new Thread(this::producerTask, "ProducerThread").start();

        // Uruchom wątek konsumenta
        new Thread(this::consumerTask, "ConsumerThread").start();
    }

    // Wątek producenta - zajmuje się TYLKO nasłuchiwaniem i dodawaniem odpowiedzi do kolejki
    private void producerTask() {
        try {
            serverSocket = new ServerSocket(5000);
            displayMessage("Serwer uruchomiony, nasłuchiwanie na porcie 5000...");

            while (!gameFinished) {
                // Akceptuj połączenia od klientów
                Socket clientSocket = serverSocket.accept();
                String clientIP = clientSocket.getInetAddress().getHostAddress();

                // Dla każdego klienta utwórz nowy wątek do odbierania odpowiedzi
                new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));

                        // Odczytaj wiadomość od klienta
                        String clientMessage = in.readLine();
                        String[] parts = clientMessage.split("\\|");

                        if (parts.length == 2) {
                            // Dodaj odpowiedź do kolejki blokującej
                            ClientAnswer answer = new ClientAnswer(parts[0], parts[1], clientIP);
                            answersQueue.put(answer);
                        }

                        clientSocket.close();
                    } catch (Exception e) {
                        System.err.println("Błąd odbierania odpowiedzi: " + e.getMessage());
                    }
                }).start();
            }
        } catch (IOException e) {
            displayMessage("Błąd serwera: " + e.getMessage());
        }
    }

    // Wątek konsumenta - analizuje odpowiedzi i steruje aplikacją
    private void consumerTask() {
        // Wyświetl pierwsze pytanie na początku gry
        SwingUtilities.invokeLater(() -> showQuestion(currentQuestionIndex));

        while (!gameFinished) {
            try {
                // Pobierz odpowiedź z kolejki (blokuje się, jeśli kolejka jest pusta)
                ClientAnswer answer = answersQueue.take();

                // Sprawdź odpowiedź
                String currentQuestion = getCurrentQuestion();
                String correctAnswer = questions.get(currentQuestion);

                if (answer.answer.equalsIgnoreCase(correctAnswer)) {
                    // Wyświetl informację o poprawnej odpowiedzi (5)
                    displayMessage(answer.nick + " (" + answer.ipAddress + ") odpowiedział poprawnie :)");

                    // Wyczyść kolejkę z pozostałych odpowiedzi, które stały się nieaktualne
                    answersQueue.clear();

                    // Przejdź do następnego pytania (6)
                    currentQuestionIndex++;
                    if (currentQuestionIndex <= questions.size()) {
                        // Pokaż następne pytanie
                        SwingUtilities.invokeLater(() -> showQuestion(currentQuestionIndex));
                    } else {
                        // Koniec zabawy (8)
                        gameFinished = true;
                        displayMessage("Odpowiedziano już na wszystkie pytania. Koniec zabawy!");
                    }
                } else {
                    // Wyświetl komunikat o niepoprawnej odpowiedzi (7)
                    displayMessage("Nadeszła odpowiedź błędna :(");
                    displayMessage(answer.nick + " (" + answer.ipAddress + ") odpowiedział niepoprawnie");
                }

            } catch (InterruptedException e) {
                displayMessage("Przerwano wątek konsumenta: " + e.getMessage());
            }
        }
    }

    private String getCurrentQuestion() {
        List<String> questionList = new ArrayList<>(questions.keySet());
        return questionList.get(currentQuestionIndex - 1);
    }

    private void showQuestion(int index) {
        String question = getCurrentQuestion();
        displayMessage("Nr " + index + ") " + question);
    }

    private void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            displayArea.append(message + "\n");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Server());
    }
}
