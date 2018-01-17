package reversiApp;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Settings extends GridPane {
	
	private int size = 8;
	
	public Settings(){
		ChoiceBox <Integer> sizeChoice = new ChoiceBox<Integer>(FXCollections.observableArrayList(6,
				8,10,12,14,16,18,20));
		ColorPicker color = new ColorPicker();
		
		
		
		
		this.add(new Text("size of board"), 1, 2);
		this.add(sizeChoice,2,2);
		this.add(color, 3, 3);
		sizeChoice.setOnAction(event->{
			SingleSelectionModel<Integer> n = sizeChoice.getSelectionModel();
			n.getSelectedItem();
			System.out.println(n.getSelectedItem());
		});
		String n = sizeChoice.getAccessibleRoleDescription();
		System.out.println(n);
		
	}

}
