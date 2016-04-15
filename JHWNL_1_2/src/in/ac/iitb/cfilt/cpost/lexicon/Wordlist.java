package in.ac.iitb.cfilt.cpost.lexicon;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.ac.iitb.cfilt.cpost.ConfigReader;
import in.ac.iitb.cfilt.prop.AppProperties;

public class Wordlist{
	private HashMap<String, HashMap<String,Vector<String>>> wordHash;
	private HashMap<String, Vector<WordProperties>> wordToPropertiesMap;
	private HashMap<String,String> parCat;
	private String lexName = "";
//	private static FileReader lexicon;
	private boolean populated = false;
	private ConfigReader config = null;
	public Wordlist(){
//		lexName = ConfigReader.get("Lexicon.lexName");
//		wordHash = new HashMap<String, HashMap<String,Vector<String>>>();
//		parCat= new HashMap<String,String>();
//		generateWordlist(lexName);
	}

	public void populate(){
		if(!lexName.equals(ConfigReader.get("Lexicon.lexName"))){
			populated = false;
		}
		if(!populated){
			lexName = ConfigReader.get("Lexicon.lexName");
			wordHash = new HashMap<String, HashMap<String,Vector<String>>>();
			wordToPropertiesMap = new HashMap<String, Vector<WordProperties>>();
			parCat= new HashMap<String,String>();
			generateWordlist(lexName);
			populated = true;
		}
	}

