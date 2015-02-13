package org.salinerobotics.library;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SingularityReader {
	public Properties readProperties(String propFileURL) throws IOException{
		
		Properties prop = new Properties();
		String propURL = propFileURL;
		FileInputStream fileInputStream;
		
		//loads properties file. Tutorial found on : http://stackoverflow.com/questions/8775303/read-properties-file-outside-jar-file
		fileInputStream = new FileInputStream(propURL);
		prop.load(fileInputStream);
		fileInputStream.close();
		
		return prop;
	}
}
