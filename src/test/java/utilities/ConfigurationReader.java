package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {
	

	private static Properties prop;

	static {

		try {
			String path = "configuration.properties";
			FileInputStream input = new FileInputStream(path);

			prop = new Properties();
			prop.load(input);

			input.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static String getProperty(String keyName) {
		return prop.getProperty(keyName);
	}


}
