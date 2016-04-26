package hindipos.hindi;

import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;
import java.io.*;
import java.util.*;

import hindipos.translate.GoogleTranslateAPI;

public class App 
{
    public static void main( String[] args ) throws IOException
    {

//		getSynonyms("बहती");
//		getSentenceDetails();
		useTranslate();
    }
    
    public static void useTranslate()
    {
    	JHWNL.initialize();
    	GoogleTranslateAPI translator = new GoogleTranslateAPI("AIzaSyBEnSzexXv-Ve1E-d9rjHvygguF6rX9I8U");
		String text = translator.translte("Greater Bombay", "en", "hi");
		System.out.println(text);
    }
    
    public static void getSentenceDetails() throws IOException
    {
    	JHWNL.initialize();
    	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String inputLine;
		try 
		{
				//inputLine ="दूर";
				inputLine=br.readLine();
				
				
				GoogleTranslateAPI translator = new GoogleTranslateAPI("AIzaSyBEnSzexXv-Ve1E-d9rjHvygguF6rX9I8U");
				inputLine = translator.translte(inputLine, "en", "hi");
			
				
				//print if not a valid question: इस जानकारी के लिए आपका धन्यवाद  (thank you for this information)
				inputLine=inputLine.trim();
				System.out.println("\nINPUT: " + inputLine+"\n\n");
				
				String[] splited = inputLine.split("\\s+");

//				System.out.println(splited.length);
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
    
    
    
    
    public static ArrayList<String> getSynonyms(String inputLine) throws IOException
    {
    	JHWNL.initialize();
		int i;
		ArrayList<String> syns=new ArrayList<String>();
		try 
		{
				inputLine=inputLine.trim();
//				System.out.println("\nINPUT: " + inputLine+"\n\n");
				
				
//					System.out.println("WORD: "+inputLine);
					
						//Look up the word for all POS tags
						IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(inputLine);				
						//Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags				
						IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
						demoIndexWord  = demoIWSet.getIndexWordArray();
						
						if(demoIndexWord.length==0)
						{
							System.out.println("No entry for this word in WordNet");
						}
						else
						{
							for ( i = 0;i < demoIndexWord.length;i++ ) 
							{
								int size = demoIndexWord[i].getSenseCount();
								Synset[] synsetArray = demoIndexWord[i].getSenses(); 
								for(int lll=0;  lll<200; lll++)
								{
									try{
									
								for ( int k = 0;k <= size;k++ ) 
								{
									try{
										if(synsetArray[k].getWord(lll)!=null && syns.contains(synsetArray[k].getWord(lll).toString())==false)
											syns.add(synsetArray[k].getWord(lll).toString());
									}
									catch(Exception e){
										continue;
									}
									
								}	
									}
									catch(Exception e){
										break;
									}
								}
							}
							
							
							System.out.print("[");
							
							for( i=0;i<syns.size()-1; i++ )
								System.out.print("\""+syns.get(i)+"\" , ");
							System.out.print("\""+syns.get(i)+"\"]");
							
							
							
						}
						return syns;			
		}
		catch (JHWNLException e) 
		{
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		} 
		return syns;
    }
    
    
}