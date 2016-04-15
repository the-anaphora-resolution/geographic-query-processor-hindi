// CFILT LAB
// Department of Computer Science and Engineering
// Indian Institute of Technology Bombay, Mumbai, India
// Author: Manish Sinha
// Abstract representation of a WordNet dictionary.
// **********************************************************************************************************
package in.ac.iitb.cfilt.jhwnl.dictionary;

import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.configuration.WNProperties;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.POS;
import in.ac.iitb.cfilt.jhwnl.data.Pointer;
import in.ac.iitb.cfilt.jhwnl.data.PointerType;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.data.Word;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//public class Dictionary extends java.lang.Object implements Installable {
/**
 * CFILT LAB Department of Computer Science and Engineering Indian Institute of
 * Technology Bombay, Mumbai, India Author: Aditya Sharma
 * 
 * Abstract representation of a WordNet dictionary using text-file databases.
 * 
 */
public class FileDictionary extends Dictionary {
	
	/* The IndexFile Hashmaps */
	private HashMap<String, String> mIdxNounHashmap = new HashMap<String, String>();
	private HashMap<String, String> mIdxAdjHashmap = new HashMap<String, String>();
	private HashMap<String, String> mIdxVerbHashmap = new HashMap<String, String>();
	private HashMap<String, String> mIdxAdvHashmap = new HashMap<String, String>();
	
	/* The Data files Hashmaps */
	private HashMap<Long,String> mDataHashmap = new HashMap<Long, String>();
	private HashMap<Long,String> mOntoHashmap = new HashMap<Long, String>();
	
	BufferedWriter errorFile;
	boolean enableLogging = false;
	
