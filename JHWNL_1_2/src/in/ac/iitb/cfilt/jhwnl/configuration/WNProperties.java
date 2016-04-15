
package in.ac.iitb.cfilt.jhwnl.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class	: WNProperties
 * Purpose	: This class will contain all the configuration parameters
 *            which are read from a properties/config file created by the 
 *            user.

 */
public class WNProperties {

	static String propertyFileName = "HindiWN.properties";
	
    /**
     * This field stores a handle to the Application's Properties file. 
     */
    private static Properties properties = null;

    /**
     * Method 	: load
     * Purpose	: Load te properties from a file other than the default location. 
     * @param customPropertyfilePath
     */
    public static void load(String customPropertyfilePath) {
    	propertyFileName = customPropertyfilePath;
    	load();
    }
        
	/**
     * Method 	: load
     * Purpose	: Loads the Application's Properties file from the default location.
     */
    public static void load() {
        properties = new Properties();
        File propertyFile = new File(propertyFileName);
        try {
        	if(propertyFile.exists()) {
            	FileInputStream propertyFileStream =  new FileInputStream(propertyFile);
                properties.load(propertyFileStream);
                propertyFileStream.close();                
        	} else {
        		propertyFile = new File(propertyFileName);
        		if(propertyFile.exists()) {        	
        			FileInputStream propertyFileStream =  new FileInputStream(propertyFile);
        			properties.load(propertyFileStream);
        			propertyFileStream.close();
        		}        		
        	}
        } catch (IOException e) {
        	configFileError();
        }
        if(properties.size() == 0) {
        	configFileError();
    	}
        propertyFileName = propertyFile.getPath();
    }
    
    /**
     * Method 	: getProperty
     * Purpose  : Returns the value of strProperty.  
     * @param   propertyName
     * @return  The value of strProperty.
     */
    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    /**
     * Method 	: setProperty
     * Purpose	: Sets the value of strProperty.
     * @param propertyName
     * @param value
     */
    public static void setProperty(String propertyName, String value) {
        properties.setProperty(propertyName, value);
    }
    
    /**
     * Method 	: store
     * Purpose	: Stores the Application's Properties file.
     */
    public static void store() {
        try {
        	properties.store(new FileOutputStream(propertyFileName), null);
        } catch (IOException e) {
        	System.err.println("Error writing to configuration file. Current settings could not be saved. " +
        			"Make sure that the file is not write-protected.");
        }
    }
    
    /**
     * Method 	: configFileError
     * Purpose	: Quit after showing message about incorrect configuration file. 
     */
    private static void configFileError() {
    	System.err.println("\n  Error reading configuration file. Make sure its correct path is specified.");
    	System.exit(-1);
	}

}
