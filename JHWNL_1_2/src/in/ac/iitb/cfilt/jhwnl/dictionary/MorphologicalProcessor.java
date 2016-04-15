package in.ac.iitb.cfilt.jhwnl.dictionary;

import java.util.List;

import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.POS;

/**
 * Class	: MorphologicalProcessor
 * Purpose	: This class is used for obtaining the base form of an inflected word.
 */
public interface MorphologicalProcessor {
	/**
	 * Try to obtain a root base form for the <code>derivation</code>. If there is more than one possible
	 * root form, then the first call to this method should return the first base form found. The latter
	 * method call behaviour is undefined, either the first root form or subsequent root forms can be returned. 
	 */
	public IndexWord lookupBaseForm(POS pos, String derivation) throws JHWNLException;

    /** Return all the base forms of <var>derivation</var> in a List of <code>String</code>s */
    public List lookupAllBaseForms(POS pos, String derivation) throws JHWNLException;
}
