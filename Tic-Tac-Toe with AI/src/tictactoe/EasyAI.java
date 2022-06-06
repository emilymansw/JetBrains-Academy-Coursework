package tictactoe;

public class EasyAI extends Game implements PlayerLogic {
    private final String difficultyLevel;
    private final char symbol;

    public EasyAI(String difficultyLevel, char symbol) {
        this.difficultyLevel = difficultyLevel;
        this.symbol = symbol;
        setTable();
    }


    @Override
    public void makeMove() {
        System.out.println("Making move level \"" + difficultyLevel + "\"");
        computerMove(symbol);
    }

    @Override
    public boolean checkWinner() {
        return hasWon(symbol);
    }
}
