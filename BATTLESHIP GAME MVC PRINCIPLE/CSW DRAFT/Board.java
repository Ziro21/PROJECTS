package battleship;

public class Board {
	
	
    private CellState[][] board;

    public Board() {
        board = new CellState[10][10];
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize board with water (initial state)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = CellState.WATER;
            }
        }
    }

    public CellState getCellState(int row, int col) {
        return board[row][col];
    }

    public void setCellState(int row, int col, CellState state) {
        board[row][col] = state;
    }

    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == CellState.WATER;
    }
    
    
    
}
