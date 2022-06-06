package tictactoe;


import java.util.*;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);
    private static final char[][] table = new char[3][3];
    static Random random = new Random();

    public static void setTable() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                table[i][j] = ' ';
            }
        }
    }

    public void printBoard() {
        System.out.println("---------");

        for (char[] i : table) {
            System.out.print("| ");
            for (char j : i) {
                System.out.print(j + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");
    }

    public void humanMove(char symbol) {
        while (true) {
            System.out.print("Enter the coordinates: ");
            String[] coordinates = scanner.nextLine().split(" ");

            try {
                int firstCoordinate = Integer.parseInt(coordinates[0]) - 1;
                int secondCoordinate = Integer.parseInt(coordinates[1]) - 1;

                if (table[firstCoordinate][secondCoordinate] != ' ') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    table[firstCoordinate][secondCoordinate] = symbol;
                    break;
                }
            } catch (NumberFormatException numberFormatException) {
                System.out.println("You should enter numbers!");
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
        }
    }

    public void computerMove(char symbol) {
        while (true) {
            int firstCoordinate = random.nextInt(3);
            int secondCoordinate = random.nextInt(3);

            if (table[firstCoordinate][secondCoordinate] == ' ') {
                table[firstCoordinate][secondCoordinate] = symbol;
                break;
            }
        }
    }

    public boolean hasWon(char symbol) {
        boolean flag = false;
        for (int i = 0; i < table.length; i++) {
            flag = flag || Arrays.equals(table[i], new char[]{symbol, symbol, symbol}) || (table[0][i] == symbol && table[1][i]
                    == symbol && table[2][i] == symbol);
        }

        return flag || (table[0][0] == symbol && table[1][1] == symbol && table[2][2] == symbol) ||
                (table[0][2] == symbol && table[1][1] == symbol && table[2][0] == symbol);
    }

    public boolean gameFinished() {
        for (char[] i : table) {
            for (char j : i) {
                if (j == ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    public static char[][] getTable() {
        return table;
    }

    public void makeAIMove(int[] coordinates, char symbol) {
        table[coordinates[0]][coordinates[1]] = symbol;
    }
}
