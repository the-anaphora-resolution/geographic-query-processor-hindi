// CFILT LAB
// Author: Manish Sinha
// Contains system info as well as JWNL properties.
// **************************************************************************************************************
package in.ac.iitb.cfilt.jhwnl;

import in.ac.iitb.cfilt.jhwnl.configuration.WNProperties;


/**
 * Class	: JHWNL
 * Purpose	: This class is the Initialization Class of the API and 
 * allows one to call the Dictionary.getInstance() method to access wordnet data  
 */
public final class JHWNL{

	
	/**
	 * Method 	: initialize 
	 * Purpose	: Load the Hindi Wordnet properties file.
	 * @param pWNPropertiesFile
	 */
	public static void initialize(String pWNPropertiesFile) {
		WNProperties.load(pWNPropertiesFile);		
		showStartMessage();		
	}
	

	/**
	 * Method 	: initialize 
	 * Purpose	: Load the Hindi Wordnet properties file. Attempts to find the 
	 * properties file in the default location <code>./config/WNProperties.txt</code> or
	 * <code>./WNProperties.txt</code>
	 */
	public static void initialize() {
		WNProperties.load();
		showStartMessage();				
	}
	
	/**
	 * Method 	: showStartMessage
	 * Purpose	: Show an initial start message for the Wordnet System
	 */
	private static void showStartMessage() {
		System.err.println("Initializing Hindi Wordnet " + WNProperties.getProperty("HindiWNVersion"));
	}

}
