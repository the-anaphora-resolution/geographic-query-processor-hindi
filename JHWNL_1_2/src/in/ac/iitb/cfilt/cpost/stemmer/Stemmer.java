package in.ac.iitb.cfilt.cpost.stemmer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

//import iitb.cfilt.cpost.logger.Logger;
//import iitb.cfilt.cpost.tokenizer.TaggedTokenizer;
import in.ac.iitb.cfilt.cpost.ConfigReader;
import in.ac.iitb.cfilt.cpost.lexicon.WordProperties;
import in.ac.iitb.cfilt.cpost.lexicon.Wordlist;
import in.ac.iitb.cfilt.cpost.utils.AccuracyReportWriter;
import in.ac.iitb.cfilt.cpost.utils.UTFConsole;


public class Stemmer {
	//private Wordlist wordlist;
	//private StemmerRuleReader stemmerRuleReader;

	//Flags
	static boolean mmAtStemmer = true;
	static boolean useStemDictionary = false;
	private Wordlist wordlist = new Wordlist();
	private StemmerRuleReader stemmerRuleReader = null;
	
	static{
		System.out.println("Stemmer.mmAtStemmer : " + mmAtStemmer);
		System.out.println("Stemmer.useStemDictionary: " + useStemDictionary);
	}

	public HashMap<String, StemmedToken> stemDictionary;

