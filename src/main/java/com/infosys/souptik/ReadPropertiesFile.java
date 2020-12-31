package com.infosys.souptik;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ReadPropertiesFile {

	public static String getValueFromPropertiesFile(String propFileName, String propKey) throws IOException {
		String propValue = StringUtils.EMPTY;
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream(propFileName);
		if(stream != null)
			prop.load(stream);
		else {
			log.debug("Properties file {} not found in classpath" , propFileName);
			throw new FileNotFoundException("Properties file "+ propFileName  +" not found in classpath");
		}
		propValue = prop.getProperty(propKey);
		return propValue;
	}
}
