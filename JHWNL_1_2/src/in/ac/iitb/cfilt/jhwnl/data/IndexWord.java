/**
 * An IndexWord represents a line of the pos_txt file. An IndexWord is created or retrieved via lookupIndexWord.
 * Author Manish Sinha
 * IndexWord.java
 */


package in.ac.iitb.cfilt.jhwnl.data; 

/** * 
 * An IndexWord represents a line of the pos_txt file. An IndexWord is created or retrieved via lookupIndexWord.
 */

public class IndexWord implements DictionaryElement {

	private String mLemma;  
	private POS mPOS = null;
	private Synset[] mSynsets;
	private long[] mSynsetOffsets;
	
	/**
	 * Constructor to create an IndexWord.
	 * @param pPos Part of speech of string 
	 * @param pSynsets The synsets corresponding to this indexword */	
	public IndexWord(POS pPos, Synset[] pSynsets, String pLemma) {
		super();
		mPOS = pPos;
		mSynsets = pSynsets;
		mSynsetOffsets = new long[mSynsets.length];
		for (int i = 0; i < mSynsets.length; i++) {
			mSynsetOffsets[i] = mSynsets[i].getOffset();
		}
		this.mLemma = pLemma;
	}

	/**
	 * Get a key that can be used to index this element. Not Functioning yet.
	 */	
	public java.lang.Object getKey() {
		return null;
	}

	/**
	 * Get the word's sense count i.e number senses the word has.
	 * @return The number of of senses for the input string.
	 */	
	public int getSenseCount() {			
		return mSynsets.length;		
	}

	/**
	 * Get the word's part-of-speech.
	 * @return Part of Speech of the input word.
	 */	
	public POS getPOS() {              // 
		return mPOS;
	}

	/**
	 * Get Synset's offset
	 * @return Array of offsets of synsets of input string.
	 */	
	public long[] getSynsetOffsets() {           //
		return (mSynsetOffsets);
	}

	/**
	 * Get all the senses of the Synset.
	 * @return <code>Array of Synset for inut string. </code>
	 * @throws Exception
	 */
	public Synset[] getSenses(){
		return(mSynsets);
	}

	/**
	 * Overrides: equals in class java.lang.Object
	 */

	public boolean equals(Object object) {     //
		if (this.equals(object) ) {
			return(true);
		}
		else return(false);
	}

	/**
	 * Return the word's lemma. Its lemma is its orthographic representation.
	 * @return String that is input
	 */

	public String getLemma() { 
		//Return the word's lemma. Its lemma is its orthographic representation.
		return(mLemma);
	}

	/**
	 * Get a particular sense of this word. Sense indices start at 1.
	 * @param index Synset number
	 * @return <code> Synset </code> at index <code>index</code>
	 * @throws Exception
	 */

	public Synset getSense(int index) throws Exception {//Get a particular sense of this word. Sense indices start at 1.
		return(mSynsets[++index]);
	}

	/**
	 * Overrides: toString in class java.lang.Object
	 */

	public String toString() { //    Overrides: toString in class java.lang.Object
		return(mLemma);
	}

	/**
	 * @return the searchStringFound
	 */
	public boolean isSearchStringFound() {
		if(mSynsets != null) {
			return true;
		} else {
			return false;
		}		 
	}
}
