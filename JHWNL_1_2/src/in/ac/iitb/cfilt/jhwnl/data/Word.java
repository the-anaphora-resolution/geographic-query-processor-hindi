package in.ac.iitb.cfilt.jhwnl.data;

/*A Word represents the lexical information related to a specific sense of an IndexWord. Word's are linked by Pointers into a network of lexically related words. getTargets retrieves the targets of these links, and getPointers retrieves the pointers themselves.*/
/**
 * A Word represents the lexical information related to a specific sense of an IndexWord. Word's are linked by Pointers into a network of lexically related words. getTargets retrieves the targets of these links, and getPointers retrieves the pointers themselves.
 *
 */
public class Word extends PointerTarget {
	/**
	 * This field stores
	 */
	private static final long serialVersionUID = -9126865080014697798L;
	private java.lang.String lemma;
	private int index;
	private Synset synset; 
	
	/**
	 * Constructor
	 * @param pSynset 
	 * @param pIndex
	 * @param pLemma
	 */
	public Word (Synset pSynset, int pIndex, String pLemma) {
		lemma = pLemma;
		index = pIndex;
		synset = pSynset;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((lemma == null) ? 0 : lemma.hashCode());
		result = prime * result + ((synset == null) ? 0 : (new Long(synset.getOffset())).hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Word))
			return false;
		final Word other = (Word) obj;
		if (index != other.index)
			return false;
		if (lemma == null) {
			if (other.lemma != null)
				return false;
		} else if (!lemma.equals(other.lemma))
			return false;
		if (synset == null) {
			if (other.synset != null)
				return false;
		} else if (!(synset.getOffset() == other.synset.getOffset()))
			return false;
		return true;
	}

	public int getIndex() {
		return(index);
	} 
/**
 * Get the String corresponding to the word object.
 * @return String representation of the word.
 */
	public java.lang.String getLemma() {
		return(lemma);
	}
/**
 * Get <code>Synset</code> corresponding to this word.
 * @return <code>Synset</code> containing this word.
 */
	public Synset getSynset() {
		return(synset);
	}
/**
 * Get the POS of the word.
 */
	public POS getPOS() {
		return(synset.getPOS());
 	}
/**
 * 
 */
	public java.lang.String toString() {
		return(lemma);
	}
/**
 * Returns all the pointers of the synset that contains this word whose source is this word.
 */
	public Pointer[] getPointers() { 
				//returns all the pointers of the synset that contains this word whose source is this word
		return(synset.getPointers());     // Specified by: getPointers in class PointerTarget
	}
	/**
	 * Return the offset for this word.
	 */
	public long getOffset() {
		return(synset.getOffset());
	}

}
