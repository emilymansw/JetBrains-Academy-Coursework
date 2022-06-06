package tictactoe;

public class MediumAI extends Game implements PlayerLogic {
    private final String difficultyLevel;
    private char[][] gameTable;
    private final int[] coordinates = new int[2];
    private final char symbol;

    public MediumAI(String difficultyLevel, char symbol) {
        this.difficultyLevel = difficultyLevel;
        this.symbol = symbol;
        setTable();
    }

    @Override
    public void makeMove() {
        System.out.println("Making move level \"" + difficultyLevel + "\"");
        gameTable = getTable();

        if (checkRows()) {
            makeAIMove(coordinates, symbol);
        } else if (checkColumns()) {
            makeAIMove(coordinates, symbol);
        } else if (checkDiagonals()) {
            makeAIMove(coordinates, symbol);
        } else {
            computerMove(symbol);
        }
    }

    @Override
    public boolean checkWinner() {
        return hasWon(symbol);
    }

    public boolean checkRows() {
        boolean flag = false;

        for (int i = 0; i < gameTable.length; i++) {
            flag = (gameTable[i][0] == gameTable[i][1] && gameTable[i][0] != ' ' && gameTable[i][2] == ' ') ||
                    (gameTable[i][0] == gameTable[i][2] && gameTable[i][0] != ' ' && gameTable[i][1] == ' ') ||
                    (gameTable[i][1] == gameTable[i][1] && gameTable[i][1] != ' ' && gameTable[i][0] == ' ');

            if (flag) {
                if (gameTable[i][0] == gameTable[i][1] && gameTable[i][0] != ' ' && gameTable[i][2] == ' ') {
                    coordinates[1] = 2;
                } else if (gameTable[i][0] == gameTable[i][2] && gameTable[i][0] != ' ' && gameTable[i][1] == ' ') {
                    coordinates[1] = 1;
                } else {
                    coordinates[1] = 0;
                }

                coordinates[0] = i;

                break;
            }
        }

        return flag;
    }

    public boolean checkColumns() {
        boolean flag = false;

        for (int i = 0; i < gameTable.length; i++) {
            flag = (gameTable[0][i] == gameTable[1][i] && gameTable[0][i] != ' ' && gameTable[2][i] == ' ') ||
                    (gameTable[0][i] == gameTable[2][i] && gameTable[0][i] != ' ' && gameTable[1][i] == ' ') ||
                    (gameTable[1][i] == gameTable[2][i] && gameTable[1][i] != ' ' && gameTable[0][i] == ' ');

            if (flag) {
                if (gameTable[0][i] == gameTable[1][i] && gameTable[0][i] != ' ' && gameTable[2][i] == ' ') {
                    coordinates[0] = 2;
                } else if (gameTable[0][i] == gameTable[2][i] && gameTable[0][i] != ' ' && gameTable[1][i] == ' ') {
                    coordinates[0] = 1;
                } else {
                    coordinates[0] = 0;
                }

                coordinates[1] = i;

                break;
            }
        }

        return flag;
    }

    public boolean checkDiagonals() {
        boolean flag;

        flag = (gameTable[0][0] == gameTable[1][1] && gameTable[0][0] != ' ' && gameTable[2][2] == ' ') ||
                (gameTable[0][0] == gameTable[2][2] && gameTable[0][0] != ' ' && gameTable[1][1] == ' ') ||
                (gameTable[1][1] == gameTable[2][2] && gameTable[1][1] != ' ' && gameTable[0][0] == ' ') ||
                (gameTable[0][2] == gameTable[1][1] && gameTable[0][2] != ' ' && gameTable[2][0] == ' ') ||
                (gameTable[0][2] == gameTable[2][0] && gameTable[0][2] != ' ' && gameTable[1][1] == ' ') ||
                (gameTable[1][1] == gameTable[2][0] && gameTable[1][1] != ' ' && gameTable[0][2] == ' ');

        if (flag) {
            if (gameTable[0][0] == gameTable[1][1] && gameTable[0][0] != ' ' && gameTable[2][2] == ' ') {
                coordinates[0] = 2;
                coordinates[1] = 2;
            } else if (gameTable[0][0] == gameTable[2][2] && gameTable[0][0] != ' ' && gameTable[1][1] == ' ') {
                coordinates[0] = 1;
                coordinates[1] = 1;
            } else if (gameTable[1][1] == gameTable[2][2] && gameTable[1][1] != ' ' && gameTable[0][0] == ' ') {
                coordinates[0] = 0;
                coordinates[1] = 0;
            } else if (gameTable[0][2] == gameTable[1][1] && gameTable[0][2] != ' ' && gameTable[2][0] == ' ') {
                coordinates[0] = 2;
                coordinates[1] = 0;
            } else if (gameTable[0][2] == gameTable[2][0] && gameTable[0][2] != ' ' && gameTable[1][1] == ' ') {
                coordinates[0] = 1;
                coordinates[1] = 1;
            } else {
                coordinates[0] = 0;
                coordinates[1] = 2;
            }
        }

        return flag;
    }
}
