package battleship;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
    	
    	Scanner sc=new Scanner(System.in);
		//ask user to choose between CLI or GUI
    	System.out.println("Please enter 1 fo CLI version and 2 for GUI version");
    	int choice=sc.nextInt();
		//path for configuration file
    	String path="/Users/zeyadkhalil/Desktop/UNI Y3/Semester 2/COMP 6018 OOP/comp6018-24-zey194/DRAFT CSW/input.txt";
		//check the choice and initiliases the correct version
    	if(choice==1)
    	{
    		BattleshipsModel model = new BattleshipsModel(1,path);
            BattleshipsCLI cli = new BattleshipsCLI(model);

            cli.play();
    	}
    	else if(choice==2)
    	{
    		BattleshipsModel model = new BattleshipsModel(2,path);
            BattleshipsController controller = new BattleshipsController(model);
            BattleshipsView view = new BattleshipsView(model);
            model.addObserver(view);
    	}
    	else
    	{
    		System.out.println("Invalid choice");
    	}
    	
        
    }
}
