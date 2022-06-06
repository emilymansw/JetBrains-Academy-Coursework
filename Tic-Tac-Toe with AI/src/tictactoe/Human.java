package tictactoe;

public class Human extends Game implements PlayerLogic {
    private final char symbol;
    public Human(char symbol) {
        this.symbol = symbol;

        setTable();
    }

    @Override
    public void makeMove() {
        humanMove(symbol);
    }

    @Override
    public boolean checkWinner() {
        return hasWon(symbol);
    }
}
