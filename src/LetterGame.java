import java.util.ArrayList;
import java.util.Scanner;

public class LetterGame {
    public static ArrayList<String> validWords = Main.getFileData("src/words");
    public static String alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    private Scanner s = new Scanner(System.in);

    private char start = randomLetter();
    private ArrayList<Character> required = new ArrayList<>();
    private int streak = 0;
    private int points = 0;

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
            return "(None)";
        }
        String repr = "" + required.getFirst();
        for (int i = 1; i < required.size(); i++) {
            repr += ", " + required.get(i);
        }
        return repr;
    }

    public void run() {
        while (required.size() < 5) {
            System.out.println("Starting Character " + start);
            System.out.println("Required Characters: " + requiredRepr());
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
                    streak += 1;
                    System.out.println("Round passed.\n");
                    int bonus = streak * 2 - 1;
                    System.out.print("Current Streak: " + streak + " (+" + bonus + "). End Streak? (Y/N): ");
                    if (!s.nextLine().equalsIgnoreCase("Y")) {
                        required.add(start);
                    } else {
                        required.clear();
                        points += streak + bonus;
                        streak = 0;
                        System.out.println("You now have " + points + " points!");
                    }
                    start = randomLetter();
                    System.out.println();
                    break;
                }
            }
        }
    }
}
