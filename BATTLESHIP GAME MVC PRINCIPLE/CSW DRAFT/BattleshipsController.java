package battleship;

public class BattleshipsController {
    private BattleshipsModel model; //same model shared

    //constructor
    public BattleshipsController(BattleshipsModel model) {
        this.model = model;
    }

    //used to process a move
    public boolean makeMove(int row, int col) {
        return model.makeMove(row, col);
    }

    public boolean isGameOver() {
        return model.isGameOver();
    }
}

