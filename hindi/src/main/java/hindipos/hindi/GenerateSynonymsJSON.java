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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GenerateSynonymsJSON {

	public static void main(String[] args) {
		String orgline,line,text="";
		int i;
		ArrayList<String> syns;
		JSONObject synonymDictJson = new JSONObject();
		try {
			PrintWriter writer = new PrintWriter("/Users/harshfatepuria/Desktop/NLP PROJECT/MavenProject/hindi/src/main/java/hindipos/hindi/capital.json", "UTF-8");

			FileReader fileReader = new FileReader("/Users/harshfatepuria/Desktop/NLP PROJECT/MavenProject/hindi/src/main/java/hindipos/hindi/names.txt");
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			GoogleTranslateAPI translator = new GoogleTranslateAPI("AIzaSyBEnSzexXv-Ve1E-d9rjHvygguF6rX9I8U");
			
			 while((orgline = bufferedReader.readLine()) != null) {
	                System.out.println("\n\n"+orgline);
	                orgline=orgline.trim();
	                
	                if(orgline.equals("")==false && orgline.startsWith("n.a.")==false)
	                {
	                	line=orgline.replace(" and ", " ");
	                	line=line.replace("NADI", "");
	                	line=line.replace("nadi", "");
	                	int indexOfC=line.indexOf("(");
	                	if(indexOfC>0)
	                	{
	                		line=line.substring(0, indexOfC);
	                	}
	                	line=line.trim();
	                	
	            		text = translator.translte(line, "en", "hi");
	            		System.out.println(text);
	          
	            		syns=App.getSynonyms(text);
	                
	            		if(syns.size()>0)
	            		{
//	            				System.out.print("[");
//	            				for( i=0;i<syns.size()-1; i++ )
//	            					System.out.print("\""+syns.get(i)+"\" , ");
//	            				System.out.println("\""+syns.get(i)+"\"]");
	            			JSONArray jsonArrayInnerChildren = new JSONArray();
	            			for( i=0;i<syns.size(); i++ )
	            				jsonArrayInnerChildren.add(syns.get(i));
	            			
	            			synonymDictJson.put(orgline.toString(), jsonArrayInnerChildren);
	            			
	            			
	            			
	            		}
					
	                }
	             
	            }   

	            bufferedReader.close(); 
	            
	            
	            writer.println(synonymDictJson);
      	        writer.close(); 
			
		} 
		catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file");                  
        }
	}
}