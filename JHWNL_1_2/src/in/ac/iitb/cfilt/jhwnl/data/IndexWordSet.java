/**
 * author Manish Sinha
 *
 * A class to simplify the access to a set of IndexWords, each containing one part of speech of the same word. IndexWordSets are usually created by a call to Dictionary.lookupAllIndexWords.
 *
 * IndexWordSet.java
 **/
package in.ac.iitb.cfilt.jhwnl.data;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * A class to simplify the access to a set of IndexWords, each containing one part of speech of the same word. IndexWordSets are usually created by a call to Dictionary.lookupAllIndexWords.
 * 
 */
public class IndexWordSet extends Object {
	/*private IndexWord[] giwa;
//	private IndexWord[] al;
	private ArrayList<IndexWord> al = new ArrayList<IndexWord>();
	private IndexWord iw_n;
	private IndexWord iw_ad;
	private IndexWord iw_v;
	private IndexWord iw_av;
	private IndexWord iw;
	private String l;
	private Set<IndexWord> set = new HashSet<IndexWord>();
	private Collection<IndexWord> collection = new HashSet<IndexWord>(); 
*/
	
	private Vector<IndexWord> _indexWords = new Vector<IndexWord>();
	String lemma;
	
	/**
	 * Default Constructor. 
	 */

	public IndexWordSet() {}
	
	/**
	 * Constructor with input string and Part-Of-Speech
	 * @param p <code>POS</code> Part-Of-Speech of string 
	 * @param lemma input string
	 * @throws JHWNLException 
	 */
	public IndexWordSet(POS p,String lemma) {
		IndexWord temp = null;
		try {
			switch(p.posvalue) {
			case 1: temp =Dictionary.getInstance().lookupIndexWord(POS.NOUN, lemma);
			break;
			case 2: temp = Dictionary.getInstance().lookupIndexWord(POS.ADJECTIVE, lemma);
			break;
			case 3: temp = Dictionary.getInstance().lookupIndexWord(POS.VERB, lemma);
			break;
			case 4: temp = Dictionary.getInstance().lookupIndexWord(POS.ADVERB, lemma);
			break;
			}
		} catch (JHWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(temp != null) {
			_indexWords.add(temp);
			this.lemma = lemma;
		}
	}

	/**
	 * Add an IndexWord to this set
	 * @param indexWord <code>IndexWord</code> to be added 
	 */
	public void add(IndexWord indexWord) {                           //Add an IndexWord to this set
		_indexWords.add(indexWord);
	}
	/**
	 * Get the number of IndexWords in this set.
	 * @return size of the IndexWordSet
	 */
	public int size() {                                         //Get the number of IndexWords in this set. 
		return(_indexWords.size());   
	} 

	/**
	 * Get an array of the IndexWords in this set.
	 * @return Array of IndexWords
	 */
	public IndexWord[] getIndexWordArray() {                    //Get an array of the IndexWords in this set. 
		return _indexWords.toArray(new IndexWord[_indexWords.size()]);
	}

	/**
	 * Find out how many senses the word with part-of-speech pos has.
	 * @param pos <code>POS</code> to be queried.
	 * @return Number of senses of lemma in each <code>POS</code>
	 */
	public int getSenseCount(POS pos) {  //Find out how many senses the word with part-of-speech pos has. 
		int count = 0;
		for (int i = 0; i < _indexWords.size(); i++) {
			IndexWord temp = _indexWords.get(i);
			if(temp.getPOS().equals(pos)) {
				count += temp.getSenseCount();
			}
		}
		return(count);
	}

	/**
	 * It is assumed that IndexWordSets will only be created by calling Dictionary.lookupAllIndexWords, so all IndexWordSets with the same lemma should be equal.
	 * Overrides: equals in class java.lang.Object
	 */
	public boolean equals(java.lang.Object object) {
//		It is assumed that IndexWordSets will only be created by calling Dictionary.lookupAllIndexWords, 
//		so all IndexWordSets with the same lemma should be equal.
//		Overrides: equals in class java.lang.Object
		if (this.equals(object)) {
			return(true);
		} else return(false);

	}
	/**
	 * Get the IndexWord associated with a particular <code>POS</code>.
	 * @param pos Part-Of-Speech to be queried.
	 * @return {@link IndexWord} for {@link POS}
	 */
	public IndexWord getIndexWord(POS pos) { //Get the IndexWord associated with p.
		for (int i = 0; i < _indexWords.size(); i++) {
			IndexWord temp = _indexWords.get(i);
			if(temp.getPOS().equals(pos)) {
				return temp;
			}
		}	
		return null;
	}

	public Collection getIndexWordCollection() { //Get a collection of the IndexWords in this set.
		return (Vector)_indexWords.clone();
	}

	/**
	 * Get the lemma for this IndexWordSet
	 * @return <code>lemma</code> Input string for this IndexWordSet. 
	 */
	public java.lang.String getLemma() {
		return(lemma);
	}

	/**
	 * Get a set of all the parts-of-speech for which there is an IndexWord in this set.
	 * @return Set of <code>POS</code>
	 */
	public Set getValidPOSSet() {
		//Get a set of all the parts-of-speech for which there is an IndexWord in this set.
		HashSet<POS> posTagSet = new HashSet<POS>();
		for (int i = 0; i < _indexWords.size(); i++) {
			posTagSet.add(_indexWords.get(i).getPOS());
		}
		return posTagSet;
	}

	/**
	 * Return true if there is a word with part-of-speech pos in this set.
	 * @param pos <code>POS</code> Part-Of-Speech
	 * @return true if there is a word with part-of-speech pos in this set, false otherwise.
	 */
	public boolean isValidPOS(POS pos) { //Return true if there is a word with part-of-speech pos in this set.
		for (int i = 0; i < _indexWords.size(); i++) {
			if(_indexWords.get(i).getPOS().equals(pos)) {
				return true;
			}
		}
		return false;
	}

	/*public java.lang.String toString() { //Overrides:toString in class java.lang.Object.

	 }*/

	/**
	 * Remove the IndexWord associated with p from this set.
	 */
	public void remove(POS pos) { //Remove the IndexWord associated with p from this set.
		for (int i = 0; i < _indexWords.size(); i++) {
			if(_indexWords.get(i).getPOS().equals(pos)) {
				_indexWords.remove(i);
			}
		}
	}
	
	/**
	 * Clear the IndexWords
	 *
	 */
	public void clear() { //clear the IndexWords 
		_indexWords.clear();
	}
}
