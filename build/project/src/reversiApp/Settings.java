package reversiApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


import javafx.scene.paint.Color;

public class Settings implements Serializable{
	private int sizeBoard;
	private String colorP1;
	private String colorP2;
	private String startPlayer;
	//private String colorP1.
	private String filename = "settingsGame";
	//private boolean flag = true;
	
	
	public Settings(File f){
		//File f = new File(filename);
		//if (!f.exists()){
			this.colorP1 = "white";
			this.colorP2 = "black";
			
			this.sizeBoard = 8;
			
			this.startPlayer = "player 1";
			
		   // String filename = "settingsGame";
			 
	        // Serialization
	        try {
	 
	            // Saving of object in a file
	            FileOutputStream file = new FileOutputStream
	                                           (filename);
	            ObjectOutputStream out = new ObjectOutputStream
	                                           (file);
	 
	            // Method for serialization of object
	            out.writeObject(this);
	 
	            out.close();
	            file.close();
	 
	            System.out.println("Object has been serialized\n");
	           
	        }
	        
	        catch (IOException ex) {
	            System.out.println("IOException is caught");
	        }
 
		
	}

		
	

	public String getStartPlayer() {
		return startPlayer;
	}




	public void setStartPlayer(String startPlayer) {
		this.startPlayer = startPlayer;
		try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream
                                           (filename);
            ObjectOutputStream out = new ObjectOutputStream
                                           (file);
 
            // Method for serialization of object
            out.writeObject(this);
 
            out.close();
            file.close();
 
            System.out.println("Object has been serialized\n");
           
        }
        
        catch (IOException ex) {
            System.out.println("IOException is caught");
        }
	}




	public int getSizeBoard() {
		return sizeBoard;
	}

	public void setSizeBoard(int sizeBoard) {
		this.sizeBoard = sizeBoard;
		 try {
	            // Saving of object in a file
	            FileOutputStream file = new FileOutputStream
	                                           (filename);
	            ObjectOutputStream out = new ObjectOutputStream
	                                           (file);
	 
	            // Method for serialization of object
	            out.writeObject(this);
	 
	            out.close();
	            file.close();
	 
	            System.out.println("Object has been serialized\n");
	           
	        }
	        
	        catch (IOException ex) {
	            System.out.println("IOException is caught");
	        }
		
	}

	public String getColorP1() {
		return colorP1;
	}

	public void setColorP1(String colorP1) {
		this.colorP1 = colorP1;
		ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
            
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
	}

	public String getColorP2() {
		
		return colorP2;
	}

	public void setColorP2(String colorP2) {
		this.colorP2 = colorP2;
		ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
            
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
	}
	
}

