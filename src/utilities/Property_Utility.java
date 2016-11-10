package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property_Utility {

	
	Properties prop = new Properties();
	
	public Property_Utility(String fileName) 
	{
		try {
			InputStream input = new FileInputStream(fileName);
			prop.load(input);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public String getEnvironmentProperty(String propertyKey)
	{
		String propertyValue =  prop.getProperty(propertyKey);
		return propertyValue;
	}
	
}
