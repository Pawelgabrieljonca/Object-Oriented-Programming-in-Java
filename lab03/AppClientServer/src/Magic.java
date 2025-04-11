import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Magic extends JFrame {
    private JTextArea displayArea;
    private Map<String, String> questions = new LinkedHashMap<>(); // Zachowuje kolejność pytań
    private int currentQuestionIndex = 1;
    private MagicSocket magicSocket;
    private BlockingQueue<Magic.ClientAnswer> answersQueue = new LinkedBlockingQueue<>();
    private boolean gameFinished = false;

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
    public Magic() {
        super("quiz");
        setSize(500, 500);
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
        try(bufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split("\\|");
                if(parts.length == 2){
                    questions.put(parts[0].trim(), parts[1].trim());
                }
            }
            displayMessage("Wczytano"+ questions.size() + " pytań z pliku.");
        }catch (IOException e){
            displayMessage("Błąd wczytywania pytań "+ e.getMessage());
        }
    }
    private void startServer() {
        new Thread(this::producerTask, "ProducerThread").start();

        new Thread(this::consumerTask, "consumerthread").start();
    }
    private void producerTask() {}
    private void consumerTask() {}
    private String getCurrentQuestion() {
        List<String> questionList= new ArrayList<>(questions.keySet());
        return questionList.get(currentQuestionIndex -1);
    }
    private void showQuestion(int index){
        String question = getCurrentQuestion();
        displayMessage("Nr " + index + ": " + question);
    }

    private void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            displayArea.setText(message);
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Magic::new);
    }
}
