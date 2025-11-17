import java.util.ArrayList;
import java.util.Scanner;

public class LetterGame {
    public static ArrayList<String> words = Main.getFileData("src/words");
    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private Scanner s = new Scanner(System.in);

    private char start = randomLetter();
    private char[] required = {};

    public LetterGame() {
        return;
    }

    public static char randomLetter() {
        return alphabet.charAt((int) (Math.random() * alphabet.length()));
    }

    public String requiredRepr() {
        if (required.length() == 0) {
            return "";
        }
        String repr = required[0];
        for (int i = 1; i < required.length(); i++) {

        }
    }

    public void run() {
        System.out.println("Your required starting character is " + start);
        String input = s.nextLine();
    }
}
