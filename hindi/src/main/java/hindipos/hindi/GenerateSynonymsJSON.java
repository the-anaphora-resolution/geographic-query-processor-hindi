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
			PrintWriter writer = new PrintWriter("/Users/harshfatepuria/Desktop/NLP PROJECT/MavenProject/hindi/src/main/java/hindipos/hindi/lake.json", "UTF-8");

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
	                	//line=line.replace("River", "");
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
	            				System.out.print("[");
	            				for( i=0;i<syns.size()-1; i++ )
	            					System.out.print("\""+syns.get(i)+"\" , ");
	            				System.out.println("\""+syns.get(i)+"\"]");
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

//
//
//public class MetadataScore {
//
//	
//	public static void main(String[] args) throws IOException {
//		
//		String polarDumpPrefix="/Volumes/ETC/polar-fulldump/";
//		int i,j,count=0;
//		JSONObject responseDetailsJson = new JSONObject();
//		responseDetailsJson.put("name", "metadataScore");
//		JSONArray jsonArrayMainChildren = new JSONArray();
//		try {
//			
//			 PrintWriter writer = new PrintWriter("/Users/harshfatepuria/Desktop/599/HW2/FINAL FILES/metadataScore.json", "UTF-8");
//			
//			 
//			 FileReader reader = new FileReader("/Users/harshfatepuria/Documents/Github/Scientific-Content-Enrichment-in-the-Text-Retrieval-Conference-TREC-Polar-Dynamic-Domain-Dataset/fulldump-path-all-json/listOfFiles.json");
//	         JSONParser jsonParser = new JSONParser();
//	         JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
//
//	         JSONArray lang= (JSONArray) jsonObject.get("files");
//	         
//	       
//	         // take the elements of the json array
//	         for(i=0; i<lang.size(); i++){
//	        	 	String fileName=lang.get(i).toString();
//	        	 	
//	        	 	JSONObject individualFileData = new JSONObject();
//	        	 	individualFileData.put("name", fileName);
//	        	 	
//	        	 	JSONArray jsonArrayInnerChildren = new JSONArray();
//	            	
//	                // read the json file
//	                FileReader reader2 = new FileReader("/Users/harshfatepuria/Documents/Github/Scientific-Content-Enrichment-in-the-Text-Retrieval-Conference-TREC-Polar-Dynamic-Domain-Dataset/fulldump-path-all-json/"+fileName);
//	                JSONParser jsonParser2 = new JSONParser();
//	                JSONObject jsonObject2 = (JSONObject) jsonParser2.parse(reader2);
//	                JSONArray lang2= (JSONArray) jsonObject2.get("files");
//	               
//	                // take the elements of the json array
//	                for(j=0; j<20; j++){
//	                	try{
//	                		ToXMLContentHandler handler = new ToXMLContentHandler();
//	                	    AutoDetectParser parser = new AutoDetectParser();
//	                	    Metadata metadata = new Metadata();
//	                	    InputStream stream =new FileInputStream(new File(polarDumpPrefix+(lang2.get(j).toString())));
//	            	        parser.parse(stream, handler, metadata);
//	            	        count=(int)metadata.size();
//	            	        JSONObject metadataJSON = new JSONObject();
//	            	        
//	            	        String filePathComplete=lang2.get(j).toString();
//	            	        
//	            	        metadataJSON.put("name", filePathComplete.substring((1+(filePathComplete.lastIndexOf('/'))))+": "+count);
//	            	        metadataJSON.put("size", count);
//	            	        jsonArrayInnerChildren.add(metadataJSON);
//	                	}
//	                	catch(Exception e){
//	                		continue;
//	                	}	
//	                }
//	                individualFileData.put("children", jsonArrayInnerChildren);
//	                jsonArrayMainChildren.add(individualFileData);
//	         }
//	         responseDetailsJson.put("children", jsonArrayMainChildren);
//	         JSONObject jsonObjectFinal = responseDetailsJson;
//	         writer.println(jsonObjectFinal);
//	         writer.close(); 
//		} 
//		catch(Exception e){
//    		e.getStackTrace();
//    	}
//	}
//}