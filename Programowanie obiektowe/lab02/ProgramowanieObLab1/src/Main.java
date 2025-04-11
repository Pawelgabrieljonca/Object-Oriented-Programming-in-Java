import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj swoje imię: ");
        String playername = scanner.nextLine();

       new Core(playername);
    }
}