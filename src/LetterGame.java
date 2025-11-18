import java.util.ArrayList;
import java.util.Scanner;

public class LetterGame {
    public static ArrayList<String> validWords = Main.getFileData("src/words");
    public static String alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    private Scanner s = new Scanner(System.in);

    private char start = randomLetter();
    private ArrayList<Character> required = new ArrayList<>();

    public LetterGame() {
        return;
    }

    public static char randomLetter() {
        return alphabet.charAt((int) (Math.random() * alphabet.length()));
    }

    public boolean containsRequired(String word) {
        for (char letter: required) {
            if (!word.contains(letter + "")) {
                return false;
            }
        }
        return true;
    }

    public String requiredRepr() {
        if (required.isEmpty()) {
            return "";
        }
        String repr = " Your word must contain: " + required.getFirst();
        for (int i = 1; i < required.size(); i++) {
            repr += ", " + required.get(i);
        }
        return repr;
    }

    public void run() {
        while (required.size() < 5) {
            System.out.println("Your required starting character is " + start + "." + requiredRepr());
            while (true) {
                System.out.print("Please enter a word: ");
                String input = s.nextLine().toUpperCase();
                System.out.println();

                if (input.isEmpty()) {
                    // Player doesn't pass round
                    break;
                }
                if (input.charAt(0) != start) {
                    System.out.println("Invalid Word - Starting Letter is not " + start + ".");
                } else if (!containsRequired(input)) {
                    System.out.println("Invalid Word - Must include all required letters.");
                } else if (!validWords.contains(input.toLowerCase())) {
                    System.out.println("Invalid Word - Not in word list.");
                } else {
                    // Player passes round
                    System.out.println("Round passed.");
                    required.add(start);
                    start = randomLetter();
                    break;
                }
                System.out.println();
            }
        }
    }
}
