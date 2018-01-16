package reversiApp;
import reversi.*;

import java.io.IOException;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class reversiBoardController extends GridPane {
	private Board board;
	private Player player1 = new Player();
	private Player player2 = new Player();
	private int height;
	private int width;
	private GameModel model;
	private boolean isCurPlayerHuman;
	private PlayerNum currentPlayerNum;
	//has game ended?
	boolean gameEnded;
	
	public reversiBoardController(GameModel model, int boardSize){
		
		//do{
		//	this.getChildren().clear();
			//height = (int)this.getPrefHeight();
		//}while(height!=400);
		this.model = model;
		System.out.println(height+"ff");
		board = model.getBoard();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reversiBoard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		Pos lastMove = new Pos(0, 0);
		gameEnded = false;
	/*	while (!gameEnded) {
			if (!model.isAbleToMove(PlayerNum.PLAYER1) && !model.isAbleToMove(PlayerNum.PLAYER2)) {
				this.draw();
				gameEnded = true;
				continue;
			}
			//todo  current player has no moves
			if (!model.isAbleToMove(currentPlayerNum)) {
				this.draw();
				//this.drawNoPossibleMoves(currentPlayerNum, lastMove);
				lastMove = new Pos(0, 0);
				switchCurrentPlayer();
				continue;
			}
			
			this.draw();
			//this.drawTurn(currentPlayerNum, lastMove);
			
			//Pos wantedMove = new Pos(0,0);
			boolean moveValid = false;
			//loop until player selects a valid move
			do {
				double height = this.getPrefHeight(); 
				//double width = this.getPrefHeight();
				int cellSize = boardSize / board.getBoardSize();
				int cellWidth = (int)this.getHeight() / board.getBoardSize();
				width = (int)this.getWidth();
				
				try { 
					fxmlLoader.load();
					
					this.setOnMouseClicked(event -> {
						int x = (int)event.getX();
						int y = (int)event.getY();
						for(int i = 0; i < board.getBoardSize(); i++){
							if ((x > i*cellSize) && (x < (i+1)*cellSize)){
								x = i;}
							if ((y > i*cellSize) && (y < (i+1)*cellSize))
								y = i;
						}
						System.out.println(x+" ,"+y);
						Pos wantedMove = new Pos(x+1,y+1);
						if (model.isPossibleMove(currentPlayerNum, wantedMove)) {
							moveValid = true;
						}
						model.place(currentPlayerNum, wantedMove);
					});
				} catch (IOException exception) {
					throw new RuntimeException(exception);
				}
				
				
				/*wantedMove = new Pos(x+1,y+1);
				if (model.isPossibleMove(currentPlayerNum, wantedMove)) {
					moveValid = true;
				}/
				//else 
					//this.drawMoveIsInvalid(wantedMove);			
			} while (!moveValid);

			//move is valid, lets place the piece
			//model.place(currentPlayerNum, wantedMove);
			//lastMove = wantedMove;
			switchCurrentPlayer();

			
		}*/
		currentPlayerNum = PlayerNum.PLAYER1;
		
		try { 
			fxmlLoader.load();
			
			this.setOnMouseClicked(event -> {
				int heightSize = height / board.getBoardSize();
				int widthSize = width / board.getBoardSize();
				int x = (int)event.getX();
				int y = (int)event.getY();
				
				
				System.out.println(height+"h");
				System.out.println(widthSize+"w");
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
		Color player1Color = Color.WHITE;
		Color player2Color = Color.BLACK;
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
			a=i;
		}
		int score1 = model.calcScoreOf(PlayerNum.PLAYER1);
		int score2 = model.calcScoreOf(PlayerNum.PLAYER2);
		VBox info = new VBox();
		info.getChildren().add(0,new Text("Player 1 score:" + score1));
		info.getChildren().add(1,new Text("Player 2 score:" + score2));
		info.getChildren().add(2,new Text("now turn:" + this.currentPlayerNum));
		this.add(info,board.getBoardSize()+1,0);
		//this.add(new Text("Player 1:" + score1),4, 0);
		//this.add(new Text("Player 2:" + score2),4, 1);
		
	}
	

}
