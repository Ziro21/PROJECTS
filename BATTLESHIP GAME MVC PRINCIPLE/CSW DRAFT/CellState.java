package battleship;

//defines possibles states
public enum CellState {
    WATER('~'), SHIP('S'), HIT('X'), MISS('O');

    private final char symbol;

    CellState(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
