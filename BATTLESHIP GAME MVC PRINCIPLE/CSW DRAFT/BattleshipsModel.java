package battleship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.ArrayList;
import java.util.List;

public class BattleshipsModel extends Observable {
    private Board board; //game board
    private List<Ship> ships; //list of all ships placed
    private List<Boolean> shipslog; //track ships
    private int tries = 0; //counter for number of tries
    private int version = 0;// ClI or GUI

    //constructor
    public BattleshipsModel(int version, String filePath) {
        board = new Board();
        ships = new ArrayList<>();
        shipslog = new ArrayList<>();
        this.version = version;
        if (!initializeBoardFromFile(filePath)) {
            System.err.println("Error initializing board from file.");
        }
    }

    public List<Boolean> shiplogs() {
        return shipslog;
    }

    //length of ships to be placed on board
    private boolean initializeBoardFromFile(String filePath) {
        List<Integer> requiredLengths = new ArrayList<>();
        requiredLengths.add(5);
        requiredLengths.add(4);
        requiredLengths.add(3);
        requiredLengths.add(2);
        requiredLengths.add(2);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int length = Integer.parseInt(parts[0]);
                String[] coordinates = parts[1].split("-");
                int row = Integer.parseInt(coordinates[0]);
                int col = Integer.parseInt(coordinates[1]);
                int direction = Integer.parseInt(parts[2]);

                if (!requiredLengths.remove((Integer) length)) {
                    System.err.println("Invalid ship length or duplicate ship: " + length);
                    return false;
                }

                if (!canPlaceShip(row, col, length, direction)) {
                    System.err.println("Cannot place ship at row: " + row + " col: " + col);
                    return false;
                }

                placeShip(row, col, length, direction);
            }

            if (!requiredLengths.isEmpty()) {
                System.err.println("Missing required ships.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //check if ship can be placed
    private boolean canPlaceShip(int row, int col, int length, int direction) {
        if (direction == 0 && col + length > 10) {
            return false;
        }
        if (direction == 1 && row + length > 10) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (direction == 0 && board.getCellState(row, col + i) != CellState.WATER) {
                return false;
            }
            if (direction == 1 && board.getCellState(row + i, col) != CellState.WATER) {
                return false;
            }
        }

        return true;
    }

    //place ship on board
    private void placeShip(int row, int col, int length, int direction) {
        shipslog.add(false);
        ships.add(new Ship(length));
        for (int i = 0; i < length; i++) {
            if (direction == 0) { // horizontal
                board.setCellState(row, col + i, CellState.SHIP);
                ships.get(ships.size() - 1).createShip(row, col + i);
            } else { // vertical
                board.setCellState(row + i, col, CellState.SHIP);
                ships.get(ships.size() - 1).createShip(row + i, col);
            }
        }
    }

    //check if a move is valid
    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < 10 && col >= 0 && col < 10 && (board.getCellState(row, col) == CellState.WATER || board.getCellState(row, col) == CellState.SHIP);
    }

    //execute move
    public boolean makeMove(int row, int col) {
        if (!isValidMove(row, col)) {
            return false; // Invalid move
        }

        if (board.getCellState(row, col) == CellState.WATER) {
            board.setCellState(row, col, CellState.MISS);
            if (version == 1) System.out.println("Guess was a MISS");
        } else {
            board.setCellState(row, col, CellState.HIT);
            Ship hitShip = getShip(row, col);
            if (version == 1) System.out.println("Guess was a HIT");
            if (hitShip != null && !hitShip.isSunk()) {
                hitShip.hit(row, col);
            }
        }

        tries++;

        if (isGameOver()) {
            setChanged();
            notifyObservers("Game Over");
        } else {
            int index = 0;
            for (Ship ship : ships) {
                if (ship.isSunk()) {
                    if (!shipslog.get(index)) {
                        shipslog.set(index, true);
                    }
                }
                index++;
            }
        }

        setChanged();
        notifyObservers();

        return true;
    }

    //check if the game is over by checking if the ships are sunk
    public boolean isGameOver() {
        boolean flag = true;
        int index = 0;
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                flag = false;
            } else {
                if (!shipslog.get(index)) {
                    if (version == 1) System.out.println("This ship is sunk completely");
                    shipslog.set(index, true);
                }
            }
            index++;
        }
        return flag;
    }

    public char[][] getBoard() {
        char[][] state = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                state[i][j] = board.getCellState(i, j).getSymbol();
            }
        }
        return state;
    }

    public int getTries() {
        return tries;
    }

    public Ship getShip(int row, int col) {
        for (Ship ship : ships) {
            for (int[] part : ship.getshipParts()) {
                if (part[0] == row && part[1] == col) {
                    return ship;
                }
            }
        }
        return null;
    }
}
