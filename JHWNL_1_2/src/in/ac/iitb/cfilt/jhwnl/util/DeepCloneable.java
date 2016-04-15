/**
@author: Manish Sinha
@department: Computer Science and Engineering Department
Indian Institute of Technology Bombay, Mumbai, India
*/

/*A DeepCloneable is a cloneable object that can be cloned shallowly (by creating a copy of the object that contains references to the same members as the original) or deeply (by creating a copy of the object and of all it's member objects).*/
package in.ac.iitb.cfilt.jhwnl.util;
public interface DeepCloneable extends java.lang.Cloneable {

	//Create a shallow clone of the object
	public java.lang.Object clone() throws java.lang.CloneNotSupportedException;
	//Create a deep clone of the object
	public java.lang.Object deepClone() throws java.lang.UnsupportedOperationException;

}
