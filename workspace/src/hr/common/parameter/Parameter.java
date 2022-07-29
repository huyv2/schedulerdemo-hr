package hr.common.parameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Parameter {
	private static final Logger log = Logger.getLogger(Parameter.class);
	
	public static Properties loadParameters() {
		Properties props = new Properties();
		
		String pathFile;
		try {
			pathFile = URLDecoder.decode(Parameter.class.getResource("parameter.properties").getPath(), "UTF-8");
			InputStream isConfig = new FileInputStream(pathFile);
			props.loadFromXML(isConfig);
		} catch (IOException e) {
			log.error(e);
		}
		
		return props;
	}
}
