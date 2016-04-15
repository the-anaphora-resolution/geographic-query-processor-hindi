package in.ac.iitb.cfilt.jhwnl.dictionary.morph;

import in.ac.iitb.cfilt.cpost.ConfigReader;
import in.ac.iitb.cfilt.cpost.stemmer.StemmedToken;
import in.ac.iitb.cfilt.cpost.stemmer.Stemmer;
import in.ac.iitb.cfilt.cpost.stemmer.StemmerRuleResult;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.configuration.WNProperties;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.POS;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;
import in.ac.iitb.cfilt.jhwnl.dictionary.MorphologicalProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Class	: StemItMorphologicalProcessor
 * Purpose	: This class is implements morphological functionality for the hindi wordnet API
 *  using StemIt.jar 
 */
public class StemItMorphologicalProcessor implements MorphologicalProcessor{
	
	private Stemmer stemmer;

	/**
	 * This field stores singleton instance 
	 */
	private static StemItMorphologicalProcessor objInstance = null;
	
	/**
	 * Method 	: getInstance
	 * Purpose	: Returns the singleton instance of the class. 
	 * @return  - Returns the singleton instance of that class.
	 */
	public static StemItMorphologicalProcessor getInstance() {
		if(objInstance == null) {
			objInstance = new StemItMorphologicalProcessor();
		}
		return objInstance;
	}
	
	/**
	 * Private Constructor for Singleton Implementation
	 */
	private StemItMorphologicalProcessor() {
		System.err.println("Initializing Morphological Analyzer.");
		String morphConfigFile = WNProperties.getProperty("morph.config.file");
		try {
			ConfigReader.read(morphConfigFile);
			stemmer = new Stemmer();
		} catch (Exception e) {
			System.err.println("Error initializing Morphological Analyzer. Continuing without Morphology capability." );
			System.err.println("\nError:" + e);
		}
	}

	/* (non-Javadoc)
	 * @see in.ac.iitb.cfilt.jhwnl.dictionary.MorphologicalProcessor#lookupAllBaseForms(in.ac.iitb.cfilt.jhwnl.data.POS, java.lang.String)
	 */
	public List lookupAllBaseForms(POS pos, String derivation) throws JHWNLException {
		ArrayList<String> rootFormList = new ArrayList<String>();
		StemmedToken stoken = stemmer.stem(derivation);	
		Vector<StemmerRuleResult> stemmerResults = stoken.getStemmedOutputs();
		for (StemmerRuleResult aResult:stemmerResults){
			if(!rootFormList.contains(aResult.getRoot())) {
				rootFormList.add(aResult.getRoot());
			}
			
		}
		return rootFormList;
	}

	/* (non-Javadoc)
	 * @see in.ac.iitb.cfilt.jhwnl.dictionary.MorphologicalProcessor#lookupBaseForm(in.ac.iitb.cfilt.jhwnl.data.POS, java.lang.String)
	 */
	public IndexWord lookupBaseForm(POS pos, String derivation) throws JHWNLException {
		IndexWord retWord;		
		StemmedToken stoken = stemmer.stem(derivation);	
		Vector<StemmerRuleResult> stemmerResults = stoken.getStemmedOutputs();
		for (StemmerRuleResult aResult:stemmerResults){
			retWord = Dictionary.getInstance().getIndexWord(pos, aResult.getRoot());
			if(retWord != null) {
				return retWord;
			}
		}		
		return null;
	}
}
