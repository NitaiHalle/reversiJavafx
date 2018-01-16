package reversi;

import java.util.Scanner;

public class Player {
	
	public Player(){
		
	}
	
	public Pos makeMove(GameModel model) {

		Scanner input = new Scanner(System.in);
		System.out.println("enter yuor move");
		int x = input.nextInt();
		int y = input.nextInt();
		//input.close();
		return new Pos(x,y);


	}	
}
