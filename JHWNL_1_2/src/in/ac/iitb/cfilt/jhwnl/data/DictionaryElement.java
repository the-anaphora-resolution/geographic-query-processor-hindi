/**
@author: Manish Sinha
@department: Computer Science and Engineering, Indian Institute of Technology Bombay, Mumbai, India
*/
//Any class that represents an element contained in the dictionary (IndexWords, Synsets, and Exceptions) must implement this //interface.
package in.ac.iitb.cfilt.jhwnl.data;
/**
 * Any class that represents an element contained in the dictionary (IndexWords, Synsets, and Exceptions) must implement this //interface.
 * 
 */
public interface DictionaryElement extends java.io.Serializable {  //Interface

public java.lang.Object getKey();                                  //Get a key that can be used to index this element.

//public long getOffset();
}
