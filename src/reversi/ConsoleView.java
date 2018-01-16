package reversi;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView implements View {
	
	private GameModel model;
	
	public ConsoleView(GameModel m){
		this.model = m;
	}

	@Override
	public void drawBoard() {
		// TODO Auto-generated method stub
		System.out.println( "Current Board:");
		drawFirstRow();
		drawCompleteLine();
		for (int i=1; i<= model.getBoardSize() ; i++) {
			drawRow(i);
			drawCompleteLine();
		}
	
		
	}

	@Override
	public void drawTurn(PlayerNum player, Pos lastPlacePos) {
		// TODO Auto-generated method stub
		if (player == PlayerNum.PLAYER1) {
			if (lastPlacePos.getX() != 0) {
				System.out.print( "O was placed at: ");
				drawPos(lastPlacePos);
				System.out.println();
			}
			System.out.println("X: it's your turn.");
		}
		else {
			if (lastPlacePos.getX() != 0) {
				System.out.println("X was placed at: ");
				drawPos(lastPlacePos);
				System.out.println();
				
			}
			System.out.println("O: it's your turn.");
		}
		drawPossibleMoves(player);
		System.out.println();
		System.out.println("Please enter your move as row,column: ");
		
	}

	@Override
	public void drawNoPossibleMoves(PlayerNum player, Pos lastPlacePos) {
		// TODO Auto-generated method stub
		if (player == PlayerNum.PLAYER1) {
			if (lastPlacePos.getX() != 0) {
				System.out.print("O was placed at: ");
				drawPos(lastPlacePos);
				System.out.println();
			}
			System.out.print("X: ");
		}
		else {
			if (lastPlacePos.getX() != 0) {
				System.out.println("X eas place at:");
				drawPos(lastPlacePos);
				System.out.println();

			}
			System.out.println("O: ");
		}
		System.out.println("has no possible moves. Press any key to switch turn...");
		//Scanner input = new Scanner(System.in);
		//input.next();
		
	}

	@Override
	public void drawMoveIsInvalid(Pos pos) {
		// TODO Auto-generated method stub
		System.out.println("move ("+ pos.getX()+","+pos.getY()+") is not possible. try again");
	}

	@Override
	public void drawEndGame(int scoreP1, int scoreP2) {
		// TODO Auto-generated method stub
		System.out.println("----end game----");
		if (scoreP1 > scoreP2)
			System.out.println("the winner is X");
		else if (scoreP1 < scoreP2)
			System.out.println("the winner is O");
		else if (scoreP1 == scoreP2)
			System.out.println("teko");

		
		System.out.println("Player 'X': " + scoreP1 + "   Player 'O': " + scoreP2);
		System.out.println("Press any key to return to menu..");
		
	}

	@Override
	public void setView(GameModel model) {
		// TODO Auto-generated method stub
		
	}
	public void drawPossibleMoves(PlayerNum ofPlayer) {
		//create a pointer (read-only) to the corresponding possible moves vector
		ArrayList<Pos> vec = model.getPossibleMoves(ofPlayer);
		System.out.println("Possible moves are: ");
		for (Pos p : vec){
			drawPos(p);;
		}
	}
	public void drawFirstRow() {
		System.out.print(" |");
		for (int i = 1; i <= model.getBoardSize(); i++) {
			System.out.print(" " + i + " |");
		}
		System.out.println();
	}

	
	
	public void drawCompleteLine() {
		System.out.print("--");
		for (int i = 1; i <= model.getBoardSize(); i++) {
			System.out.print("----");
		}
		System.out.println();

	}
	
	
	public void drawRow(int row) {
		System.out.print(row + "|");
		for (int i = 1; i <= model.getBoardSize(); i++) {
			System.out.print(" ");
			Pos curr = new Pos(row, i);
			switch (model.getCellAt(curr)) {
				case CELL_EMPTY:
					System.out.print(" ");
					break;
				case CELL_PLAYER1:
					System.out.print("X");
					break;
				case CELL_PLAYER2:
					System.out.print("O");
					break;
				case CELL_ERROR:
				default:
					System.out.print("E");
					break;
			}
			System.out.print(" |");
		}
		System.out.println();
	}


	public void drawPos(Pos pos) {
		System.out.print("(" + pos.getX() + "," + pos.getY() + ")");
	}
}



