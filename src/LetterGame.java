import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class LetterGame {
    public static ArrayList<String> validWords = Main.getFileData("src/words");
    public static String alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    private Scanner s = new Scanner(System.in);

    private ArrayList<Character> required = new ArrayList<>();
    private char start = randomLetter();
    private int streak = 0;
    private int points = 0;
    private int reshuffles = 2;
    private int maxPoints;
    private int rounds = 0;

    public LetterGame(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public char randomLetter() {
        ArrayList<Character> remaining = new ArrayList<>();
        for (int i = 0; i < alphabet.length(); i++) {
            if (!required.contains(alphabet.charAt(i))) {
                remaining.add(alphabet.charAt(i));
            }
        }
        return remaining.get((int) (Math.random() * remaining.size()));
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

    public void instructions() {
        System.out.println("This is a Letter Game in which the objective is to form English words that meet certain criteria.");
        System.out.println("Each round, the player is given a random letter and asked to find a word starting with that letter. If they succeed, that letter will be added to a list of required letters that must be used in subsequent rounds.");
        System.out.println("After any round, the player may choose to 'end their streak,' clearing the required list and gaining a sum of points equal to their streak length plus a bonus.");
        System.out.println("If the player fails to find a word, they may respond '!' to the prompt. Their streak will end, but they gain fewer points than if they had previously ended it.");
        System.out.println("Twice during the game, the player may enter '*' to reshuffle the chosen starting letter.");
        System.out.println("Try to reach " + maxPoints + " points in the fewest rounds possible.\n");
    }

    public void endStreak(int addedPoints) {
        points += addedPoints;
        streak = 0;
        required.clear();
        start = randomLetter();
        if (points < maxPoints) {
            System.out.println("You now have " + points + " points!");
        }
    }

    public void run() {
        while (points < maxPoints) {
            rounds += 1;
            System.out.println("Round " + rounds + ":");
            System.out.println("Starting Character: " + start);
            System.out.println("Required Characters: " + requiredRepr());
            while (true) {
                System.out.print("Please enter a word: ");
                String input = s.nextLine().toUpperCase();
                System.out.println();
                if (input.isEmpty()) {
                    // Player enters empty string
                    System.out.println("Invalid. Please submit a word or enter '!' to end streak.");
                } else if (input.equals("!")) {
                    // Player can't find a word - end streak
                    System.out.println("Streak ended.");;
                    endStreak((streak * (streak + 1)) / 4);
                    System.out.println();
                    break;
                } else if (input.equals("*") && reshuffles > 0) {
                    // Player reshuffles letter
                    reshuffles -= 1;
                    System.out.println("Reselecting starting letter. You have " + reshuffles + " reshuffle(s) remaining.\n");
                    start = randomLetter();
                    rounds -= 1;
                    break;
                } else if (input.charAt(0) != start) {
                    System.out.println("Invalid Word - Starting letter is not " + start + ".");
                } else if (!containsRequired(input)) {
                    System.out.println("Invalid Word - Must include all required letters.");
                } else if (!validWords.contains(input.toLowerCase())) {
                    System.out.println("Invalid Word - Not in word list.");
                } else {
                    // Player passes round
                    streak += 1;
                    System.out.println("Round passed.\n");
                    int bonus = (streak * (streak + 1)) / 2 - streak;
                    if (points + streak + bonus >= maxPoints) {
                        points += streak + bonus;
                        break;
                    }
                    System.out.print("Current Streak: " + streak + " (+" + bonus + "). End Streak? (Y/N): ");
                    if (!s.nextLine().equalsIgnoreCase("Y")) {
                        required.add(start);
                        start = randomLetter();
                    } else {
                        endStreak(streak + bonus);
                    }
                    System.out.println();
                    break;
                }
            }
        }
        System.out.println("You've earned " + points + " points!");
        System.out.println("You took a total of " + rounds + " rounds.");
    }
}
