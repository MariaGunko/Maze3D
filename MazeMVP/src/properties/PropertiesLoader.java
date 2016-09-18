package properties;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PropertiesLoader {
	private static PropertiesLoader instance;
	private Properties properties;
	
	private PropertiesLoader() {
		XMLDecoder decoder;
		try {
			decoder = new XMLDecoder (new FileInputStream ("properties.xml"));
			properties=(Properties)decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static PropertiesLoader getInstance() {
		if (instance == null){
			instance = new PropertiesLoader();
		}
		return instance;
	}

	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}
}
