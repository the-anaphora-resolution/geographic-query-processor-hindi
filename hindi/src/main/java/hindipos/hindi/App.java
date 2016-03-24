package hindipos.hindi;

import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;



//
//import hindipos.translate.GoogleTranslateAPI;
//




public class App 
{
	
	
    public static void main( String[] args )
    {
    	
    	JHWNL.initialize();
		
		String inputLine;
		try {
				inputLine ="इंडिया";
				
				
//				GoogleTranslateAPI translator = new GoogleTranslateAPI("AIzaSyBEnSzexXv-Ve1E-d9rjHvygguF6rX9I8U");
//		        String text = translator.translte("हिंदुस्तान", "hi", "en");
//		        System.out.println(text);
				
				
				
				
				inputLine=inputLine.trim();
				System.out.println("\nINPUT: " + inputLine);
				//	 Look up the word for all POS tags
				IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(inputLine);				
				//	 Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags				
				IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
				demoIndexWord  = demoIWSet.getIndexWordArray();
				for ( int i = 0;i < demoIndexWord.length;i++ ) {
					int size = demoIndexWord[i].getSenseCount();
					System.out.println("Sense Count is " + size);	
					Synset[] synsetArray = demoIndexWord[i].getSenses(); 
					for ( int k = 0;k < size;k++ ) {
						System.out.println("Synset [" + k +"] "+ synsetArray[k]);
						System.out.println("Synset POS: " + synsetArray[k].getPOS());
					}
				}
		}  catch (JHWNLException e) {
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		} 
    }
}
