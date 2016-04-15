/**
 Author: Manish Sinha and Aditya Sharma
 Department of Computer Science and Engineering
 Indian Institute of Technology Bombay, Mumbai, India
 */
package in.ac.iitb.cfilt.jhwnl.data;
import java.util.Arrays;
import java.util.Vector;

/**
 * A Synset, or synonym set, represents a line of a WordNet pos.data file. A Synset represents a concept, and contains a set of Words, each of which has a sense that names that concept (and each of which is therefore synonymous with the other words in the Synset).
 * Synsets are linked by Pointers into a network of related concepts; this is the Net in WordNet. getTargets retrieves the targets of these links, and getPointers retrieves the pointers themselves. 
 */


public class Synset extends PointerTarget implements DictionaryElement {
	
	private static final long serialVersionUID = 601824564751L;
	private boolean hasantonym;
	private String gloss;
	private long synsetId;
	private Word[] mSynsetWords;
	private Pointer[] synsetPointers;
	private POS mPOS;
	private String ontoTreeDescription;
	private Vector<String>  wordLexicalRelWordPairList;
	
	public Synset(boolean hasantonym, String gloss, long synsetId, Word[] synsetWords, Pointer[] synsetPointers, POS mpos, Vector<String> wordLexicalRelWordPairList) {
		super();
		this.hasantonym = hasantonym;
		this.gloss = gloss;
		this.synsetId = synsetId;
		this.mSynsetWords = synsetWords;
		this.synsetPointers = synsetPointers;
		this.mPOS = mpos;
		this.wordLexicalRelWordPairList = wordLexicalRelWordPairList;
	}

	public Synset(){
	
	}
	
	public Synset(long pSynsetId, String ontoTreeDescription) {
		super();
		this.gloss = null;
		this.synsetId = pSynsetId;
		this.mSynsetWords = null;
		this.synsetPointers = null;
		this.mPOS = null;
		this.ontoTreeDescription = ontoTreeDescription;
		this.wordLexicalRelWordPairList = null;
	}

	/**
	 * Two Synsets are equal if their POS's and offsets are equal.
	 */
	

	public boolean hasAntonym(){		
		return hasantonym;
	}

	/**
	 * Get a key that can be used to index this element.
	 */
	public java.lang.Object getKey() {    //    Get a key that can be used to index this element.
		return null;
	} 

	/**
	 * Get the gloss for this synset.
	 * @return Gloss as a string.
	 */
	public java.lang.String getGloss() {
		if(gloss != null) {
			return(gloss);
		} else { // Probably an Ontology synset 
			return ontoTreeDescription;	
		}
	}	

	/**
	 * Get the offset of this synset.
	 */
	public long getOffset() {
		return(synsetId);
	}

	/**
	 * Get the words in this synset.
	 * @return Array of Word object for each word in the synset.
	 */
	public Word[] getWords() {
		return(mSynsetWords);
	}
	/**	  
	 * Return an array of pointers for this Synset, which can be used to access all relations of the synset. A specific 
	 * type of relation can be selected by comparing them with the static members of the PointerTarget Class.
	 */
	public Pointer[] getPointers() { 
		return(synsetPointers); 
	}

	/**
	 * Returns true if lemma is one of the words contained in this synset.
	 * @param lemma word to be queried.
	 * @return true if the lemma is present in the synset, false otherwise.
	 */
	public boolean containsWord(String lemma) { 
		//returns true if lemma is one of the words contained in this synset.
		for (int i = 0; i < mSynsetWords.length; i++) {
			if(lemma.equals(mSynsetWords[i].getLemma())) {
				return true;
			}
		} 		
		return false;		
	}

	/**
	 * Return this synset's POS
	 */
	public POS getPOS() { 			//Description copied from class: PointerTarget Return this target's POS
		return mPOS;  		//Return this target's POS
	}					//Specified by:getPOS in class PointerTarget

	/**
	 * Get individual word in this synset.
	 * @param index index of the word queried.
	 * @return <code>Word</code> object at <code>index</code>
	 */
	public Word getWord(int index) {
//		System.out.println("Idx "+index);
		if(index > 0) {
			return(mSynsetWords[--index]);
		}
		else {
			return null;//(mSynsetWords[index]);
		}
	}

	/**
	 * String representation of this Synset object.
	 */
	public String toString() {    
		StringBuilder result = new StringBuilder();
		result.append(synsetId);
		result.append(" - ");
		result.append(mPOS);
		result.append(" - [");

		if(! (mSynsetWords == null)) {
			int numWords = mSynsetWords.length;

			for (int i = 0; i < numWords - 1; i++) {
				result.append(mSynsetWords[i].getLemma()+", ");
			}
			if (numWords > 0){
				result.append(mSynsetWords[numWords - 1]);
			}
			result.append("]");
		} else { // for ontology synset
			result.append(ontoTreeDescription);
		}
		return(result.toString());
	}

	/**
	 * Get the number of words in the synset.
	 * @return count of the words in the synset.
	 */
	public int getWordsSize() {
		return(mSynsetWords.length);
	}

	/**
	 * Get the ontology nodes hierarchy starting from parent till TOP node.
	 * @return String of parents of the synset in the ontology till the TOP node, seperated by semicolon.
	 */
	public String getOntoNodes(){
		return ontoTreeDescription;
	}

	public String getWordPairs(){
		String pairs = "";
		for(int i=0; i < wordLexicalRelWordPairList.size(); i++)
			pairs += wordLexicalRelWordPairList.get(i) + "; ";
		return pairs;
	}

	/**
	 * Return the no of relations of this particular synset
	 * @return count of the relations with which this synset is linked.
	 */
	public int getRelations() { // return the no of relations of this particular synset
		return synsetPointers.length;
	}// this is presently temperory

	public void hasAntonyms(boolean hasantonym2) {
		this.hasantonym = hasantonym2;
	}

	public void setWords(Word[] pSynsetWords) {
		this.mSynsetWords = pSynsetWords;
	}

	public void setLexicalPointers(Vector<String> pWordLexicalRelWordPairList) {
		this.wordLexicalRelWordPairList = pWordLexicalRelWordPairList; 
	}

	public void setPointers(Pointer[] pPointerArr) {
		synsetPointers = pPointerArr;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((gloss == null) ? 0 : gloss.hashCode());
		result = PRIME * result + ((mPOS == null) ? 0 : mPOS.hashCode());
		result = PRIME * result + Arrays.hashCode(mSynsetWords);
		result = PRIME * result + ((ontoTreeDescription == null) ? 0 : ontoTreeDescription.hashCode());
		result = PRIME * result + (int) (synsetId ^ (synsetId >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final Synset other = (Synset) obj;
		if (gloss == null) {
			if (other.gloss != null)
				return false;
		} else if (!gloss.equals(other.gloss))
			return false;
		if (mPOS == null) {
			if (other.mPOS != null)
				return false;
		} else if (!mPOS.equals(other.mPOS))
			return false;
		if (!Arrays.equals(mSynsetWords, other.mSynsetWords))
			return false;
		if (ontoTreeDescription == null) {
			if (other.ontoTreeDescription != null)
				return false;
		} else if (!ontoTreeDescription.equals(other.ontoTreeDescription))
			return false;
		if (synsetId != other.synsetId)
			return false;
		return true;
	}
}
