package reversiApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import reversi.Board;
import reversi.GameModel;
import reversi.PlayerNum;

public class reversiGameController implements Initializable{

	@FXML
	private HBox root;
	private GameModel model = new GameModel(4);
	private int boardSize = 400;
	private VBox info = new VBox();
	private Button restart = new Button("restart game");
	private Button settings = new Button("settings");
	private reversiBoardController ControllerBoard;
	//private Settings sett= 
	//private PlayerNum currentPlayerNum = PlayerNum.PLAYER1;
	            
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ControllerBoard = new reversiBoardController(model, boardSize); 
		
		root.widthProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewWidth = newValue.doubleValue() - 120; ControllerBoard.setPrefWidth(boardNewWidth);
			ControllerBoard.draw();
			});
			root.heightProperty().addListener((observable, oldValue, newValue) -> {
				ControllerBoard.setPrefHeight(newValue.doubleValue());
				ControllerBoard.draw();
			});
		ControllerBoard.setPrefWidth(400);
		ControllerBoard.setPrefHeight(400);
		info.getChildren().add(restart);
		info.getChildren().add(settings);
		this.startNewGame();
		//ControllerBoard.draw();
		
		//int score1;
		//int score2 = model.calcScoreOf(PlayerNum.PLAYER2);
		//VBox info = new VBox();
		//Button b = new Button("new game");
		//info.getChildren().add(b);
	/*	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reversiGameControl.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		//info.getChildren().add(0,new Text("Player 1 score:" + score1));
		//info.getChildren().add(1,new Text("Player 2 score:" + score2));
		
		//try{
			root.setOnMouseClicked(event->{
				int score1 = model.calcScoreOf(PlayerNum.PLAYER1);
				info.getChildren().add(0,new Text("Player 1 score:" + score1));
				int score2 = model.calcScoreOf(PlayerNum.PLAYER2);
				info.getChildren().add(1,new Text("Player 2 score:" + score2));
			});
			
		//} //catch (IOException e){
			//throw new RuntimeException(e);
		//}
		*/
		restart.setOnAction(event->{
			root.getChildren().remove(ControllerBoard);
			root.getChildren().clear();
			this.startNewGame();
		});
		
		settings.setOnAction(event->{
			root.getChildren().clear();
			root.getChildren().add(info);
			root
		});
	}
	
	private void startNewGame(){
		root.getChildren().add(info);
		double width = ControllerBoard.getPrefWidth();
		double height = ControllerBoard.getPrefHeight();
		ControllerBoard = new reversiBoardController(new GameModel(4), boardSize);
		ControllerBoard.setPrefWidth(width);
		ControllerBoard.setPrefHeight(height);
		root.getChildren().add(0,ControllerBoard);
		ControllerBoard.draw();
	}
	
	
}
