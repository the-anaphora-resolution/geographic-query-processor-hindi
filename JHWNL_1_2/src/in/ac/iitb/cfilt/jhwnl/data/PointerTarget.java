package in.ac.iitb.cfilt.jhwnl.data;

import java.io.Serializable;
import java.util.Vector;

/*A PointerTarget is the source or target of a Pointer. The target of a semantic PointerTarget is a Synset; the target of a lexical PointerTarget is a Word.*/


/**
 *  A PointerTarget is the source or target of a Pointer. The target of a semantic PointerTarget is
 * a Synset; the target of a lexical PointerTarget is a Word.
 *
 * Types of pointerd are : 
 * ONTO_NODES, NEAR_SYNSET, HYPERNYM, HYPONYM, SIMILAR, ATTRIBUTES, FUNCTION_VERB, ABILITY_VERB, 
 * CAPABILITY_VERB, COMPOUND, ALSO_SEE, MODIFIES_NOUN, DERIVED_FROM, MODIFIES_VERB, ENTAILMENT,  
 * TROPONYM, COMPOUNDING, CONJUNCTION, ANTO_ACTION, ANTO_AMOUNT, ANTO_DIRECTION, ANTO_GENDER, 
 * ANTO_PERSONLITY, ANTO_PLACE, ANTO_QUALITY, ANTO_SIZE, ANTO_STATE, ANTO_TIME, ANTO_COLOUR,  
 * ANTO_MANNER, GRAD_STATE, GRAD_SIZE, GRAD_LIGHT, GRAD_GENDER, GRAD_TEMPERATURE, GRAD_COLOR, 
 * GRAD_TIME, GRAD_QUALITY, GRAD_ACTION, GRAD_MANNER, MERO_COMPONENT_OBJECT, MERO_MEMBER_COLLECTION,  
 * MERO_STUFF_OBJECT, MERO_FEATURE_ACTIVITY, MERO_PLACE_AREA, MERO_PHASE_STATE, MERO_PORTION_MASS, 
 * MERO_RESOURCE_PROCESS, MERO_POSITION_AREA, HOLO_COMPONENT_OBJECT, HOLO_MEMBER_COLLECTION, 
 * HOLO_STUFF_OBJECT, HOLO_FEATURE_ACTIVITY, HOLO_PLACE_AREA, HOLO_PHASE_STATE, HOLO_PORTION_MASS, 
 * HOLO_RESOURCE_PROCESS, HOLO_POSITION_AREA, CAUSATIVE 
*/
public abstract class PointerTarget implements Serializable {
	protected PointerTarget pointerTargetsArr[];
	protected PointerTarget() {}

	/**
	 * Get all pointers of the specified type.
	 * @param type
	 * @return Array of all pointers of specified type.
	 */
	public Pointer[] getPointers(PointerType type) {
		Vector<Pointer> pointerList = new Vector<Pointer>();
		Pointer[] pointers = getPointers();
		for (int i = 0; i < pointers.length; i++) {
			if(pointers[i].getType().equals(type)) {
				pointerList.add(pointers[i]);
			}
		} 
		return pointerList.toArray(new Pointer[0]);
	}
	//public abstract POS getPOS();

	/**
	 * Overrides: equals in class java.lang.Object
	 */
	public boolean equals(java.lang.Object obj) {	//Overrides: equals in class java.lang.Object
		if (this.equals(obj)) {
			return(true);
		} else return(false);
	}
	
	/**
	 * Returns a list of Target's pointers
	 */
	public abstract Pointer[] getPointers(); //Returns a list of Target's pointers
	
	/**
	 * Overrides: toString in class java.lang.Object
	 */
	public abstract java.lang.String toString();
	
	/**
	 * Return this target's POS
	 */
	public abstract POS getPOS(); //Return this target's POS */
	//Get all the pointer targets of this synset
	
	/**
	 * Gets the target of this pointer, generally a Synset for semantic relations, and a Word for lexical relations 
	 */
	public PointerTarget[] getTargets() throws Exception { //Get all the pointer targets of this synset
		return(pointerTargetsArr);	
	}

	/* TODO : Need to test this method for correctness
	 * Get all the targets of the pointers of which belong to the specified type.
	 * @param PointerTarget[]
	 * @return return the target of this pointer
	 * @throws Exception
	 *//* 
	public PointerTarget[] getTargets(PointerType type) throws Exception { 
		LinkedHashSet<PointerTarget> specificPointers = new LinkedHashSet<PointerTarget>();
		for (int i = 0; i < pointerTargetsArr.length; i++) {
			Pointer[] thisPointers = pointerTargetsArr[i].getPointers();
			for (int j = 0; j < thisPointers.length; j++) {
				if(thisPointers[j].getType().equals(type) ) {
					specificPointers.addAll(Arrays.asList(pointerTargetsArr[i].getTargets()));
					break; // Continue with next PointerTarget
				}
			} 			
		}
		return(specificPointers.toArray(new PointerTarget[specificPointers.size()]));
	}
	  
	  */	
	public long getOffset() { 
	return(0);
	}
}
