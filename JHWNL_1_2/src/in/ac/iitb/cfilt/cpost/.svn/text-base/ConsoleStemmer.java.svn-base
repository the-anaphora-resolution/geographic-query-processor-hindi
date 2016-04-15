package in.ac.iitb.cfilt.cpost;

import in.ac.iitb.cfilt.cpost.stemmer.StemmedToken;
import in.ac.iitb.cfilt.cpost.stemmer.Stemmer;
import in.ac.iitb.cfilt.cpost.stemmer.StemmerRuleResult;
import in.ac.iitb.cfilt.cpost.utils.UTFConsole;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ConsoleStemmer {
	public static void main( String args[]) {
		if(args.length > 0) {		
			try {
				BufferedWriter outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("temp"+ File.separator +"temp.txt"), "UTF8"), 4096);
			
				initialize();
				List baseForms = lookupAllBaseForms(args[0]);
				for (Iterator iter = baseForms.iterator(); iter.hasNext();) {
					String thisBaseform = (String) iter.next(); {
						outFile.write(thisBaseform + "\t");
					}				
				}
				outFile.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}		
	}
	
	public static void initialize() {
		System.err.println("Initializing Morphological Analyzer.");
		String morphConfigFile = "HindiStemmerConfig.txt";
		try {
			//ConfigReader.read(morphConfigFile);
			stemmer = new Stemmer();
		} catch (Exception e) {
			System.err.println("Error initializing Morphological Analyzer. Continuing without Morphology capability." );
			System.err.println("\nError:" + e);
		}
	}

	/* (non-Javadoc)
	 * @see in.ac.iitb.cfilt.jhwnl.dictionary.MorphologicalProcessor#lookupAllBaseForms(in.ac.iitb.cfilt.jhwnl.data.POS, java.lang.String)
	 */
	public static List lookupAllBaseForms( String derivation) {
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
	
	private static Stemmer stemmer;	
}