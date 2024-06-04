package battleship;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private List<int[]> shipParts;
    int part=0;

    public Ship(int length) {
        shipParts = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            shipParts.add(new int[3]);
        }
    }


    public void createShip(int row, int col) {
    	shipParts.get(part)[0] = row ;
        shipParts.get(part)[1] = col;
        shipParts.get(part)[2] = 0;
        part+=1;
    }


    public void hit(int row, int col) {
    	
        for (int[] part : shipParts) {
            if (part[0] == row && part[1] == col) {
                part[2] = 1; // Assuming the third index is used to indicate the hit status
                break; // Exit the loop once the part is found
            }
        }
    }

    public boolean isSunk() {
        for (int[] part : shipParts) {
            if (part[2] != 1) {
                return false; // If any part is not hit, the ship is not sunk
            }
        }
        return true; // All parts are hit, the ship is sunk
    }
    
    public List<int[]> getshipParts()
    {
    	return shipParts;
    }

}
