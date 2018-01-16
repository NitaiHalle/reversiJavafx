package reversiApp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	
	//private PlayerNum currentPlayerNum = PlayerNum.PLAYER1;
	            
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		reversiBoardController ControllerBoard = new reversiBoardController(model, boardSize); 
		
		root.widthProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewWidth = newValue.doubleValue() - 120; ControllerBoard.setPrefWidth(boardNewWidth);
			ControllerBoard.draw();
			});
			root.heightProperty().addListener((observable, oldValue, newValue) -> {
				ControllerBoard.setPrefHeight(newValue.doubleValue());
				ControllerBoard.draw();
			});
		
		
		ControllerBoard.setPrefWidth(520);
		ControllerBoard.setPrefHeight(400);
		//root.getChildren().add(0,new Text("Player 1 score:" + score1));
		//root.getChildren().add(1,new Text("Player 2 score:" + score2));
		root.getChildren().add(0, ControllerBoard);
		ControllerBoard.draw();
		
	}
	
	
}
