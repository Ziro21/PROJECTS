package battleship;

import java.util.Scanner;

public class BattleshipsCLI {
    private BattleshipsModel model; //same model used
    private Scanner scanner;

    //constructor
    public BattleshipsCLI(BattleshipsModel model) {
        this.model = model;
        scanner = new Scanner(System.in);
    }

    public void play() {
        while (!model.isGameOver()) {
            // Display board
            displayBoard();

            // Get user input
            System.out.print("Enter location ");
            String adress=scanner.nextLine();
            int row = adress.charAt(0) - 'A';
            int col = adress.charAt(1) - '1';
            
            if(adress.length()==3 && adress.charAt(1)=='1' && adress.charAt(2)=='0')
            {
            	col=9;
            }
            

            // Make move
            if (!model.makeMove(row, col)) {
                System.out.println("Invalid move. Try again.");
            }
        }

        // Game over, display result
        System.out.println("Game over! You sank all the ships in " + model.getTries() + " tries.");
    }

    private void displayBoard() {
        char[][] board = model.getBoard();
        System.out.println("   1 2 3 4 5 6 7 8 9 10");
        System.out.println("  ---------------------");
        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + " |");
            for (int j = 0; j < 10; j++) {
                char cell = board[i][j];
                if (cell == '~' || cell == 'S') {
                    System.out.print(" ");
                } else if (cell == 'X') {
                    System.out.print("X");
                } else if (cell == 'O') {
                    System.out.print("O");
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.println("  ---------------------");
        }
    }

}

