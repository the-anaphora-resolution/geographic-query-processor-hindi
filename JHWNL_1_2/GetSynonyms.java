import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;
import java.io.*;
import java.util.*;

public class GetSynonyms 
{
    public static void main( String[] args ) throws IOException
    {
    	JHWNL.initialize();
		getSynonyms();
    }
    public static void getSynonyms() throws IOException
    {
    	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String inputLine;
		int i;
		ArrayList<String> syns=new ArrayList<String>();
		try 
		{
				
				//inputLine ="दूर";
				System.out.print("Enter a word in Hindi to search for its synonyms: ");
				inputLine=br.readLine();

				inputLine=inputLine.trim();
				System.out.println("\nINPUT: " + inputLine+"\n\n");
				
					
						//Look up the word for all POS tags
						IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(inputLine);				
						//Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags				
						IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
						demoIndexWord  = demoIWSet.getIndexWordArray();
						
						if(demoIndexWord.length==0)
						{
							System.out.println("No entry for this word in WordNet\n");
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
							System.out.println("\""+syns.get(i)+"\"]\n");
						}
		}
		catch (JHWNLException e) 
		{
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		} 
    }  
}