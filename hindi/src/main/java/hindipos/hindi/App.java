package hindipos.hindi;

import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;

import java.io.*;

import hindipos.translate.GoogleTranslateAPI;





public class App 
{
	
	
    public static void main( String[] args ) throws IOException
    {
    	
    	JHWNL.initialize();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String inputLine;
		try 
		{
				
				//inputLine ="दूर";
				inputLine=br.readLine();
				
				
//				GoogleTranslateAPI translator = new GoogleTranslateAPI("AIzaSyBEnSzexXv-Ve1E-d9rjHvygguF6rX9I8U");
//				String text = translator.translte("harsh", "en", "hi");
//				System.out.println(text);
				

				inputLine=inputLine.trim();
				System.out.println("\nINPUT: " + inputLine+"\n\n");
				
				String[] splited = inputLine.split("\\s+");

				
				for (int l=0; l<splited.length; l++)
				{
					System.out.println("WORD: "+splited[l]);
					
						//Look up the word for all POS tags
						IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(splited[l]);				
						
						//Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags				
						IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
						demoIndexWord  = demoIWSet.getIndexWordArray();
						
						if(demoIndexWord.length==0)
						{
							System.out.println("No entry for this word in WordNet");
						}
						else
						{
							for ( int i = 0;i < demoIndexWord.length;i++ ) 
							{
								int size = demoIndexWord[i].getSenseCount();
								System.out.println("Sense Count is " + size);	
								Synset[] synsetArray = demoIndexWord[i].getSenses(); 
								for ( int k = 0;k < size;k++ ) 
								{
									System.out.println("Synset [" + k +"] "+ synsetArray[k]);
									System.out.println("Synset POS: " + synsetArray[k].getPOS());
								}						
							}
						}
						System.out.println("\n\n");
				}
		}
		catch (JHWNLException e) 
		{
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		} 
    }
}
