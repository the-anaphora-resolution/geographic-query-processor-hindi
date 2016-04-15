package in.ac.iitb.cfilt.jhwnl.dictionary;

import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.configuration.WNProperties;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.POS;
import in.ac.iitb.cfilt.jhwnl.data.Synset;

import java.util.Iterator;

/**
 * CFILT LAB Department of Computer Science and Engineering Indian Institute of
 * Technology Bombay, Mumbai, India Author: Manish Sinha
 * 
 * Abstract representation of a WordNet dictionary.
 * 
 */

public abstract class Dictionary{

	private static Dictionary _dictionary = null;
	private MorphologicalProcessor _morphprocessor = null;
	
	/**
	 * Static method to return an instance of Dictionary. This instance should 
	 * be used to access the Wordnet Data. 
	 * 
	 * @return Dictionary object instance
	 */
	public static Dictionary getInstance() {
		
		if(!(_dictionary == null)){
			return _dictionary;
		}
		
		String dictType = WNProperties.getProperty("dictionary.type");
		if(dictType==null || dictType.equals("FileDictionary")){			
			_dictionary  = new FileDictionary();
			return _dictionary;
		} else if(dictType != null){
			try {
				_dictionary  = (Dictionary)Class.forName("in.ac.iitb.cfilt.jhwnl.dictionary." +dictType).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return _dictionary;			
		} else {
			return null;
		}		
	}
	
	/**
	 * Create a Dictionary. 
	 */
	protected Dictionary() {
		boolean useMorph = Boolean.parseBoolean(WNProperties.getProperty("use.morph"));
		if(useMorph){
			String morphProcessor = WNProperties.getProperty("morph.processor");
			try {
				_morphprocessor = (MorphologicalProcessor)Class.forName("in.ac.iitb.cfilt.jhwnl.dictionary.morph." +morphProcessor).getMethod("getInstance", (Class[])null).invoke((Object)null, (Object[])null);
			} catch (Exception e) {
				e.printStackTrace();
				_morphprocessor = null;
			}	
		}		
	}
		
	/**
	 * Whether to enable Morphological Processing or not
	 */
	public void enableMorphologicalProcessor(boolean option) {
		WNProperties.setProperty("use.morph", ""+option);
		WNProperties.store();
		if(option){
			String morphProcessor = WNProperties.getProperty("morph.processor");
			try {
				_morphprocessor = (MorphologicalProcessor)Class.forName("in.ac.iitb.cfilt.jhwnl.dictionary.morph." +morphProcessor).getMethod("getInstance", (Class[])null).invoke((Object)null, (Object[])null);
			} catch (Exception e) {
				System.err.println("Error initializing Morphological Processor: " + e);
				_morphprocessor = null;
			}	
		} else {
			_morphprocessor = null;
		}
	}
		

	/**
	 * Method 	: getMorphologicalProcessor
	 * Purpose	: Returns the Morphological Processor object to for getting stemmed root forms of a word.
	 * @return a reference to a Morphological Processor object
	 */
	public MorphologicalProcessor getMorphologicalProcessor() {
		return _morphprocessor;
	}
	
	/**
	 * Prepares the lemma for being used in a lookup operation.
	 * This method trims whitespace and converts the lemma
	 * to lower case.
	 * @param lemma the lemma to be prepared
	 * @return String the prepared lemma
	 */
	protected static String prepareQueryString(String lemma) {
		return lemma.trim().replaceAll(" ", "_");
	}

	/**
	 * This method implements the getIndexWord() method
	 */
	protected abstract IndexWord getSynsetsAsIndexWord(POS pos, String lemma) throws JHWNLException;

	
	/**
	 *  	Return an {@link IndexWord}, which can be used to access all the {@link Synset}s with the 
	 *  specified POS containing the lemma as a word.
	 */
	public IndexWord getIndexWord(POS pos, String lemma) throws JHWNLException{
		IndexWord word = getSynsetsAsIndexWord(pos, lemma);		
		// Handle the case of missing "-na" at the end of verbs
		if (word == null && (pos == null || pos == POS.VERB)) {	
			word = getSynsetsAsIndexWord(pos, lemma + "ना");
		}	
		return word;
	}
	
	/**
	 * Returns all {@link Synset} with the specified {@link POS} containing the root form of lemma as 
	 * a word. Morphed forms of words will be stemmed to obtain the root word before searching.
	 * @param pos the part-of-speech of the word to look up
	 * @param lemma the lemma to look up
	 * @return IndexWord the IndexWord found by the lookup procedure, or null
	 *				if an IndexWord is not found
	 */
	public IndexWord lookupIndexWord(POS pos, String lemma) throws JHWNLException {
		lemma = prepareQueryString(lemma);
		IndexWord word = getIndexWord(pos, lemma);
		if (word == null && getMorphologicalProcessor() != null) {
			word = getMorphologicalProcessor().lookupBaseForm(pos, lemma);
		}
		return word;
	}

	/**
     * Return a set of IndexWords, with each element in the set corresponding to all distinct {@link POS} in
     * which synsets are present for the lemma.
     * @param lemma the word for which to lookup senses
	 * @return An {@link IndexWordSet}, containing many {@link IndexWord}s each of which is a sense of <var>lemma</var>
	 */
	public IndexWordSet lookupAllIndexWords(String lemma) throws JHWNLException {
		lemma = prepareQueryString(lemma);
		IndexWordSet set = new IndexWordSet();//lemma);
		for (Iterator itr = POS.getAllPOS().iterator(); itr.hasNext();) {
			IndexWord current = lookupIndexWord((POS)itr.next(), lemma);
			if (current != null) set.add(current);
		}
		return set;
	}
	
	/**
	 * Return a set of IndexWords, with each element in the set corresponding to all distinct root forms of the <var>lemma</var> 
	 * found after Morphological Processing.
	 * @param pos the part-of-speech of the word to look up
	 * @param lemma the lemma to look up
	 * @return IndexWord the IndexWord found by the lookup procedure, or null
	 *				if an IndexWord is not found
	 */
	public IndexWordSet lookupMorphedIndexWords(POS pos, String lemma) throws JHWNLException {
		lemma = prepareQueryString(lemma);
		IndexWordSet indexWordSet = new IndexWordSet();
		IndexWord word = getIndexWord(pos, lemma);
		if(word != null) {
			indexWordSet.add(word);
		}
		if (word == null && getMorphologicalProcessor() != null) {
			MorphologicalProcessor morpher = getMorphologicalProcessor();
			for (Iterator baseformItr = morpher.lookupAllBaseForms(pos, lemma).iterator(); baseformItr.hasNext();){
				String thisLemma = (String)baseformItr.next();  
				IndexWord current = getIndexWord(pos, thisLemma);
				if (current != null){ 
					indexWordSet.add(current);
				}
			}		
		}
		return indexWordSet;		
	}

	/**
	 * Returns a set of IndexWord for all root forms of the lemma for all available POS in which {@link Synset}s 
	 * are present for the <var>lemma</var>.
	 * @param lemma the word for which to lookup senses
	 * @return An array of IndexWords, each of which is a sense of <var>word</var>
	 */
	public IndexWordSet lookupAllMorphedIndexWords(String lemma) throws JHWNLException {
		lemma = prepareQueryString(lemma);
		IndexWordSet indexWordSet = new IndexWordSet();
		if (getMorphologicalProcessor() != null) {
			MorphologicalProcessor morpher = getMorphologicalProcessor();
			for (Iterator itr = POS.getAllPOS().iterator(); itr.hasNext();) {
				POS thisPOS = (POS)itr.next();
				for (Iterator baseformItr = morpher.lookupAllBaseForms(thisPOS, lemma).iterator(); baseformItr.hasNext();){
					IndexWord current = getIndexWord(thisPOS, (String)baseformItr.next());
					if (current != null){ 
						indexWordSet.add(current);
					}
				}				
			}		
		}
		return indexWordSet;
	}
		
	/**
	 * Return the Synset with the given Synset-Id and Part of Speech.
	 */
	public abstract Synset getSynsetAt(POS pos, long synsetId) throws JHWNLException;
	
	/**
	 * Return the Synset with the given Synset-Id.
	 */	
	public abstract Synset getOntoSynset(long synsetId)throws JHWNLException;
	
	/**
	 * Method 	: nounWordList
	 * Purpose	: Returns array of all the Nouns in the Wordnet
	 * @return Sorted array of all nouns
	 */
	public abstract String[] getNounWordList();
	
	/**
	 * Method 	: verbWordList
	 * Purpose	: Returns array of all the Verbs in the Wordnet
	 * @return Sorted array of all verbs
	 */
	public abstract String[] getVerbWordList();
	
	/**
	 * Method 	: adjWordList
	 * Purpose	: Returns array of all the Adjectives in the Wordnet
	 * @return Sorted array of all adjctives
	 */
	public abstract String[] getAdjWordList();
	
	/**
	 * Method 	: advWordList
	 * Purpose	: Returns array of all the Adverbs in the Wordnet
	 * @return Sorted array of all adverbs
	 */
	public abstract String[] getAdvWordList();
}