	protected FileDictionary() {
		super();
		String databasePath = WNProperties.getProperty("filedata.path");
		if(databasePath == null) {
			System.err.println("Couldn't find Text File Database path. Defaulting to \"./database/*\"");
			databasePath = "database";
		}
		try {
			BufferedReader aIdxNoun = new BufferedReader(new InputStreamReader(new FileInputStream(
					databasePath + File.separatorChar+ "idxnoun_txt"), "UTF8"));
			BufferedReader aIdxAdj = new BufferedReader(new InputStreamReader(new FileInputStream(
					databasePath + File.separatorChar+ "idxadjective_txt"), "UTF8"));
			BufferedReader aIdxVerb = new BufferedReader(new InputStreamReader(new FileInputStream(
					databasePath + File.separatorChar+ "idxverb_txt"), "UTF8"));
			BufferedReader aIdxAdv = new BufferedReader(new InputStreamReader(new FileInputStream(
					databasePath + File.separatorChar+ "idxadverb_txt"), "UTF8"));
			BufferedReader aDataFile = new BufferedReader(new InputStreamReader(new FileInputStream(
					databasePath + File.separatorChar+ "data_txt"), "UTF8"));
			BufferedReader aOntoFile = new BufferedReader(new InputStreamReader(new FileInputStream(
					databasePath + File.separatorChar+ "onto_txt"), "UTF8"));
			
			/* Read each index file and place "word,lines" into HashMaps*/
			processIndexFile(mIdxNounHashmap, aIdxNoun);
			processIndexFile(mIdxAdjHashmap, aIdxAdj);
			processIndexFile(mIdxVerbHashmap, aIdxVerb);
			processIndexFile(mIdxAdvHashmap, aIdxAdv);
					
			/* Read each data file and place "synset-id,lines" into HashMaps*/
			processDataFile(mDataHashmap, aDataFile);
			processDataFile(mOntoHashmap, aOntoFile);
			
			aIdxNoun.close();
			aIdxVerb.close();
			aOntoFile.close();
			aIdxAdv.close();
			aIdxAdj.close();
			aDataFile.close();	
			
			enableLogging = Boolean.parseBoolean(WNProperties.getProperty("enable.error.logging"));
			if(enableLogging) {			
				String errorFileName = WNProperties.getProperty("error.file");
				if(errorFileName == null || errorFileName.length() == 0) {
					errorFileName = "hwn_errors.txt";
				}
				errorFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(errorFileName),"UTF8"));
			}		
			
			 
		} catch (FileNotFoundException e) {
			System.err.println("Wordnet Text Database not found. Please check the path specified. \n" + e);
			System.exit(-3);
		} catch (IOException e) {
			System.err.println("Error in reading Wordnet files.");
			e.printStackTrace();
			System.exit(-3);
		}

	}

	private void processIndexFile(HashMap<String, String> pIdxHashmap, BufferedReader pIdxBufReader) throws IOException {
		String aIndexEntry;
		Pattern aCommentPattern = Pattern.compile("^[ #*]");		
		while((aIndexEntry = pIdxBufReader.readLine()) != null) {					
			Matcher aMatcher = aCommentPattern.matcher(aIndexEntry);
			if(aMatcher.matches()){		// This line is a comment line
				continue;				// Read next line
			} else {
				int wordEndsAt = aIndexEntry.indexOf(' ');
				String wordEntry = aIndexEntry.substring(0,wordEndsAt);
				pIdxHashmap.put(wordEntry, aIndexEntry);
			}
		}	
	}

	private void processDataFile(HashMap<Long, String> pDataHashmap, BufferedReader pDataFile) throws NumberFormatException, IOException {
		String aSynsetEntry;
		Pattern aCommentPattern = Pattern.compile("^[ #*]");		
		while((aSynsetEntry = pDataFile.readLine()) != null) {					
			Matcher aMatcher = aCommentPattern.matcher(aSynsetEntry);
			if(aMatcher.matches()){		// This line is a comment line
				continue;				// Read next line
			} else {
				int wordEndsAt = aSynsetEntry.indexOf(' ');
				String wordEntry = aSynsetEntry.substring(0,wordEndsAt);
				Long aSynsetId = Long.parseLong(wordEntry);
				pDataHashmap.put(aSynsetId, aSynsetEntry);
			}
		}	
	}

	static Dictionary DICTIONARY = null;

	

	@Override
	public Synset getSynsetAt(POS pos, long synsetId) throws JHWNLException {
		return parseSynset(pos, synsetId);		
	}

	@Override
	public Synset getOntoSynset(long synsetId) throws JHWNLException {
		return parseOntologySynset(synsetId);		
	}
	
	@Override
	public IndexWord getSynsetsAsIndexWord(POS mPos, String lemma) throws JHWNLException {
		
		long[] synsetOffsets;	
		int sensecount = 0;
		int counterposition;
		//POS mPOS = null;
		Synset[] synsets = null;

		String searchString = lemma;
		// Replace all " " (space characters by underscore in searchString, same as in the Wordnet Database)
		searchString = searchString.trim().replaceAll(" ","_");
		String aSynsetEntry = "";
		
		switch (mPos.posvalue) {
		case 1: 
			aSynsetEntry = mIdxNounHashmap.get(searchString);
			break;		
		case 2: 
			aSynsetEntry = mIdxAdjHashmap.get(searchString);
			break;
		case 3: 
			aSynsetEntry = mIdxVerbHashmap.get(searchString);
			break;	
		case 4: 
			aSynsetEntry = mIdxAdvHashmap.get(searchString);
			break;			
		} 

		if (aSynsetEntry != null) { 
			String[] aSynsetData = aSynsetEntry.split(" ");		

			int i = Integer.parseInt(aSynsetData[1]);			
			int pointercount = Integer.parseInt(aSynsetData[2]);
			counterposition = pointercount + 3;
			sensecount = Integer.parseInt(aSynsetData[counterposition]);

			//System.out.println("From IndexWord: SenseCount " + sensecount);
			//System.out.println("SenseCountstr " + str[counterposition]);
			//counterposition++;
			//System.out.println("counterposition is " + counterposition);         

			int j = 0; // 0 is changed to 1
			synsets = new Synset[sensecount];
			synsetOffsets = new long[sensecount];
			for ( i = counterposition + 1;i <= (counterposition + sensecount); i++ ) {
				synsetOffsets[j] = Long.parseLong(aSynsetData[i]);	
				//System.out.println(i);

				synsets[j] = Dictionary.getInstance().getSynsetAt(mPos,synsetOffsets[j]); 
				j++;
			}
		} else {
			return null;
		}		
		
		return new IndexWord(mPos,synsets, searchString);
	}
	
	
	/**
	 * Method 	: parseSynset
	 * Purpose	: Parse a line in text file database to get the synset. 
	 * @param pos
	 * @param synsetId
	 * @return a {@link Synset} object corresponding to the line. 
	 */
	private Synset parseSynset(POS pos,long synsetId) {
		Synset aSynset;
		String [] aSynsetData;
		String gloss;
		Word[] synsetWords = new Word[0];
		Pointer [] synsetPointers;
		int relations;
		boolean hasantonym = false;
		Vector<String> wordLexicalRelWordPairList = new Vector<String>(); // akshay

				
		String aSynsetEntry = mDataHashmap.get(synsetId);
		if (aSynsetEntry != null) {

			int glossStart = aSynsetEntry.indexOf('|');			
			gloss = aSynsetEntry.substring(glossStart + 1);
			
			aSynsetData = aSynsetEntry.substring(0, glossStart).split(" ");
			
			int wordPosition = 0; //0 is changed to 1
			POS synsetPos = new POS(Integer.parseInt(aSynsetData[1]));
			if(pos == null){						// If queried pos was null, set it to actual value	
				pos = synsetPos;
			} else if(!pos.equals(synsetPos)) {		// Else Check whether synset with specified synset_id has the same POS as the input parameter
				//pos = synsetPos;
				return null;						// And return null if it hasn't
			} 
			aSynset = new Synset(false, gloss, synsetId, null, null, pos, null);
			String[] aWordEntries = aSynsetData[3].split(":");		
			Vector<Word> wordVector = new Vector<Word>();
			for (int i = 0; i < aWordEntries.length; i++) {
				wordVector.add(new Word(aSynset, wordPosition, aWordEntries[i]));
				wordPosition++;
			}			
			
			if(wordVector.size() > 0) {	// At least one word found
				synsetWords = wordVector.toArray(synsetWords);
			}
			else {
				synsetWords = null;
			}
			
			relations = Integer.parseInt(aSynsetData[4]);	
			synsetPointers = new Pointer[relations];
			int j = 0;
			int n = 5;
			for ( int i = 0; i < relations; i++ ) {
				/* Pointer information is encoded through 4 digits in folowing manner "XYZZ"
				 where X -> part-of-speech
				             1 Noun
				             2 Adjective
				             3 Verb
				             4 Adverb
				       Y -> relation type
				             1 Semantic
				             2 Lexical
				             3 Gradation
				             4 Ontology
				      ZZ -> subtype in that category				
				*/
				
				n = n + j;
				POS targetPos = null;
				int targetPosIntVal =  Integer.parseInt("" + aSynsetData[n].charAt(0));
				switch (targetPosIntVal) {
				case 1:
					targetPos = POS.NOUN;
					break;
				case 2:
					targetPos = POS.ADJECTIVE;
					break;
				case 3:
					targetPos = POS.VERB;
					break;
				case 4:
					targetPos = POS.ADVERB;
					break;
				default:
					break;
				}

				 
				if ( aSynsetData[n].charAt(1) == '1' ) {// 	Semantic Relation
					PointerType pointertype = new PointerType(aSynsetData[n].substring(2));
					long targetoffset = Long.parseLong(aSynsetData[n+1]);
					try {	
						synsetPointers[i] = new Pointer(aSynset,0,pointertype,targetPos,targetoffset,0,synsetId);
					} catch(NullPointerException ex) {
						System.out.println("semantic " + synsetId + ex);
					}
					j = 2;	
				} else	if ( aSynsetData[n].charAt(1) == '2' ) {
					PointerType pointertype = new PointerType(aSynsetData[n].substring(2));
					String temp;
					int sourceWordIndex = Integer.parseInt(aSynsetData[n+1].substring(0,2));
					int targetWordIndex = Integer.parseInt(aSynsetData[n+1].substring(2));
					
					/* Subtracted 1 for getting correct WordIndices in Arrays 
					 * (Java Array start with 0, 'index' refers to index'th word) - Aditya*/ 
					sourceWordIndex--;
					targetWordIndex--;				
					
					long targetSynsetId = Long.parseLong(aSynsetData[n+2]);
					String targetSynsetData  = "";
					try {
					if(pointertype.toString().contains("ANTO") || pointertype.toString().contains("COMP")){
						hasantonym = true;
						/* 	When the antonyms are stored in the synset line itself
						String wordLexicalRelWordPair = aSynsetData[n+3]; //------- akshay*/
						//Word wordLexicalRelWordPair = new Word(this, index, str[n+3]);
						
						// Get the lexical relation word-pair
						targetSynsetData = mDataHashmap.get(targetSynsetId);
						String wordLexicalRelWordPair = synsetWords[sourceWordIndex] + 
							":"+targetSynsetData.split(" ")[3].split(":")[targetWordIndex];
						wordLexicalRelWordPairList.add(wordLexicalRelWordPair); //---- akshay
					}
					//System.out.println("Synset.java - Case = 2: targetoffset "+ targetoffset);
						
						//PT[i] = new Word[];
						synsetPointers[i] = new Pointer(synsetWords[sourceWordIndex],sourceWordIndex,pointertype,targetPos,targetSynsetId,targetWordIndex, synsetId);// <------ orig
						//pointerArr[i] = new Pointer(wordplusanto,index,pointertype,pos,targetoffset,targetIndex); // -akshay.
					} catch(ArrayIndexOutOfBoundsException e) {
						/* This exception comes because of relations in
						 * database( eg. referring to a non-existing word in synset)
						 * Write this in a file to report to the Lexicographers */
						String errorMsg = "Error in ANTONYMY relation for synset " + synsetId  + ", " + pos  + " with synset " + targetSynsetData;
						if(enableLogging) {
							try {							 
								errorFile.write(errorMsg);
							} catch(IOException e1) {
								// Error writing to file. Simply echo to error stream
								System.err.println(errorMsg);
							}
						}
												
					}
				
					j = 3; // Use j = 4 When the antonyms are stored in the synset line itself, this should be 4 (Akshay's Method)			
				}
				
				// To handle Gradation.--akshay 
				else if ( aSynsetData[n].charAt(1) == '3' ) {
					PointerType pointertype = new PointerType(aSynsetData[n].substring(2));
					
					int index = Integer.parseInt(aSynsetData[n+1].substring(0,2));
					int targetIndex = Integer.parseInt(aSynsetData[n+1].substring(2,4));
					
					/* Subtracted 1 for getting correct WordIndices, have to verify correctness of this - Aditya*/ 
					//index--;
					//targetIndex--;
					
					int targetIndex1 = Integer.parseInt(aSynsetData[n+1].substring(4));
					long targetoffset = Long.parseLong(aSynsetData[n+2]);
					long targetoffset1 = Long.parseLong(aSynsetData[n+3]);
					try {	
						synsetPointers[i] = new Pointer(synsetWords[index],index,pointertype,targetPos,targetoffset,targetIndex, targetoffset1, targetIndex1);
					} catch(Exception ex) {
						System.out.println("lexical	-- gradation " + synsetId  + ex);
					}
					j = 4;			
				}
				//	To handle Ontology Nodes --akshay.
				else if ( aSynsetData[n].charAt(1) == '4' ) {
					PointerType pointertype = new PointerType(aSynsetData[n].substring(2));
					@SuppressWarnings("unused") String temp;

					long targetoffset = Long.parseLong(aSynsetData[n+1]);
					try {
						synsetPointers[i] = new Pointer(0, pointertype, targetoffset);
					} catch(Exception ex) {
						System.out.println("ontology	" + ex);
					}
					j = 2;			
				}

			}
		}
		else {
			return null;		
		}
		
		// Clean up pointerArr by removing nulls
		Vector<Pointer> pointerVector = new Vector<Pointer>();
		for (int i = 0; i < synsetPointers.length; i++) {
			if(synsetPointers[i]!=null) {
				pointerVector.add(synsetPointers[i]);
			}
		}		
		synsetPointers = pointerVector.toArray(new Pointer[pointerVector.size()]);
		
		aSynset.hasAntonyms(hasantonym);
		aSynset.setWords(synsetWords);
		aSynset.setPointers(synsetPointers);
		aSynset.setLexicalPointers(wordLexicalRelWordPairList);	
		
		return aSynset;
	}

	/**
	 * Constuctor for ontology relation.
	 */
	private Synset parseOntologySynset(long offset){
		String ontoTreeDescription = new String();
		ontoTreeDescription = "";
		String aSynsetEntry = "";
		String aPar_Cnt_Child = "";
		String ontologyOffsetInfo[] = new String[3];
		
		aSynsetEntry = mOntoHashmap.get(offset);
		if(aSynsetEntry != null){
			String aOffsetData = aSynsetEntry.substring(0, aSynsetEntry.indexOf('|'));
			ontologyOffsetInfo = aOffsetData.split(" +");
			String ontologyDesc = aSynsetEntry.substring(aSynsetEntry.indexOf('|')+1).trim();
			ontologyDesc = ontologyDesc.replaceAll("\\{", "(");
			ontologyDesc = ontologyDesc.replaceAll("\\}", ")");
			//ontologyDesc += "; ";
			ontoTreeDescription += ontologyDesc;
			long cnt = Long.parseLong(ontologyOffsetInfo[1]);
			if( cnt != 0 ){
				boolean isOntologyRootReached = false;
				int count = 0;
				while(!isOntologyRootReached){					
					count++;
					if(Long.parseLong(ontologyOffsetInfo[1]) != 0){ //check field 2 (count) is greater than 0
						String aOntoData = mOntoHashmap.get(Long.parseLong(ontologyOffsetInfo[2]));					

						aPar_Cnt_Child = aOntoData.substring(0, aOntoData.indexOf('|'));
						ontologyOffsetInfo = aPar_Cnt_Child.split(" +");
						if( (Long.parseLong(ontologyOffsetInfo[0]) == 1) && count >= 1 ){
							isOntologyRootReached = true;
						}
						ontologyDesc = aOntoData.substring(aOntoData.indexOf('|')+1).trim();
						ontologyDesc = ontologyDesc.replaceAll("\\{", "(");
						ontologyDesc = ontologyDesc.replaceAll("\\}", ")");
						ontoTreeDescription += "; " + ontologyDesc;
					}
				}
			}
		} else {
			return null;
		}
		return new Synset(offset, ontoTreeDescription);
		}

	@Override
	public String[] getAdjWordList() {
		if(adjWordList == null) {
			String[] wordList = mIdxAdjHashmap.keySet().toArray(new String[0]);
			Arrays.sort(wordList);
			adjWordList = wordList;
			return wordList;
		} else {
			return adjWordList;
		}
	}

	@Override
	public String[] getAdvWordList() {
		if(advWordList == null) {
			String[] wordList = mIdxAdvHashmap.keySet().toArray(new String[0]);
			Arrays.sort(wordList);
			advWordList = wordList;
			return wordList;
		}else {
			return advWordList;
		}
	}

	@Override
	public String[] getNounWordList() {
		if(nounWordList == null) {
			String[] wordList = mIdxNounHashmap.keySet().toArray(new String[0]);
			Arrays.sort(wordList);
			nounWordList = wordList;
			return wordList;	
		} else {
			return nounWordList;
		}
	}
	
	@Override
	public String[] getVerbWordList() {
		if(verbWordList == null ) {
		String[] wordList = mIdxVerbHashmap.keySet().toArray(new String[0]);
		Arrays.sort(wordList);
		verbWordList = wordList;
		return wordList;
		} else {
			return verbWordList;
		}		
	}
	
	protected void finalize() throws Throwable {
		
		try {
			if(enableLogging) {
				errorFile.close();
			}
		} catch (IOException e) {
			System.err.println("Error in closing Wordnet Error Log file.");
		} finally {
			super.finalize();
		}
	}
	
	private String[] nounWordList = null;
	private String[] verbWordList = null;
	private String[] adjWordList = null;
	private String[] advWordList = null;
}
