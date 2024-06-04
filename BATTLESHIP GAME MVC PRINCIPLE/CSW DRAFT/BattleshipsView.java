package battleship;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BattleshipsView extends JFrame implements Observer {
    private BattleshipsModel model;
    private JButton[][] buttons; //for the board

    private int logs=0; //counter to keep track of sunk ships


    //constructor
    public BattleshipsView(BattleshipsModel model) {
        this.model = model;
        model.addObserver(this); // Add the view as an observer
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Battleships");
        setSize(600, 600);
        setLocationRelativeTo(null);

        // Initialize GUI components (board)
        JPanel panel = new JPanel(new GridLayout(10, 10));
        buttons = new JButton[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                button.setActionCommand(i + " " + j);
                button.addActionListener(e -> {
                    String[] coordinates = e.getActionCommand().split(" ");
                    int row = Integer.parseInt(coordinates[0]);
                    int col = Integer.parseInt(coordinates[1]);
                    model.makeMove(row, col);
                });
                panel.add(button);
                buttons[i][j] = button;
            }
        }
        add(panel);

        setVisible(true);
    }

    //update method called when model notifies a change
    @Override
    public void update(Observable o, Object arg) {
    	
    	if (arg instanceof String && ((String) arg).equals("Game Over")) {
            JOptionPane.showMessageDialog(this, "Congratulations. All ships are sunk in "+model.getTries()+" tries");
        }
    	else
    	{
    		List<Boolean> log =model.shiplogs();
    		
    		int sunk=0;
    		for(boolean status:log)
    		{
    			if(status)
    			{
    				sunk++;
    			}
    		}
    		
    		
    		char[][] board = model.getBoard();
    		
    		
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    char cell = board[i][j];
                    if (cell == '~') {
                        buttons[i][j].setText("");
                    } else if (cell == 'X') {
                        buttons[i][j].setText("X");
                        buttons[i][j].setBackground(Color.GREEN);
                        
                    } else if (cell == 'O') {
                        buttons[i][j].setText("O");
                        buttons[i][j].setBackground(Color.RED);
                    } else if (cell == 'S') {
                        buttons[i][j].setText("");
                    }
                }
            }
            if(sunk>logs && sunk<5)
    		{
    			JOptionPane.showMessageDialog(this, "Good Work, This ship is sunk. Keep going");
    			logs=sunk;
    		}
    	}
        
    }
}
