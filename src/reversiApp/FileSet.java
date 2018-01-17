package reversiApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*public class FileSet implements Serializable {
	private Settings set;

	public FileSet loadFromFile(File f) {
		// TODO Auto-generated method stub
		FileSet settings = new FileSet();
        try {
        	FileSet.load(f);
            return settings;
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + f);
            return settings;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return settings;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
	

/*	private static void load(File f)  {
		// TODO Auto-generated method stub
		FileSet settings = null;
        ObjectInputStream objectInputStream = null;
        try {

            objectInputStream = new ObjectInputStream(new FileInputStream(f));

            settings = (FileSet) objectInputStream.readObject();
            this.set = highScoresTable.scoreList;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
		
	}

*
	public void save(File f) throws IOException {
		// TODO Auto-generated method stub
		 ObjectOutputStream objectOutputStream = null;
	        try {
	            objectOutputStream = new ObjectOutputStream(new FileOutputStream(f));
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
	                System.err.println("Failed closing file: " + f);
	            }
	        }
		
	}
	*/
//}
