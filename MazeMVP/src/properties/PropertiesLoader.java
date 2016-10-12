package properties;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class loads the properties of the game
 * @author MariaAmiran
 *
 */
public class PropertiesLoader {
	private static PropertiesLoader instance;
	private Properties properties;
	 
	/**
	 * CTOR
	 */
	private PropertiesLoader() {
		try {
			XMLDecoder decoder = new XMLDecoder (new BufferedInputStream (new FileInputStream ("properties.xml")));
			
			properties=(Properties)decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The method returns an instance of properties loader
	 * @return
	 */
	public static PropertiesLoader getInstance() {
		if (instance == null){
			instance = new PropertiesLoader();
		}
		return instance;
	}

	/**
	 * getter for the properties class
	 * @return
	 */
	public Properties getProperties() {
		return properties;
	}
}
