package reversiApp;
import reversi.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ReversiBoardController extends GridPane {
	private Board board;
	private Player player1 = new Player();
	private Player player2 = new Player();
	private int height;
	private int width;
	private GameModel model;
	private boolean isCurPlayerHuman;
	private PlayerNum currentPlayerNum;
	private Settings settings;
	//has game ended?
	boolean gameEnded;
	
	public ReversiBoardController(GameModel model, int boardSize,Settings s){
		
		this.settings = s;
		this.model = model;
		board = model.getBoard();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reversiBoard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		gameEnded = false;
	
		currentPlayerNum = PlayerNum.PLAYER1;
		
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
				
					if (!model.isAbleToMove(currentPlayerNum)) {
						switchCurrentPlayer();
						this.draw();
					}
					if (!model.isAbleToMove(PlayerNum.PLAYER1) && !model.isAbleToMove(PlayerNum.PLAYER2)){
						gameEnded = true;
						
					}
				}
			});
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		
		
	}
	
	public void switchCurrentPlayer() {
		currentPlayerNum = (currentPlayerNum == PlayerNum.PLAYER1)? PlayerNum.PLAYER2 : PlayerNum.PLAYER1;
		//isCurPlayerHuman = (currentPlayerNum == PlayerNum.PLAYER1)? isPlayer1Human : isPlayer2Human;
	}
	
	public Player getCurrentPlayer() {
		return (currentPlayerNum == PlayerNum.PLAYER1) ? player1 : player2;
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
		
		int a = 0;
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
		//int score1 = model.calcScoreOf(PlayerNum.PLAYER1);
		//int score2 = model.calcScoreOf(PlayerNum.PLAYER2);
		//VBox info = new VBox();
		//Button b = new Button("new game");
		//info.getChildren().add(b);
		//info.getChildren().add(0,new Text("Player 1 score:" + score1));
		//info.getChildren().add(1,new Text("Player 2 score:" + score2));
		
		int score1 = this.model.calcScoreOf(PlayerNum.PLAYER1);
		int score2 = this.model.calcScoreOf(PlayerNum.PLAYER2);
		String winner;
		if(score1 == score2){
			winner = "in same score for all";
		} else{
			if(score1 > score2){
				winner = "player 1 is win the score is: "+score1;
			}else{
				winner = "player 2 is win the score is: "+score2;
			}
		}
		Alert alert = new Alert(AlertType.NONE, winner+" click x to continue");
		if(gameEnded && !alert.isShowing()){
			
			//Alert alert = new Alert(AlertType.NONE, winner+" click x to continue");
			alert.getOnCloseRequest();
			alert.show();
			alert.getOnCloseRequest();
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
				// TODO Auto-generated catch block
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
	
	public String getWinner(){
		String winner = null;
		if(gameEnded){
			int score1 = this.model.calcScoreOf(PlayerNum.PLAYER1);
			int score2 = this.model.calcScoreOf(PlayerNum.PLAYER2);
			//String winner;
			if(score1 == score2){
				winner = "in same score for all";
			} else{
				if(score1 > score2){
					winner = "player 1 is win the score is: "+score1;
				}else{
					winner = "player 2 is win the score is: "+score2;
				}
			}
			
			//Alert alert = new Alert(AlertType.NONE, winner+" click x to continue");
			
		}
		return winner;
	}
}