	/**
	 *  Constructor for userdefined Lexicon file.
	 */
	/*
	public Wordlist(String lexName) {
		wordHash = new HashMap<String, HashMap<String,Vector<String>>>();
		parCat=new HashMap<String, String>();
		generateWordlist(lexName);
	}
	 */
	private void generateWordlist(String lexiconFilename) {
		System.out.println("Wordlist reading begins : " + new Timestamp(System.currentTimeMillis()));
		try {
			if(lexiconFilename.startsWith("$setu")){
				System.out.println("starts with setu");
				String path=AppProperties.getProperty("setu");
				if((path.substring(path.length()-1)).equals("/")){
					path=path.substring(0,path.length()-1);
				}
				lexiconFilename=path.concat(lexiconFilename.substring(5));
				//System.out.println(path);
			}

			String line;
			BufferedReader bRead = new BufferedReader(new InputStreamReader(new FileInputStream(lexiconFilename), "UTF8"));
			line = " ";

			while (line != null){
				line = bRead.readLine();
				if(line != null){
					line = line.trim();
					if(line.length()!=0 && !line.startsWith("//"))
						hashIt(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Wordlist reading ends : " + new Timestamp(System.currentTimeMillis()));
	}

	public String getCategory(String paradigm){
		return parCat.get(paradigm);
	}

	private void hashIt(String line){

		Pattern pEnd = Pattern.compile(">");
		Matcher mEnd = pEnd.matcher(line);
		line = mEnd.replaceAll("");

		Pattern pStart = Pattern.compile("<");
		Matcher mStart = pStart.matcher(line);
		line = mStart.replaceAll("");

		Pattern pSplit = Pattern.compile(",");
		String[] lineComponents = pSplit.split(line);

		//System.out.println("line is "+ line);
		String word = lineComponents[0].trim();
		String paradigm = lineComponents[1].trim();
		String category = lineComponents[2].trim();

		parCat.put(paradigm,category);

		String first_char = word.substring(0, 1);//get index character
		//System.out.println("first_char is "+ first_char);
		if (wordHash.containsKey(first_char)) {

			HashMap<String,Vector<String>> par_word = (HashMap<String,Vector<String>>) wordHash.get(first_char);

			if (par_word.containsKey(paradigm)) {

				Vector<String> words = par_word.get(paradigm);
				words.add(word.trim());
				par_word.put(paradigm, words);
				wordHash.put(first_char, par_word);

			} else {

				Vector<String> words = new Vector<String>();
				words.add(word.trim());
				par_word.put(paradigm, words);
				wordHash.put(first_char, par_word);

			}

		} else {

			Vector<String> words = new Vector<String>();
			HashMap<String,Vector<String>> par_word = new HashMap<String,Vector<String>>();

			words.add(word.trim());
			par_word.put(paradigm, words);
			wordHash.put(first_char, par_word);
		}
		
		if(wordToPropertiesMap.containsKey(word)){
			Vector<WordProperties> props = wordToPropertiesMap.get(word);
			WordProperties newProperties = new WordProperties(paradigm, category);
			if(!props.contains(newProperties)){
				props.add(newProperties);
			}
		}
		else{
			Vector<WordProperties> props = new Vector<WordProperties>();
			props.add(new WordProperties(paradigm, category));
			wordToPropertiesMap.put(word, props);
		}
	}

	public WordProperties searchWordlistFor(String s, String par){
		WordProperties retVal = null;
		s = s.trim();
		par = par.trim();
		if (!(s.equals(null) || s.equals(""))){ 
			String first_char= s.substring(0,1);
			if (wordHash.containsKey(first_char)){
				HashMap<String,Vector<String>> par_word = wordHash.get(first_char);

				Vector<String> words = par_word.get(par);
				if(words != null && words.contains(s)){
					retVal = new WordProperties(par, (String) parCat.get(par));
				}
				else{
					words = par_word.get("nst");
					if(words != null && words.contains(s)){
						retVal = new WordProperties("nst", "nst");
					}
					else{
						boolean stemmerHack = true;
						if(stemmerHack){
//							words = par_word.get("wh");
//							if(words != null && words.contains(s)){
//							retVal = new WordProperties("wh", "wh");
//							}
//							else{
//							words = par_word.get("direct_pn");
//							if(words != null && words.contains(s)){
//							retVal = new WordProperties("direct_pn", "pn");
//							}
//							else{
//							words = par_word.get("oblique_pn");
//							if(words != null && words.contains(s)){
//							retVal = new WordProperties("oblique_pn", "pn");
//							}
//							else{
							words = par_word.get("proper_noun");
							if(words != null && words.contains(s)){
								retVal = new WordProperties("proper_noun", "pnoun");
							}
							else{
								words = par_word.get("ex_noun");
								if(words != null && words.contains(s)){
									retVal = new WordProperties("ex_noun", "noun");
								}
							}
//							}
//							}
//							}
						}
					}
				}
			}
		}
		return retVal;
	}


	public Vector<WordProperties> searchWordlistFor(String s) {
		Vector<WordProperties> retVal = null;
		s = s.trim();
//		System.out.println("Searching for '" + s + "'");
		if(!s.equals(null) && !s.equals("")){
			String firstChar = s.substring(0, 1);
			if(wordHash.containsKey(firstChar)){
				retVal = new Vector<WordProperties>();
				HashMap<String,Vector<String>> par_word = wordHash.get(firstChar);
				Set<String> paradigms = par_word.keySet();
//				System.out.println("paradigms.contains(\"verb\") : " + paradigms.contains("verb"));
//				System.out.println("((Vector)par_word.get(\"verb\")).contains("+s+")" + ((Vector)par_word.get("verb")).contains(s));
				Iterator paradigmIter = paradigms.iterator();
				while(paradigmIter.hasNext()){
					String par = (String) paradigmIter.next();
//					if(par.equals("cardinal")){
//					System.out.println(par_word.get(par));
//					}
					if(par_word.get(par).contains(s)){
						WordProperties wp = new WordProperties(par, (String)parCat.get(par));
						retVal.add(wp);
					}
				}
			}
		}
		return retVal;
	}
	
	public Set<String> getAmbiguitySchemes(){
		HashSet<String> retVal = new HashSet<String>();
		Set<String> words = wordToPropertiesMap.keySet();
		HashSet<String> currentCategories = new HashSet<String>();
		for(String word : words){
			Vector<WordProperties> props = wordToPropertiesMap.get(word);
			for(WordProperties property : props){
				currentCategories.add(property.getCategory());
			}
			Vector<String> currentCategoriesVector = new Vector<String>(currentCategories);
			Collections.sort(currentCategoriesVector);
			String currentCategoryString = "";
			for(String category : currentCategoriesVector){
				currentCategoryString += category+"*";
			}
			retVal.add(currentCategoryString.substring(0, currentCategoryString.length()-1));
		}
		return retVal;
	}
}

