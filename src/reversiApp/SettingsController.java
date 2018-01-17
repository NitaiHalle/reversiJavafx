package reversiApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SettingsController extends GridPane {
	
	//private ReversiGameController reversiGameController;
	private Settings settings;// = null;// = new Settings();
	
	public SettingsController(Settings settings){
		this.settings = settings;
		//this.reversiGameController = r;
		//this.settings = this.reversiGameController.getSettings();
		//this.getSettings();
		ChoiceBox <Integer> sizeChoice = new ChoiceBox<Integer>(FXCollections.observableArrayList(4,6,
				8,10,12,14,16,18,20));
		
		ChoiceBox <String> colorChoicePlayer1 = new ChoiceBox<String>(FXCollections.observableArrayList(
				"white","black","green","blue","red","yellow","orange","brown","gray","pink","purple"));
		
		ChoiceBox <String> colorChoicePlayer2 = new ChoiceBox<String>(FXCollections.observableArrayList(
				"white","black","green","blue","red","yellow","orange","brown","gray","pink","purple"));
		
		
		this.add(new Text("choose size of board :"), 0, 0);
		this.add(new Text("choose color player1: "), 0, 1);
		this.add(new Text("choose color player2: "), 0, 2);
		
		this.add(sizeChoice,1,0);
		this.add(colorChoicePlayer1, 1, 1);
		this.add(colorChoicePlayer2, 1, 2);
		
		sizeChoice.setOnAction(event->{
			SingleSelectionModel<Integer> choice = sizeChoice.getSelectionModel();
			this.settings.setSizeBoard(choice.getSelectedItem());
			System.out.println(choice.getSelectedItem());
			
		});
		
		colorChoicePlayer1.setOnAction(event->{
			SingleSelectionModel<String> selcetOptP1 = colorChoicePlayer1.getSelectionModel();
			String colorP1 = selcetOptP1.getSelectedItem();
			this.settings.setColorP1(selcetOptP1.getSelectedItem());
			System.out.println("p1:"+colorP1);
		});
		
		colorChoicePlayer2.setOnAction(event->{
			SingleSelectionModel<String> selcetOptP2 = colorChoicePlayer2.getSelectionModel();
			String colorP2 = selcetOptP2.getSelectedItem();
			this.settings.setColorP2(selcetOptP2.getSelectedItem());
			System.out.println("p2:"+colorP2);
		});
		String n = sizeChoice.getAccessibleRoleDescription();
		System.out.println(n);
		
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
