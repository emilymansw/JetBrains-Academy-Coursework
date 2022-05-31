package bullscows;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int digits = -1;
        while (digits < 1){
            String digitsInput = scanner.next();
            try{
                digits = Integer.parseInt(digitsInput);
                if(digits < 1){
                    System.out.println("Error, length cannot be less than 1.");
                    System.exit(1);
                }
            } catch (Exception e){
                System.out.printf("Error: \"%s\" isn't a valid number.", digitsInput);
                System.exit(1);
            }
        }

        System.out.println("Input the number of possible symbols in the code:");
        int numOfSymbols = scanner.nextInt();
        while (numOfSymbols < digits){
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", digits, numOfSymbols);
            System.exit(1);
        }
        while (numOfSymbols > 36){
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(1);
        }

        int letterUpperBound = numOfSymbols - 10 + 97 - 1;
        StringBuilder secretCodeStars = new StringBuilder();
        for (int i = 0 ; i < digits; i++){
            secretCodeStars.append('*');
        }
        if(letterUpperBound < 97){
            if(numOfSymbols <= 10){
                System.out.printf("The secret is prepared: %s (0-%d).\n", secretCodeStars.toString(), numOfSymbols - 1);
            } else {
                System.out.printf("The secret is prepared: %s (0-%d, a).\n", secretCodeStars.toString(), numOfSymbols - 1);
            }
        } else {
            System.out.printf("The secret is prepared: %s (0-9, %c-%c).\n", secretCodeStars.toString(), (char) 97,(char) (numOfSymbols - 10 + 97 - 1));
        }

        ArrayList<Character> allPossibleSymbols = new ArrayList<>();
        for(int i = 0; i < numOfSymbols; i++){
            if(i < 10){
                allPossibleSymbols.add((char) (48 + i));
            } else {
                allPossibleSymbols.add((char) (i - 10 + 97));
            }
        }


        StringBuilder secretNumber = new StringBuilder();
        ArrayList<Character> usedSymbols = new ArrayList<>();
        char symbol;

        for(int i = 0; i < digits; i++){
            do{
                symbol = allPossibleSymbols.get((int) (Math.random() * (numOfSymbols - 1) + 1));
            } while (usedSymbols.contains(symbol));
            secretNumber.append(symbol);
            usedSymbols.add(symbol);
        }

        System.out.println("Okay, let's start a game!\n");
        boolean gameEnded = false;
        int turn = 1;

        while (!gameEnded){
            System.out.printf("Turn %d:\n", turn);
            int numOfCow = 0;
            int numOfBull = 0;
            String guess = scanner.next();
            String secretNumberStr = secretNumber.toString();
            ArrayList<Integer> bullPlace = new ArrayList<Integer>();
            if (guess.equals(secretNumberStr)){
                numOfCow = digits;
                numOfBull = digits;

            } else {
                for(int i = 0; i < digits; i++){
                    if(guess.charAt(i) == secretNumberStr.charAt(i)){
                        numOfBull++;
                        bullPlace.add(i);
                    }
                }
                for(int i = 0; i < digits; i++){
                    for(int n = 0; n < digits; n++){
                        if(guess.charAt(i) == secretNumberStr.charAt(i)){
                            if(bullPlace.contains(n)){
                                continue;
                            }
                            numOfCow++;
                        }
                    }
                }
            }
            if(numOfBull == digits){
                gameEnded = true;
                System.out.printf("Grade: %d bulls\n Congratulations! You guessed the secret code.", digits);
            } else {
                System.out.printf("Grade: %d bull and %d cow\n", numOfBull, numOfCow);

            }

            turn++;

        }

    }


}
