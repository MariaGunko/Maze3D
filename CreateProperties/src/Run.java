import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Run {

	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.setNumOfThreads(20);
		prop.setGenerateMazeAlgorithm("GrowingTreeGenerator_RandomNextMove");
		prop.setSolveMazeAlgorithm("DFS");
		prop.setViewForm("GUI");
		
		XMLEncoder xmlEncoder = null;
		try {
			xmlEncoder = new XMLEncoder (new FileOutputStream("properties.xml"));
			xmlEncoder.writeObject(prop);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			xmlEncoder.close();
		}

	}

}
