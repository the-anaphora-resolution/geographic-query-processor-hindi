// CFILT LAB
// Author: Manish Sinha
// Instances of this class enumerate the possible major syntactic categories, or Part's Of Speech.
// **************************************************************************************************************

package in.ac.iitb.cfilt.jhwnl.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 *Part-Of-Speech class representing four parts of speech. 
 *
 */
public final class POS extends java.lang.Object implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2840722001649859826L;

	public int posvalue;

	public POS(int value) {
		posvalue = value;
	}

	public static final POS NOUN = new POS(1);

	public static final POS ADJECTIVE = new POS(2);

	public static final POS VERB = new POS(3);

	public static final POS ADVERB = new POS(4);

	private static final List ALL_POS =
	    Collections.unmodifiableList(Arrays.asList(new POS[] {NOUN, VERB, ADJECTIVE, ADVERB}));

	/**
	 * String representation of the Object.
	 */
	public String toString() {
		switch (this.posvalue) {
		case 1:
			return ("NOUN");
		case 2:
			return ("ADJECTIVE");
		case 3:
			return ("VERB");
		case 4:
			return ("ADVERB");
		default:
			return (null);
		}
	}

	public static List getAllPOS() {
		return ALL_POS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + posvalue;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final POS other = (POS) obj;
		if (posvalue != other.posvalue)
			return false;
		return true;
	}
	
}
