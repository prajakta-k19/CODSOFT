import java.util.*;
public class NumberGuessingGame {
    public static int MAX_ATTEMPTS = 7;
    public static int MAX_NUMBER = 100;
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        int roundsWon = 0;
        int totalRounds = 0;

        WelcomeScreen();

        do {
            totalRounds++;
            if (playRound()) {
                roundsWon++;
            }
        } while (PlayAgain());

        showGameSummary(roundsWon, totalRounds);
        sc.close();
    }

    private static void WelcomeScreen() {
        System.out.println("===== WELCOME TO THE NUMBER GUESSING GAME!!=====");
        System.out.println("READ THE BELOW MENTIONED RULES:");
        System.out.println("I will think of a number between 1 and " + MAX_NUMBER);
        System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it.");
        System.out.println("Try to guess with as few attempts as possible.");
        System.out.println();
    }

    private static boolean playRound() {
        int numberToGuess = random.nextInt(MAX_NUMBER) + 1;
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            int guess = getUserGuess();
            attempts++;

            if (guess < numberToGuess) {
                System.out.println("Too low!");
            } else if (guess > numberToGuess) {
                System.out.println("Too high!");
            } else {
                System.out.println("Correct! You guessed the number in " + attempts + " attempt(s).");
                String remark;
                if(attempts==1){
                    remark="Perfect!!";
                }
                else if(attempts<=3){
                    remark="Excellent!!";
                }
                else if(attempts<=5){
                    remark="Good";
                }
                else{
                    remark="Barely won!!";
                }
                System.out.println("Your score this round: " + (MAX_ATTEMPTS - attempts + 1));
                System.out.println(remark);
                return true;
            }

            System.out.println("Attempts left: " + (MAX_ATTEMPTS - attempts));
        }

        System.out.println("Out of attempts! The correct number was: " + numberToGuess);
        return false;
    }

    private static int getUserGuess() {
        while (true) {
            System.out.print("Enter your guess (1-" + MAX_NUMBER + "): ");
            try {
                int guess = sc.nextInt();
                if (guess >= 1 && guess <= MAX_NUMBER) {
                    return guess;
                }
                System.out.println("Please enter a number between 1 and " + MAX_NUMBER + ".");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer.");
                sc.next();
            }
        }
    }

    private static boolean PlayAgain() {
        System.out.print("\nDo you want to play another round? (yes/no): ");
        String response= sc.next().trim().toLowerCase();
        return response.equals("yes")|| response.equals("y");
    }   

    private static void showGameSummary(int roundsWon, int totalRounds) {
        System.out.println("\n    GAME SUMMARY      ");
        System.out.println("Total Rounds Played: " + totalRounds);
        System.out.println("Rounds Won: " + roundsWon);
        System.out.println("Win Rate: " + (int)((roundsWon * 100.0) / totalRounds) + "%");
        System.out.println("Thanks for playing! Goodbye!");
    }
} 