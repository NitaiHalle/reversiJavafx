package reversiApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import reversi.Board;
import reversi.GameModel;
import reversi.PlayerNum;

public class ReversiGameController implements Initializable{

	@FXML
	private HBox root;
	private GameModel model;
	private int boardSize = 400;
	private VBox info = new VBox();
	private Button restart = new Button("restart game");
	private Button settingsButton = new Button("settings");
	private ReversiBoardController ControllerBoard;
	private Settings settings = null;
	private SettingsController settingsController;// =  new SettingsController(settings);
	//private Settings settings = null;// = new Settings();
	private boolean endGame = false;
	private PlayerNum currentPlayerNum;//= PlayerNum.PLAYER1;
	            
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		File f = new File("settingsGame");
		if (!f.exists()){
			System.out.println("cdcd");
			this.settings = new Settings(f);
		} else {
			this.getSettings();
		}
		
		settingsController =  new SettingsController(settings);
    		
		this.model = new GameModel(settings.getSizeBoard());
		ControllerBoard = new ReversiBoardController(model, boardSize, settings); 
		
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
		info.getChildren().add(settingsButton);
		//Text t = new Text("turn : " + this.currentPlayerNum)
		info.getChildren().add(new Text("turn : "+ this.ControllerBoard.getCurrentPlayer()));//+ this.currentPlayerNum));
		this.startNewGame();
		
		settingsController.setPrefHeight(400);
		settingsController.setPrefWidth(400);
		
		
		
		restart.setOnAction(event->{
			this.getSettings();
			root.getChildren().remove(ControllerBoard);
			root.getChildren().clear();
			//this.settings.setSizeBoard(8);
			this.getSettings();
			this.startNewGame();
		});
		
		settingsButton.setOnAction(event->{
			root.getChildren().clear();
			info.getChildren().clear();
			info.getChildren().add(restart);
			//info.getChildren().add(e)
			root.getChildren().add(info);
			this.changeSettings();
		});
		
		root.setOnMouseClicked(event->{
			info.getChildren().clear();
			info.getChildren().add(restart);
			info.getChildren().add(settingsButton);
			this.currentPlayerNum = this.ControllerBoard.getCurrentPlayer();
			//info.getChildren().add(new Text("turn : "+ this.currentPlayerNum));
			
			this.endGame = this.ControllerBoard.gameEnd();
			if (this.endGame){
				PlayerNum winner = this.ControllerBoard.getWinner();
				int score = this.ControllerBoard.getWinnerScore();
				if (winner == null){
					info.getChildren().add(new Text("the game end"));
					info.getChildren().add(new Text("even"));
					info.getChildren().add(new Text("the score is :" + score));	
				} else {
					info.getChildren().add(new Text("the game end"));
					info.getChildren().add(new Text(winner+" wins"));
					info.getChildren().add(new Text("his score is :" + score));
				}
				info.getChildren().add(new Text());
			} else {
				info.getChildren().add(new Text("turn : "+ this.currentPlayerNum));
			}
			//Text t = new Text("turn : " + this.currentPlayerNum)
			//info.getChildren().add(new Text("turn : "+ this.currentPlayerNum));
			
			
		});
		
		
	}
	
	private void startNewGame(){
		root.getChildren().add(info);
		double width = ControllerBoard.getPrefWidth();
		double height = ControllerBoard.getPrefHeight();
		System.out.println(this.settings.getSizeBoard()+"ffff");
		ControllerBoard = new ReversiBoardController(new GameModel(this.settings.getSizeBoard()), boardSize, this.settings);
		ControllerBoard.setPrefWidth(width);
		ControllerBoard.setPrefHeight(height);
		root.getChildren().add(0,ControllerBoard);
		ControllerBoard.draw();
	}
	
	private void changeSettings(){
		double width = settingsController.getPrefWidth();
		double height = settingsController.getPrefHeight();
		settingsController.setPrefWidth(width);
		settingsController.setPrefHeight(height);
		root.getChildren().add(0,settingsController);
		
	}
	
	
	
	private void getSettings(){
		try {
			 
            FileInputStream file = new FileInputStream
                                         ("settingsGame");
            ObjectInputStream in = new ObjectInputStream
                                         (file);
 
            this.settings = (Settings)in.readObject();
 
            in.close();
            file.close();
            System.out.println("Object has been deserialized\n"
                                + "Data after Deserialization.");
        }
 
        catch (IOException ex) {
            System.out.println("IOException is caught");
        }
 
        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                                " is caught");
        }
	}
	
	
}
