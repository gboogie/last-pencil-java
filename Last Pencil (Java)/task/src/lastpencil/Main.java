package lastpencil;

import java.util.Scanner;
import java.util.Random;

public class Main {
    private static int numberOfPencils;
    private static String currentPlayer;
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Setup pencils
        setupPencils(scanner);

        // Setup current player
        setupCurrentPlayer(scanner);

        // Game loop
        while (numberOfPencils > 0) {
            System.out.println("|".repeat(numberOfPencils));

            // Make a move
            makeMove(scanner);

            // Game transition
            String nextPlayer = currentPlayer.equals("John") ? "Jack" : "John";

            if (numberOfPencils != 0) {
                currentPlayer = nextPlayer;
            } else {
                System.out.println(nextPlayer + " won!");
            }
        }

        // Clean up
        scanner.close();
    }

    /**
     * Set up the game's pencils
     *
     * @param scanner the object collecting user input
     */
    private static void setupPencils(Scanner scanner) {
        System.out.println("How many pencils would you like to use:");

        while (numberOfPencils == 0) {
            String s = scanner.nextLine();

            // Input needs to be numeric
            if (!s.matches("\\d+")) {
                System.out.println("The number of pencils should be numeric");
                continue;
            }

            // Input needs to be positive
            int n = Integer.parseInt(s);

            if (n <= 0) {
                System.out.println("The number of pencils should be positive");
                continue;
            }

            numberOfPencils = n;
        }
    }

    /**
     * Set up the game's current player
     *
     * @param scanner the object collecting user input
     */
    private static void setupCurrentPlayer(Scanner scanner) {
        System.out.println("Who will be the first (John, Jack):");

        while (currentPlayer == null) {
            String s = scanner.nextLine();

            // Input needs to be John or Jack
            if (!s.equals("John") && !s.equals("Jack")) {
                System.out.println("Choose between 'John' and 'Jack'");
                continue;
            }

            currentPlayer = s;
        }
    }

    /**
     * Make a move
     *
     * @param scanner the object collecting user input
     */
    private static void makeMove(Scanner scanner) {
        System.out.println(currentPlayer + "'s turn!");

        switch (currentPlayer) {
            case "John" -> johnsMove(scanner);
            case "Jack" -> jacksMove();
        }
    }

    /**
     * Process John's move
     *
     * @param scanner the object collecting user input
     */
    private static void johnsMove(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine();

            // Input needs to be 1, 2, or 3
            if (!s.equals("1") && !s.equals("2") && !s.equals("3")) {
                System.out.println("Possible values: '1', '2' or '3'");
                continue;
            }

            // Input cannot exceed the number of pencils
            int n = Integer.parseInt(s);

            if (n > numberOfPencils) {
                System.out.println("Too many pencils were taken");
                continue;
            }

            numberOfPencils -= n;
            break;
        }
    }

    /**
     * Process Jack's move
     */
    private static void jacksMove() {
        int n = 1;

        // In a losing position
        if (((numberOfPencils - 1) % 4) == 0) { // 1, 5, 9, 13, 17, etc
            if (numberOfPencils != 1) {
                n = random.nextInt(3) + 1;
            }
        } else {
            // In a winning position
            if ((numberOfPencils % 4) == 0) { // 4, 8, 12, 16, 20, etc
                n = 3;
            }

            if (((numberOfPencils + 1) % 4) == 0) { // 3, 7, 11, 15 19, etc
                n = 2;
            }

            if (((numberOfPencils + 2) % 4) == 0) { // 2, 6, 10, 14, 18, etc
                n = 1;
            }
        }

        System.out.println(n);

        numberOfPencils -= n;
    }

}
