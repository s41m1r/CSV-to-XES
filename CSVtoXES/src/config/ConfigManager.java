package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	static final String PATH = "mapping.properties";
	
	private ConfigManager(){}
	
	public static String getProp(String property){
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream(PATH);
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			return prop.getProperty(property);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
