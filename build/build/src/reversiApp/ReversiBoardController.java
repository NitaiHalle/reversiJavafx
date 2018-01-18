package reversiApp;
import reversi.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ReversiBoardController extends GridPane {
	//members
	private Board board;
	private int height;
	private int width;
	private GameModel model;
	private PlayerNum currentPlayerNum;
	private Settings settings;
	boolean gameEnded;
	/*
	 * constructor
	 */
	public ReversiBoardController(GameModel model, int boardSize,Settings s){
		
		this.settings = s;
		this.model = model;
		board = model.getBoard();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reversiBoard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		gameEnded = false;
		
		if (this.settings.getStartPlayer().equals("player 1")) {
			currentPlayerNum = PlayerNum.PLAYER1;
		} else {
			currentPlayerNum = PlayerNum.PLAYER2;
		}	
		try { 
			fxmlLoader.load();
			
			this.setOnMouseClicked(event -> {
				
			
				int heightSize = height / board.getBoardSize();
				int widthSize = width / board.getBoardSize();
				int x = (int)event.getX();
				int y = (int)event.getY();
				
				
				for(int i = 0; i < board.getBoardSize(); i++){
					if ((x > i*heightSize) && (x < (i+1)*heightSize))
						x = i +1;
					if ((y > i*widthSize) && (y < (i+1)*widthSize)){
						y = i+1;
						
					}
					
					Pos wantedMove = new Pos(y,x);
					if (model.isPossibleMove(currentPlayerNum, wantedMove)) {
						model.place(currentPlayerNum, wantedMove);
						this.switchCurrentPlayer();
						this.draw();
					}
					
					if (!model.isAbleToMove(PlayerNum.PLAYER1) && !model.isAbleToMove(PlayerNum.PLAYER2)){
						gameEnded = true;
					}
				
					if (!model.isAbleToMove(currentPlayerNum)) {
						switchCurrentPlayer();
						this.draw();
					}
					
				}
			});
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	public void switchCurrentPlayer() {
		currentPlayerNum = (currentPlayerNum == PlayerNum.PLAYER1)? PlayerNum.PLAYER2 : PlayerNum.PLAYER1;
	}
	public boolean gameEnd(){
		return this.gameEnded;
	}
	public PlayerNum getCurrentPlayer() {
		return (currentPlayerNum == PlayerNum.PLAYER1) ? PlayerNum.PLAYER1 : PlayerNum.PLAYER2;
	}
	
	public void draw() {
		Color player1Color = this.stringToColor(this.settings.getColorP1());
		Color player2Color = this.stringToColor(this.settings.getColorP2());
		this.getChildren().clear();
		
		height = (int)this.getPrefHeight(); 
		width = (int)this.getPrefWidth();
		int cellHeight = height / board.getBoardSize();
		int cellWidth = height / board.getBoardSize();
		int radios = cellHeight > cellWidth ? cellWidth/2 : cellHeight/2;
		System.out.println(cellWidth);
		boolean color1 = true;
		
		
		if(board.getBoardSize() % 2 == 0){
			for (int i = 0; i < board.getBoardSize(); i++) {
				for (int j = 0; j < board.getBoardSize(); j++) {
					if (color1){
						this.add(new Rectangle(cellWidth,cellHeight,Color.AQUA), j, i);
						color1 = !color1;
					}
					else {
						this.add(new Rectangle(cellWidth,cellHeight,Color.DARKCYAN), j, i);
						color1 = !color1;
					}
					
					if (board.getCellAt(i, j) == Cell.CELL_PLAYER1)
						this.add(new Circle(radios, player1Color), j, i);
					else if (board.getCellAt(i, j) == Cell.CELL_PLAYER2)
						this.add(new Circle(radios, player2Color), j, i);
				}
				color1 = !color1;
				
			}
		} else {
			for (int i = 0; i < board.getBoardSize(); i++) {
				for (int j = 0; j < board.getBoardSize(); j++) {
					if (color1){
						this.add(new Rectangle(cellWidth,cellHeight,Color.AQUA), j, i);
						color1 = !color1;
					}
					else {
						this.add(new Rectangle(cellWidth,cellHeight,Color.DARKCYAN), j, i);
						color1 = !color1;
					}
					
					if (board.getCellAt(i, j) == Cell.CELL_PLAYER1)
						this.add(new Circle(radios, player1Color), j, i);
					else if (board.getCellAt(i, j) == Cell.CELL_PLAYER2)
						this.add(new Circle(radios, player2Color), j, i);
				}
				
			}
		}
		
	}
	public void setGameModel(GameModel m) {
		this.model = m;
	}
	
	private Color stringToColor(String ColorInString){
		switch (ColorInString) {
			case "black":
				return Color.BLACK;	
			case "white":
				return Color.WHITE;
			case "yellow":
				return Color.YELLOW;	
			case "green":
				return Color.GREEN;
			case "gray":
				return Color.GRAY;
				
			case "pink":
				return Color.PINK;
			case "orange":
				return Color.ORANGE;
				
			case "blue":
				return Color.BLUE;
			case "purple":
				return Color.PURPLE;
			case "brown":
				return Color.BROWN;
			case "red":
				return Color.RED;
		}
		return null;
			
	}
	
	
	public String getColorP2() {
		ObjectInputStream objectInputStream = null;
        try {
        	objectInputStream = new ObjectInputStream(new FileInputStream("settings"));
        	try {
				settings = (Settings)objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}         
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectInputStream != null) {
                	objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: settings");
            }
        }
		return settings.getColorP2();
	}
	
	public PlayerNum getWinner(){
		PlayerNum winner = null;
		if(gameEnded){
			int score1 = this.model.calcScoreOf(PlayerNum.PLAYER1);
			int score2 = this.model.calcScoreOf(PlayerNum.PLAYER2);
			if(score1 == score2){
				winner = null;
			} else{
				if(score1 > score2){
					winner = PlayerNum.PLAYER1;
				}else{
					winner = PlayerNum.PLAYER2;
				}
			}
		}
		return winner;
	}
	public int getWinnerScore(){
		int score1 = this.model.calcScoreOf(PlayerNum.PLAYER1);
		int score2 = this.model.calcScoreOf(PlayerNum.PLAYER2);
		if (score1 > score2){
			return score1;
		} else {
			return score2;
		}
	}
	public int getScoreP1(){
		return this.model.calcScoreOf(PlayerNum.PLAYER1);
		
	}
	public int getScoreP2(){
		return this.model.calcScoreOf(PlayerNum.PLAYER2);
		
	}
}
