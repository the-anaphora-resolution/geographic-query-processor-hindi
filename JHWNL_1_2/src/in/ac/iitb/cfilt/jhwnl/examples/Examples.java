
package in.ac.iitb.cfilt.jhwnl.examples;

import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Pointer;
import in.ac.iitb.cfilt.jhwnl.data.PointerType;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Class	: Examples
 * Purpose	: This class gives an example as to how to use this 
 * API to access various different information. It reads a list of 
 * Hindi words in Unicode (UTF8) in the file <code>inputwords.txt</code> 
 * in the current directory and output all relations of these words. 
 */
public class Examples {
	
	static void demonstration() {

		BufferedReader inputWordsFile = null;
		try {
			inputWordsFile = new BufferedReader(new InputStreamReader (new FileInputStream ("../inputwords.txt"), "UTF8"));
		} catch( FileNotFoundException e){
			System.err.println("Error opening input words file.");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.err.println("UTF-8 encoding is not supported.");
			System.exit(-1);
		}
		JHWNL.initialize("../properties/HindiWN.properties"); 
		                                       
		String inputLine;
		long[] synsetOffsets;
		
		try {
			while((inputLine = inputWordsFile.readLine()) != null){
				System.out.println("\n" + inputLine);
				//	 Look up the word for all POS tags
				IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(inputLine.trim());				
				//	 Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags				
				IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
				demoIndexWord  = demoIWSet.getIndexWordArray();
				for ( int i = 0;i < demoIndexWord.length;i++ ) {
					int size = demoIndexWord[i].getSenseCount();
					System.out.println("Sense Count is " + size);	
					synsetOffsets = demoIndexWord[i].getSynsetOffsets();
					for ( int k = 0 ;k < size; k++ ) {
						System.out.println("Offsets[" + k +"] " + synsetOffsets[k]);	
					}

					Synset[] synsetArray = demoIndexWord[i].getSenses(); 
					for ( int k = 0;k < size;k++ ) {
						System.out.println("Synset [" + k +"] "+ synsetArray[k]);
						System.out.println("Synset POS: " + synsetArray[k].getPOS());
						Pointer[] pointers = synsetArray[k].getPointers();
						System.out.println("Synset Num Pointers:" + pointers.length);
						for (int j = 0; j < pointers.length; j++) {							
							if(pointers[j].getType().equals(PointerType.ONTO_NODES)) {	// For ontology relation
								System.out.println(pointers[j].getType() + " : "  + Dictionary.getInstance().getOntoSynset(pointers[j].getOntoPointer()).getOntoNodes());
							} else {
								System.out.println(pointers[j].getType() + " : "  + pointers[j].getTargetSynset());
							}							
						}
						
					}
				}
				
			}
		} catch (IOException e) {
			System.err.println("Error in input/output.");			
			e.printStackTrace();
		} catch (JHWNLException e) {
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		} 
	}
	
	public static void main(String args[]) throws Exception {
		demonstration();
	}
}
