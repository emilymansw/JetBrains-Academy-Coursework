package tictactoe;

public class HardAI extends Game implements PlayerLogic {
    private final String difficultyLevel;
    private char[][] gameTable;
    private final int[] coordinates = new int[2];
    private final char symbol;
    private final char player;
    private final char opponent;

    public HardAI(String difficultyLevel, char symbol) {
        this.difficultyLevel = difficultyLevel;
        this.symbol = symbol;
        this.player = symbol;
        if(symbol == 'O'){
            this.opponent = 'X';
        } else {
            this.opponent = 'O';
        }
        setTable();
    }


    @Override
    public void makeMove() {
        System.out.println("Making move level \"" + difficultyLevel + "\"");

        gameTable = getTable();
        findBestMove(gameTable);
        makeAIMove(coordinates, symbol);
    }

    @Override
    public boolean checkWinner() {
        return hasWon(symbol);
    }


    private int minimax(char board[][], int depth, Boolean isMax) {
        if (hasWon(player)){
            return +10;
        };
        if(hasWon(opponent)){
            return -10;
        }
        if (gameFinished() == true){
            return 0;
        }

        int bestScore = isMax ? -1000 : 1000;

        if (isMax) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j]==' ') {
                        board[i][j] = player;
                        bestScore = Math.max(bestScore, minimax(board, depth++, !isMax));
                        board[i][j] = ' ';
                    }
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = opponent;
                        bestScore = Math.min(bestScore, minimax(board, depth++, !isMax));
                        board[i][j] = ' ';
                    }
                }
            }
        }
        return bestScore;
    }


    private void findBestMove(char board[][]) {
        int bestScore = -1000;
        coordinates[0] = -1;
        coordinates[1] = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimax(board, 0, false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        coordinates[0] = i;
                        coordinates[1] = j;
                        bestScore = score;
                    }
                }
            }
        }
    }
}
