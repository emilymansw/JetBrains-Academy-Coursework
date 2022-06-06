package tictactoe;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static String[] commands;
    private static final Game[] gamePlayers = new Game[2];
    private static final String[] players = new String[2];
    private static String difficultyLevel;

    public static void main(String[] args) {
        gameCommands();

        if (commands.length == 3) {
            setPlayer();
            gameLoop();
        }

        System.exit(0);
    }

    public static void gameLoop() {
        int count = 0;

        while (true) {
            if (count % 2 == 0) {
                if ("computer".equals(players[0]) && "easy".equals(difficultyLevel)) {
                    gamePlayers[0].printBoard();
                    ((EasyAI) gamePlayers[0]).makeMove();
                    if (((EasyAI) gamePlayers[0]).checkWinner()) {
                        gamePlayers[0].printBoard();
                        System.out.println("X wins");
                        break;
                    } else if (gamePlayers[0].gameFinished()) {
                        gamePlayers[0].printBoard();
                        System.out.println("Draw");
                        break;
                    }
                } else if ("computer".equals(players[0]) && "medium".equals(difficultyLevel)) {
                     gamePlayers[0].printBoard();
                    ((MediumAI) gamePlayers[0]).makeMove();

                    if (((MediumAI) gamePlayers[0]).checkWinner()) {
                        gamePlayers[0].printBoard();
                        System.out.println("X wins");
                        break;
                    } else if (gamePlayers[0].gameFinished()) {
                        gamePlayers[0].printBoard();
                        System.out.println("Draw");
                        break;
                    }
                } else if ("computer".equals(players[0]) && "hard".equals(difficultyLevel)) {
                    gamePlayers[0].printBoard();
                    ((HardAI) gamePlayers[0]).makeMove();
                    if (((HardAI) gamePlayers[0]).checkWinner()) {
                        gamePlayers[0].printBoard();
                        System.out.println("X wins");
                        break;
                    } else if (gamePlayers[0].gameFinished()) {
                        gamePlayers[0].printBoard();
                        System.out.println("Draw");
                        break;
                    }
                } else {
                    gamePlayers[0].printBoard();
                    ((Human) gamePlayers[0]).makeMove();

                    if (((Human) gamePlayers[0]).checkWinner()) {
                        gamePlayers[0].printBoard();
                        System.out.println("X wins");
                        break;
                    } else if (gamePlayers[0].gameFinished()) {
                        gamePlayers[0].printBoard();
                        System.out.println("Draw");
                        break;
                    }
                }
            } else {
                if ("computer".equals(players[1]) && "easy".equals(difficultyLevel)) {
                    gamePlayers[1].printBoard();
                    ((EasyAI) gamePlayers[1]).makeMove();

                    if (((EasyAI) gamePlayers[1]).checkWinner()) {
                        gamePlayers[1].printBoard();
                        System.out.println("O wins");
                        break;
                    } else if (gamePlayers[1].gameFinished()) {
                        gamePlayers[1].printBoard();
                        System.out.println("Draw");
                        break;
                    }
                } else if ("computer".equals(players[1]) && "medium".equals(difficultyLevel)) {
                    gamePlayers[1].printBoard();
                    ((MediumAI) gamePlayers[1]).makeMove();
                    if (((MediumAI) gamePlayers[1]).checkWinner()) {
                        gamePlayers[1].printBoard();
                        System.out.println("O wins");
                        break;
                    } else if (gamePlayers[1].gameFinished()) {
                        gamePlayers[1].printBoard();
                        System.out.println("Draw");
                        break;
                    }
                } else if ("computer".equals(players[1]) && "hard".equals(difficultyLevel)) {
                    gamePlayers[1].printBoard();
                    ((HardAI) gamePlayers[1]).makeMove();
                    if (((HardAI) gamePlayers[1]).checkWinner()) {
                        gamePlayers[1].printBoard();
                        System.out.println("O wins");
                        break;
                    } else if (gamePlayers[1].gameFinished()) {
                        gamePlayers[1].printBoard();
                        System.out.println("Draw");
                        break;
                    }
                } else {
                    gamePlayers[1].printBoard();
                    ((Human) gamePlayers[1]).makeMove();
                    if (((Human) gamePlayers[1]).checkWinner()) {
                        gamePlayers[1].printBoard();
                        System.out.println("O wins");
                        break;
                    } else if (gamePlayers[1].gameFinished()) {
                        gamePlayers[1].printBoard();
                        System.out.println("Draw");
                        break;
                    }
                }
            }
            count++;
        }
    }

    public static void gameCommands() {
        do {
            System.out.print("Input command: ");
            commands = scanner.nextLine().toLowerCase().split("\\s+");
            if (commands.length == 3 || "exit".equals(commands[0])) {
                break;
            } else {
                System.out.println("Bad parameters!");
            }
        } while (true);
    }

    public static void setPlayer() {
        if ("easy".equals(commands[1]) || "medium".equals(commands[1]) || "hard".equals(commands[1])) {
            players[0] = "computer";
            difficultyLevel = commands[1];

            if ("easy".equals(commands[1])) {
                gamePlayers[0] = new EasyAI(difficultyLevel, 'X');
            } else if ("medium".equals(commands[1])){
                gamePlayers[0] = new MediumAI(difficultyLevel, 'X');
            } else if ("hard".equals(commands[1])){
                gamePlayers[0] = new HardAI(difficultyLevel, 'X');
            }
        }

        if ("user".equals(commands[1])) {
            gamePlayers[0] = new Human('X');
            players[0] = "human";
        }

        if ("easy".equals(commands[2]) || "medium".equals(commands[2]) || "hard".equals(commands[2])) {
            players[1] = "computer";
            difficultyLevel = commands[2];

            if ("easy".equals(commands[2])) {
                gamePlayers[1] = new EasyAI(difficultyLevel, 'O');
            } else if ("medium".equals(commands[2])){
                gamePlayers[1] = new MediumAI(difficultyLevel, 'O');
            } else if ("hard".equals(commands[2])) {
                gamePlayers[1] = new HardAI(difficultyLevel, 'O');
            }
        }

        if ("user".equals(commands[2])) {
            gamePlayers[1] = new Human('O');
            players[1] = "human";
        }
    }
}


