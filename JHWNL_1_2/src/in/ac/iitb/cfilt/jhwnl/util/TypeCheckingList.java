/**
@author: Manish Sinha
@department of Computer Science and Engineering
Indian Institute of Technology Bombay, Mumbai, India
*/

/*Wrapper for a list that checks the type of arguments before putting them in the list. It also does type-checking on methods which iterate over the list so that they fail fast if the argument is not of the correct type.*/

package in.ac.iitb.cfilt.jhwnl.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class TypeCheckingList implements List, DeepCloneable {
	Class Type;
	Class parenttype;
	List backinglist;
	LinkedList<Object> linkedlist = new LinkedList<Object>();

	public TypeCheckingList(Class type) {
		Type = type;
	}

	public TypeCheckingList(List backingList,Class type) {
		backinglist = backingList;
		Type = type;
	}

	public TypeCheckingList(List backingList,Class type,Class parentType) {
		backinglist = backingList;
		Type = type;
		parenttype = parentType;
	}

	public Class getType() {
		return(Type);
	}

	public Object clone() throws CloneNotSupportedException {
	//Description copied from interface: DeepCloneable    Create a shallow clone of the object
	//Specified by:clone in interface DeepCloneable
	//Overrides:clone in class Object CloneNotSupportedException
		TypeCheckingList shallowcopy = (TypeCheckingList)this.clone();
                return(shallowcopy);	
	}

	public Object deepClone() throws UnsupportedOperationException {
	//public Object deepClone() throws UnsupportedOperationException
	                try {
                        	ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
                        	ObjectOutputStream objOut = new ObjectOutputStream(byteArr);
                        	objOut.writeObject(this);
                        	ByteArrayInputStream byteArrIn = new ByteArrayInputStream(byteArr.toByteArray());
                        	ObjectInputStream objIn = new ObjectInputStream(byteArrIn);
                        	return(objIn.readObject());
                	} catch (Exception ex) {
                        	return(null);
                	}

	}

	public boolean add(Object o) { //     Specified by:  add in interface java.util.List
		if (linkedlist.add(o)) {
			return(true);
		} 
		return(false);
	}
	
	public void add(int index,Object o) { // Specified by: add in interface java.util.List
			linkedlist.add(index,o);
	}

	public boolean addAll(java.util.Collection c) { // Specified by: addAll in interface java.util.List
		if (linkedlist.addAll(c)) { 
			return (true);
		}
		return(false);
	}

	public boolean addAll(int index,java.util.Collection c) { //    Specified by: addAll in interface java.util.List
		if (linkedlist.addAll(index,c)) {
			return (true);
		}
		return(false); 
	}

	public boolean contains(Object o) { // Specified by: contains in interface java.util.List
		if (linkedlist.contains(o)) {
			return(true);
		}
		return(false);
	} 

	public boolean containsAll(java.util.Collection c) { //  Specified by: containsAll in interface java.util.List
		if (linkedlist.containsAll(c)) { 
			return(true);
		}
		return(false);
	}

	public Object set(int index,Object element) { // Specified by: set in interface java.util.List
		return(linkedlist.set(index,element));
	}

	public int indexOf(Object o) { //    Specified by: indexOf in interface java.util.List
		return(linkedlist.indexOf(o));
	}

	public int lastIndexOf(Object o) { //  Specified by: lastIndexOf in interface java.util.List
		return(linkedlist.lastIndexOf(o));
	}

	public boolean remove(Object o) { //    Specified by: remove in interface java.util.List
		return(linkedlist.remove(o));
	}

	public java.util.ListIterator listIterator() { // Specified by: listIterator in interface java.util.List
		return(linkedlist.listIterator());
	}

	public java.util.ListIterator listIterator(int index) { // Specified by:listIterator in interface java.util.List
		return(linkedlist.listIterator(index));
	}

	protected TypeCheckingList.TypeCheckingListIterator getTypeCheckingListIterator() {
		TypeCheckingList.TypeCheckingListIterator tcli = new TypeCheckingListIterator();
		return(tcli);
	}

	/*protected TypeCheckingList.TypeCheckingListIterator getTypeCheckingListIterator(int index) {
	        TypeCheckingList.TypeCheckingListIterator tcli = new TypeCheckingListIterator(index);
                return(tcli);	
	}*/

	public int size() { // Specified by: size in interface java.util.List
		return(linkedlist.size());
	}

	public boolean isEmpty() { // Specified by:  isEmpty in interface java.util.List
		if (linkedlist.size() == 0) {
			return(true);
		}
		return(false);
	}

	public java.util.Iterator iterator() { // Specified by: iterator in interface java.util.List
		return(linkedlist.listIterator());
	}
	
	public Object[] toArray() { // Specified by: toArray in interface java.util.List
		return(linkedlist.toArray());
	}

	public Object[] toArray(Object[] a) { // Specified by: toArray in interface java.util.List
		return(linkedlist.toArray(a));
	}

	public boolean removeAll(java.util.Collection c) throws UnsupportedOperationException, NullPointerException { 
	// Specified by: removeAll in interface java.util.List
		return(linkedlist.removeAll(c));	
	}

	public boolean retainAll(java.util.Collection c) { // Specified by: retainAll in interface java.util.List
		return(linkedlist.retainAll(c));
	}

	public void clear() { // Specified by: clear in interface java.util.List
		linkedlist.clear();
	}

	public Object get(int index) { // Specified by: get in interface java.util.List
		return(linkedlist.get(index));		
	}

	public Object remove(int index) { //  Specified by: remove in interface java.util.List
		return(linkedlist.remove(index));
	}

	public java.util.List subList(int fromIndex,int toIndex) { // Specified by: subList in interface java.util.List
		return(linkedlist.subList(fromIndex,toIndex));	
	}


public final class TypeCheckingListIterator extends Object implements java.util.ListIterator {
java.util.ListIterator listiterator = linkedlist.listIterator();
        public void add(Object o) { //    Specified by:  add in interface java.util.ListIterator
                listiterator.add(o);
        }

        public Class getType() { //
                return(Type);
        }

        public boolean hasNext() { //    Specified by: hasNext in interface java.util.ListIterator
                return(listiterator.hasNext());
        }

        public boolean hasPrevious() { //     Specified by:   hasPrevious in interface java.util.ListIterator
                return(listiterator.hasPrevious());
        }

        public Object next() {    //Specified by:        next in interface java.util.ListIterator
                return(listiterator.next());
        }

        public int nextIndex() { //    Specified by:  nextIndex in interface java.util.ListIterator
                return(listiterator.nextIndex());
        }

        public Object previous() { //    Specified by:  previous in interface java.util.ListIterator
                return(listiterator.previous());
        }

        public int previousIndex() { //    Specified by:    previousIndex in interface java.util.ListIterator
                return(listiterator.previousIndex());
        }

        public void remove() { //     Specified by:     remove in interface java.util.ListIterator
                listiterator.remove();
        }

        public void set(Object o) { //    Specified by:     set in interface java.util.ListIterator
                listiterator.set(o);
        }

}

}
