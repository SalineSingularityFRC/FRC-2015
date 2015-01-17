package org.salinerobotics.library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SingularityReader {
	
	public SingularityReader() {
		
	}
	
	public Properties readProperties(String propFileURL) throws IOException{
		
		Properties prop = new Properties();
		String propURL = propFileURL;
		FileInputStream file;
		
		//loads properties file. Tutorial found on : http://stackoverflow.com/questions/8775303/read-properties-file-outside-jar-file
		file = new FileInputStream(propURL);
		prop.load(file);
		file.close();
		
		/*
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propURL);
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file " + propFileName + "not found");
		}
		*/
		//Enumeration<Object> elements = prop.elements();
		return prop;
		
	}
	
}