	@SuppressWarnings("unchecked")
	public Stemmer(){
		//wordlist = new Wordlist();
		boolean fileFound = true;
		try {
			ObjectInput oin = new ObjectInputStream(new FileInputStream(ConfigReader.get("Stemmer.stemDictionary")));
			stemDictionary = (HashMap<String, StemmedToken>) oin.readObject();
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			fileFound = false;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(!fileFound || !useStemDictionary){
			stemDictionary = new HashMap<String, StemmedToken>();
		}
		wordlist.populate();
		stemmerRuleReader = new StemmerRuleReader(wordlist);
		stemmerRuleReader.populate();
	}

	static void writeStemDictionary(){
		try {
			ObjectOutput oout = new ObjectOutputStream(new FileOutputStream(ConfigReader.get("Stemmer.stemDictionary")));
			//oout.writeObject(stemDictionary);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Vector<StemmedToken> stem (Vector<String> tokens){
		Vector<StemmedToken> retVal = new Vector<StemmedToken>(tokens.size());
		for(int i = 0; i < tokens.size(); i++){
			StemmedToken currentStemmedToken = stem(tokens.get(i));
			if(currentStemmedToken != null){
				retVal.add(currentStemmedToken);
			}
		}
		if(retVal.size() == 0){
			retVal = null;
		}
		if(useStemDictionary){
			writeStemDictionary();
		}
		return retVal;
	}

	public StemmedToken stem(String token){
//		Logger.println("Stemming : " + token);
		StemmedToken retVal = null;
		if(false && stemDictionary.containsKey(token)){
			retVal = stemDictionary.get(token);
		}
		else{
			retVal = new StemmedToken(token);
			StemmerRuleResult specialCharacterResult = stemmerRuleReader.applySpecialCharacterRulesOn(token);
			if(specialCharacterResult != null){
				retVal.addStemmerOutput(specialCharacterResult);
			}

			Vector<StemmerRuleResult> lexItems = checkInLexicon(token);
			if(lexItems != null){
				retVal.addAllStemmerOutputs(lexItems);
			}


			Vector<StemmerRuleResult> suffixStrippedResults;

			suffixStrippedResults = checkWithSuffix(token); 
			if(suffixStrippedResults!=null){
				retVal.addAllStemmerOutputs(suffixStrippedResults);
			}

			if(retVal.getStemmedOutputs().size() == 0){
				Vector<String> spellingVariationsVector = stemmerRuleReader.getSpellingVariations(token);
				if(spellingVariationsVector != null){
					for(int i = 0; i < spellingVariationsVector.size(); i++){
						String currentSpellingVariation = spellingVariationsVector.get(i);
						lexItems = checkInLexicon(currentSpellingVariation);
						if(lexItems != null){
							retVal.addAllStemmerOutputs(lexItems);
						}
						suffixStrippedResults = checkWithSuffix(currentSpellingVariation);
						if(suffixStrippedResults!=null){
							retVal.addAllStemmerOutputs(suffixStrippedResults);
						}
					}
				}
			}

			if(retVal.getStemmedOutputs().size() == 0){
				//If still nothing found, add 'Unknown'
				StemmerRuleResult srresult = new StemmerRuleResult(token, "unknown", "unknown", "", "");
				retVal.addStemmerOutput(srresult);
			}
			else{
				// Code to perform Maximum Matching
//				boolean mmAtStemmer = false;
//				if(mmAtStemmer){
//				Vector<StemmerRuleResult> retValOutputs = retVal.getStemmedOutputs();
//				int leastSuffixLength = retValOutputs.get(0).getSuffixSize();
//				int currentSize;
//				HashSet<StemmerRuleResult> srrWithMaxMatching = new HashSet<StemmerRuleResult>();

//				for(int i = 0; i < retValOutputs.size(); i++){
//				currentSize = retValOutputs.get(i).getSuffixSize();
//				if(leastSuffixLength > currentSize){
//				leastSuffixLength = currentSize;
//				}
//				}
//				for(int i = 0; i < retValOutputs.size(); i++){
//				currentSize = retValOutputs.get(i).getSuffixSize();
//				if(currentSize == leastSuffixLength){
//				srrWithMaxMatching.add(retValOutputs.get(i));
//				}
//				}
//				retVal.clearOutputs();
//				retValOutputs.clear();
//				retValOutputs.addAll(srrWithMaxMatching);
//				retVal.addAllStemmerOutputs(retValOutputs);
//				}
			}
//			for(StemmerRuleResult srresult : retVal.getStemmedOutputs()){
//			Logger.println("\t" + srresult.toString());
//			}
			retVal.sortResults();

			stemDictionary.put(token, retVal);
		}
		return retVal;
	}

	private Vector<StemmerRuleResult> checkWithSuffix(String token) {
		Vector<StemmerRuleResult> retVal = new Vector<StemmerRuleResult>();
		int maxSuffixLength = 0;
		String maxSuffix = "";
		String maxParadigm = "";
		String maxCategory = "";
		String maxRoot = "";
		LinkedList<String> maxSuffixes = null;
		Vector<StemmerRuleResult> suffixReplacedTokenVector = stemmerRuleReader.applySuffixReplacementRulesOn(token);
		if(suffixReplacedTokenVector != null){
			for(int i = 0; i < suffixReplacedTokenVector.size(); i++){
				StemmerRuleResult currentResult = suffixReplacedTokenVector.get(i);
				String root = currentResult.getRoot();
				String paradigm = currentResult.getParadigm();
				LinkedList<String> suffixes = currentResult.getSuffixList();

				StemmerRuleResult srresult = null;
//				System.out.println("Root : " + root + " Paradigm : " + paradigm + " Category : " + currentResult.getCategory());
//				System.out.println("Searching " + root + " in lexicon for paradigm " + paradigm);

				WordProperties wp = wordlist.searchWordlistFor(root, paradigm);
				if(wp != null) {
					srresult = new StemmerRuleResult(root, wp.getParadigm(), wp.getCategory(), "", suffixes);
//					System.out.println("Found in Lexicon : " + root + " for token : " + token + " lexiconParadigm : " + wp.getParadigm() + " ruleParadigm : " + paradigm + " Category : " + wp.getCategory());
					if(!retVal.contains(srresult))
						retVal.add(srresult);
				}
//				Vector<WordProperties> wpv = wordlist.searchWordlistFor(root);
//				if(wpv != null){
//				for(int k = 0; k < wpv.size(); k++){
//				WordProperties wp = wpv.get(k);
//				srresult = new StemmerRuleResult(root, paradigm, wp.getCategory(), "", suffixes);
//				System.out.println("Found in Lexicon : " + root + " for token : " + token + " lexiconParadigm : " + wp.getParadigm() + " ruleParadigm : " + paradigm);
//				if(!retVal.contains(srresult)){
//				retVal.add(srresult);
//				}
//				}
//				}
				else {
					/*Hack begins*/
					if(paradigm.equals("number")){
						srresult = new StemmerRuleResult(root, "number", "number", "", suffixes);
						if(!retVal.contains(srresult))
							retVal.add(srresult);
					}
					/*Hack ends*/
					if(stemmerRuleReader.isAuxiliary(root) && suffixes.size() < 2){
//						System.out.println(root + " is auxiliary");
						srresult = new StemmerRuleResult(root, "vaux", "verb_aux", "", suffixes);
						if(!retVal.contains(srresult))
							retVal.add(srresult);
					}
					if(paradigm.equals("verb") && (stemmerRuleReader.hasRegularRootForm(root)||stemmerRuleReader.hasRegularRootForm(token))){
						String regularRootForm=null;
						if(stemmerRuleReader.hasRegularRootForm(root))
							regularRootForm = stemmerRuleReader.getRegularRootForm(root);
						else
							if (stemmerRuleReader.hasRegularRootForm(token))
								regularRootForm = stemmerRuleReader.getRegularRootForm(token);
//						System.out.println("The regular root for " + token + " is " + regularRootForm);
						srresult = new StemmerRuleResult(regularRootForm, paradigm, paradigm, "", suffixes);
						if(!retVal.contains(srresult))
							retVal.add(srresult);
//						System.out.println("Inside checkWithSuffix : Added :: " + srresult.toString());

						if(stemmerRuleReader.isAuxiliary(regularRootForm)){
							srresult = new StemmerRuleResult(regularRootForm, "vaux", "verb_aux", "", suffixes);
							if(!retVal.contains(srresult))
								retVal.add(srresult);
						}
						if(stemmerRuleReader.hasUniqueSuffix(suffixes)){
							String uniqueSuffix = stemmerRuleReader.getUniqueSuffix(suffixes);
							if(uniqueSuffix.length() > maxSuffixLength){
								maxSuffix = uniqueSuffix;
								maxSuffixes = suffixes;
								maxSuffixLength = maxSuffix.length();
								maxParadigm = paradigm;
								maxCategory = wordlist.getCategory(paradigm);
								maxRoot = regularRootForm;
							}
						}
					}
				}
			}
		}
		if(retVal.size() == 0 && maxSuffixLength>0){
			StemmerRuleResult srresult = new StemmerRuleResult(maxRoot, maxParadigm, maxCategory, "", maxSuffixes);
			if(!retVal.contains(srresult)){
				retVal.add(srresult);
			}
		}
		if(retVal.size() == 0){
			retVal = null;
		}
		else{
			// Code to perform Maximum Matching --- Code moved to stem() function

			if(mmAtStemmer){


				int leastSuffixLength = retVal.get(0).getSuffixSize();
				int currentSize;
				HashSet<StemmerRuleResult> srrWithMaxMatching = new HashSet<StemmerRuleResult>();

				for(int i = 0; i < retVal.size(); i++){
					currentSize = retVal.get(i).getSuffixSize();
//					UTFConsole.out.println("Root: " + retVal.get(i).getRoot() + " suffixSize : " + currentSize);
					if(leastSuffixLength > currentSize){
						leastSuffixLength = currentSize;

					}
				}
				for(int i = 0; i < retVal.size(); i++){
					currentSize = retVal.get(i).getSuffixSize();
					if(currentSize == leastSuffixLength){
						srrWithMaxMatching.add(retVal.get(i));
					}
				}
				retVal.clear();
				retVal.addAll(srrWithMaxMatching);
			}
		}
		return retVal;
	}

	private Vector<StemmerRuleResult> checkInLexicon(String token) {
		Vector<StemmerRuleResult> retVal = new Vector<StemmerRuleResult>();
		Vector<WordProperties> tokenProperties = wordlist.searchWordlistFor(token);
		if(tokenProperties != null){
			for(int i = 0; i < tokenProperties.size(); i++){
				WordProperties tokenProperty = tokenProperties.get(i);
//				System.out.println("Found in Lexicon (fullsearch): " + token + " lexiconParadigm : " + tokenProperty.getParadigm() + " Category : " + tokenProperty.getCategory());
				StemmerRuleResult srresult = new StemmerRuleResult(token, tokenProperty.getParadigm(), tokenProperty.getCategory(),"", "");
				if(!retVal.contains(srresult))
					retVal.add(srresult);
			}
		}
		if(stemmerRuleReader.isAuxiliary(token)){
			StemmerRuleResult srresult = new StemmerRuleResult(token, "vaux", "verb_aux", "", "");
			if(!retVal.contains(srresult))
				retVal.add(srresult);
		}
		if(retVal.size() == 0){
			Vector<String[]> derivationalMorphologyOutput = stemmerRuleReader.checkDerivationalMorphology(token);
			if(derivationalMorphologyOutput != null){
				for(int i = 0; i < derivationalMorphologyOutput.size(); i++){
					String[] properties = derivationalMorphologyOutput.get(i);
					String derivedRoot = properties[0];
					String derivedRootCategory = properties[1];
					String derivedNewCategory = properties[2];

					Vector<WordProperties> derivedRootWordProperties = wordlist.searchWordlistFor(derivedRoot);
					if(derivedRootWordProperties != null){
						for(int j = 0; j < derivedRootWordProperties.size(); j++){
							WordProperties dwp = derivedRootWordProperties.get(j);
							if(dwp.getCategory().equals(derivedRootCategory)){
								StemmerRuleResult srresult = new StemmerRuleResult(derivedRoot, "", derivedNewCategory,"","");
								if(!retVal.contains(srresult))
									retVal.add(srresult);
							}
						}
					}
				}
			}

		}
		if(retVal.size() == 0){
			retVal = null;
		}
		return retVal;
	}

	/*public static void main(String args[]){
		//ConfigReader.read("Resources/hindiConfig");
		ConfigReader.read(args[0]);
		//StemmerRuleReader srreader = new StemmerRuleReader();
		TaggedTokenizer tt = new TaggedTokenizer();
		//tt.tokenize("/mnt/dgf1/home/dineshg/workspace/CPOST/TestSet");
		tt.tokenize(args[1]);
		Vector<String> tokens =  tt.getTokens();
//		Vector<String> tags = tt.getTags();

		Stemmer stemmer = new Stemmer();
		Vector<StemmedToken> stemmedTokens = stemmer.stem(tokens);
		
		AccuracyReportWriter arw = new AccuracyReportWriter("StemmerOutput");
		for(int i = 0; i < stemmedTokens.size(); i++){
			UTFConsole.out.println((i+1)+")"+stemmedTokens.get(i).getToken());
			arw.out.println((i+1)+")"+stemmedTokens.get(i).getToken());
			for(StemmerRuleResult srresult : stemmedTokens.get(i).getStemmedOutputs()){
				UTFConsole.out.println("Root : " + srresult.getRoot() + " Category : " + srresult.getCategory());
				arw.out.println("Root : " + srresult.getRoot() + " Category : " + srresult.getCategory());
			}
		}
		UTFConsole.out.println(tokens.size() + " tokens stemmed.");
		 
		 Uncomment the following snippet to get unstemmed words.
		<snippet>
		AccuracyReportWriter arw = new AccuracyReportWriter("Unstemmed");
		for(int i = 0; i < stemmedTokens.size(); i++){
			if(stemmedTokens.get(i).getStemmedOutputs().get(0).getCategory().equals("unknown")){
				UTFConsole.out.println(tt.getTaggedTokens().get(i));
				arw.out.println(tt.getTaggedTokens().get(i));
			}
		}
		</snippet>
		
		AccuracyReportWriter arw = new AccuracyReportWriter("StemmerCheck");
		UTFConsole.out.println("Token\t\t\t\tRoot\t\t\t\tCategory");
		arw.out.println("Token\t\t\t\tRoot\t\t\t\tCategory");
		for(int i = 0; i < stemmedTokens.size(); i++){
			//UTFConsole.out.println(stemmedTokens.get(i).getToken() );
			//arw.out.println((i+1)+")"+stemmedTokens.get(i).getToken());
			for(StemmerRuleResult srresult : stemmedTokens.get(i).getStemmedOutputs()){
				UTFConsole.out.println(stemmedTokens.get(i).getToken() + "\t\t\t\t" + srresult.getRoot() + "\t\t\t\t" + srresult.getCategory());
				arw.out.println(stemmedTokens.get(i).getToken() + "\t\t\t\t" + srresult.getRoot() + "\t\t\t\t" + srresult.getCategory());
			}
		}
		 
		
		try {
			FileReader fr = new FileReader("Resources/hindiTokens");
			BufferedReader bfr = new BufferedReader(fr);
			String token = null;
			token = bfr.readLine();
			while(token != null){
				StemmedToken st = stemmer.stem(token);
				Vector<StemmerRuleResult> srresult = st.getStemmedOutputs();
				System.out.println("Token : " + token);
				for(int i=0; i < srresult.size(); i++){
					System.out.println(srresult.elementAt(i).toString());
				}
				token = bfr.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}*/
}
