/** @author: Manish Sinha
////@department: Department of Computer Science and Engineering,
//////////////// Indian Institute of Technology Bombay, Mumbai, India
*/
package in.ac.iitb.cfilt.jhwnl.data;
import java.util.HashSet;



/**
 * Instances of this class enumerate the possible WordNet pointer types, and are used to label PointerTypes. Each  <br>
 * PointerType carries additional information: a human-readable label, an optional reflexive type that labels links  <br>
 * pointing the opposite direction, an encoding of parts-of-speech that it applies to, and a short string that  <br>
 * represents it in the dictionary files.
 *   
 *  Types of pointers are : 
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
 * 
 */

public final class PointerType extends java.lang.Object implements java.io.Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 511993783593239926L;
	HashSet<String> posSet = new HashSet<String>();
	private String type;
	private String pointertype;
	
	/**
	 * Constructor
	 * @param pt String representing the relation.
	 */
	public PointerType (String pt) {
		pointertype = pt;
		/*if (pt == "ANTONYM")     type = 00;                                                                  
  		if (pt == "ATTRIBUTE")   type = 01;
  		if (pt == "CAUSE")       type = 02;
  		if (pt == "DERIVED")     type = 03;
  		if (pt == "ENTAILED_BY") type = 04;
  		if (pt == "ENTAILMENT")  type = 05;
  		if (pt == "HYPERNYM")    type = 06;
  		if (pt == "HYPONYM")     type = 07;
  		//if (pt == "MEMBER_HOLONYM")  type = 08;
  //if (pt == "MEMBER_MERONYM")  type = 09;
  		if (pt == "PART_HOLONYM")    type = 10;
  		if (pt == "PART_MERONYM")    type = 11;
  		if (pt == "SEE_ALSO")        type = 12;
  		if (pt == "SIMILAR_TO")      type = 13;
		if (pt == "SUBSTANCE_HOLONYM")    type = 14;*/

		String pos; 
		switch(Integer.parseInt(pointertype)) {
			case 0: type = "ONTO_NODES";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 1: type = "NEAR_SYNSET"; 
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 2: type = "HYPERNYM";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				break;	
			case 3: type = "HYPONYM";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 4: type = "SIMILAR";
				pos = "ADJECTIVE";				
				posSet.add(pos);
				break;	
			case 5: type = "ATTRIBUTE";  
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 6: type = "FUNCTION_VERB";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 7: type = "ABILITY_VERB";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 8: type = "CAPABILITY_VERB";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 9: type = "COMPOUND"; 
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 10: type = "ALSO_SEE"; 
				pos = "ADJECTIVE";				
				posSet.add(pos);
				break;	
			case 11: type = "MODIFIES_NOUN";
				pos = "ADJECTIVE";				
				posSet.add(pos);
				break;	
			case 12: type = "DERIVED_FROM"; 
				pos = "VERB";				
				posSet.add(pos);
				break;	
			case 13: type = "MODIFIES_VERB";
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 14: type = "ENTAILMENT";
				pos = "VERB";				
				posSet.add(pos);
				break;	
			case 15: type = "TROPONYM";
				pos = "VERB";				
				posSet.add(pos);
				break;	
			case 16: type = "COMPOUNDING";
				pos = "VERB";				
				posSet.add(pos);
				break;	
			case 17: type = "CONJUNCTION"; 
				pos = "VERB";				
				posSet.add(pos);
				break;	
			case 18: type = "ANTO_ACTION";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 19: type = "ANTO_AMOUNT";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 20: type = "ANTO_DIRECTION";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 21: type = "ANTO_GENDER";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 22: type = "ANTO_PERSONALITY";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 23: type = "ANTO_PLACE";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 24: type = "ANTO_QUALITY";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 25: type = "ANTO_SIZE";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 26: type = "ANTO_STATE";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 27: type = "ANTO_TIME";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 28: type = "ANTO_COLOUR";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 29: type = "ANTO_MANNER";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 30: type = "GRAD_STATE";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 31: type = "GRAD_SIZE";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 32: type = "GRAD_LIGHT";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 33: type = "GRAD_GENDER";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 34: type = "GRAD_TEMPERATURE";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 35: type = "GRAD_COLOR";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 36: type = "GRAD_TIME";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 37: type = "GRAD_QUALITY";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 38: type = "GRAD_ACTION";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 39: type = "GRAD_MANNER";
				pos = "NOUN";				
				posSet.add(pos);
				pos = "ADJECTIVE";				
				posSet.add(pos);
				pos = "VERB";				
				posSet.add(pos);
				pos = "ADVERB";				
				posSet.add(pos);
				break;	
			case 40: type = "MERO_COMPONENT_OBJECT"; 
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 41: type = "MERO_MEMBER_COLLECTION";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 42: type = "MERO_STUFF_OBJECT";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 43: type = "MERO_FEATURE_ACTIVITY";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 44: type = "MERO_PLACE_AREA";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 45: type = "MERO_PHASE_STATE";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 46: type = "MERO_PORTION_MASS";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 47: type = "MERO_RESOURCE_PROCESS";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 48: type = "MERO_POSITION_AREA";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 49: type = "HOLO_COMPONENT_OBJECT"; 
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 50: type = "HOLO_MEMBER_COLLECTION";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 51: type = "HOLO_STUFF_OBJECT";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 52: type = "HOLO_FEATURE_ACTIVITY";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 53: type = "HOLO_PLACE_AREA";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 54: type = "HOLO_PHASE_STATE";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 55: type = "HOLO_PORTION_MASS";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 56: type = "HOLO_RESOURCE_PROCESS";
				pos = "NOUN";				
				posSet.add(pos);
				break;	
			case 57: type = "HOLO_POSITION_AREA";
				pos = "NOUN";				
				posSet.add(pos);
				break;
			case 58: type = "CAUSATIVE";
			pos = "VERB";				
			posSet.add(pos);
			break;
			default: type = null;	
		}
/* 		if (pt == "00")   type = "ONTO_NODES";
  		if (pt == "01")   type = "NEAR_SYNSET";
		if (pt == "02")     type = "HYPERNYM";                                                                
  		if (pt == "03")   type = "HYPONYM";
  		if (pt == "04")   type = "SIMILAR";
  		if (pt == "05")   type = "ATTRIBUTES";
  		if (pt == "06")   type = "FUNCTION_VERB";
  		if (pt == "07")   type = "ABILITY_VERB";
  		if (pt == "08")   type = "CAPABILITY_VERB";
  		if (pt == "09")   type = "COMPOUND";
  		if (pt == "10")   type = "ALSO_SEE";
  		if (pt == "11")   type = "MODIFIES_NOUN";
  		if (pt == "12")   type = "DERIVED_FROM";
  		if (pt == "13")   type = "MODIFIES_VERB";
  		if (pt == "14")   type = "ENTAILMENT";
  		if (pt == "15")   type = "TROPONYM";
  		if (pt == "16")   type = "COMPOUNDING";
  		if (pt == "17")   type = "CONJUNCTION";
  		if (pt == "18")   type = "ANTO_ACTION";
  		if (pt == "19")   type = "ANTO_AMOUNT";
  		if (pt == "20")   type = "ANTO_DIRECTION";
  		if (pt == "21")   type = "ANTO_GENDER";
  		if (pt == "22")   type = "ANTO_PERSONALITY";
  		if (pt == "23")   type = "ANTO_PLACE";
  		if (pt == "24")   type = "ANTO_QUALITY";
  		if (pt == "25")   type = "ANTO_SIZE";
  		if (pt == "26")   type = "ANTO_STATE";
  		if (pt == "27")   type = "ANTO_TIME";
  		if (pt == "28")   type = "ANTO_COLOUR";
  		if (pt == "29")   type = "ANTO_MANNER";
  		if (pt == "30")   type = "GRAD_STATE";
  		if (pt == "31")   type = "GRAD_SIZE";
  		if (pt == "32")   type = "GRAD_LIGHT";
  		if (pt == "33")   type = "GRAD_GENDER";
  		if (pt == "34")   type = "GRAD_TEMPERATURE";
  		if (pt == "35")   type = "GRAD_COLOR";
  		if (pt == "36")   type = "GRAD_TIME";
  		if (pt == "37")   type = "GRAD_QUALITY";
  		if (pt == "38")   type = "GRAD_ACTION";
  		if (pt == "39")   type = "GRAD_MANNER";
  		if (pt == "40")   type = "MERO_COMPONENT_OBJECT";
  		if (pt == "41")   type = "MERO_MEMBER_COLLECTION";
  		if (pt == "42")   type = "MERO_STUFF_OBJECT";
  		if (pt == "43")   type = "MERO_FEATURE_ACTIVITY";
  		if (pt == "44")   type = "MERO_PLACE_AREA";
  		if (pt == "45")   type = "MERO_PHASE_STATE";
  		if (pt == "46")   type = "MERO_PORTION_MASS";
  		if (pt == "47")   type = "MERO_RESOURCE_PROCESS";
  		if (pt == "48")   type = "MERO_POSITION_AREA";
  		if (pt == "49")   type = "HOLO_COMPONENT_OBJECT";
  		if (pt == "50")   type = "HOLO_MEMBER_COLLECTION";
  		if (pt == "51")   type = "HOLO_STUFF_OBJECT";
  		if (pt == "52")   type = "HOLO_FEATURE_ACTIVITY";
  		if (pt == "53")   type = "HOLO_PLACE_AREA";
  		if (pt == "54")   type = "HOLO_PHASE_STATE";
  		if (pt == "55")   type = "HOLO_PORTION_MASS";
  		if (pt == "56")   type = "HOLO_RESOURCE_PROCESS";
  		if (pt == "57")   type = "HOLO_POSITION_AREA";
*/
 	}

	public static final PointerType ONTO_NODES  = new PointerType("00");
	public static final PointerType NEAR_SYNSET = new PointerType("01");
	public static final PointerType HYPERNYM = new PointerType("02");
	public static final PointerType HYPONYM = new PointerType("03");
	public static final PointerType SIMILAR = new PointerType("04");
	public static final PointerType ATTRIBUTES = new PointerType("05");
	public static final PointerType FUNCTION_VERB = new PointerType("06");
	public static final PointerType ABILITY_VERB = new PointerType("07");
	public static final PointerType CAPABILITY_VERB = new PointerType("08");
	public static final PointerType COMPOUND = new PointerType("09");
	public static final PointerType ALSO_SEE = new PointerType("10");
	public static final PointerType MODIFIES_NOUN = new PointerType("11");
	public static final PointerType DERIVED_FROM = new PointerType("12");
	public static final PointerType MODIFIES_VERB = new PointerType("13");
	public static final PointerType ENTAILMENT = new PointerType("14");
	public static final PointerType TROPONYM = new PointerType("15");
	public static final PointerType COMPOUNDING = new PointerType("16");
	public static final PointerType CONJUNCTION = new PointerType("17");
	public static final PointerType ANTO_ACTION = new PointerType("18");
	public static final PointerType ANTO_AMOUNT = new PointerType("19");
	public static final PointerType ANTO_DIRECTION = new PointerType("20");
	public static final PointerType ANTO_GENDER = new PointerType("21");
	public static final PointerType ANTO_PERSONLITY = new PointerType("22");
	public static final PointerType ANTO_PLACE = new PointerType("23");
	public static final PointerType ANTO_QUALITY = new PointerType("24");
	public static final PointerType ANTO_SIZE = new PointerType("25");
	public static final PointerType ANTO_STATE = new PointerType("26");
	public static final PointerType ANTO_TIME = new PointerType("27");
	public static final PointerType ANTO_COLOUR = new PointerType("28");
	public static final PointerType ANTO_MANNER = new PointerType("29");
	public static final PointerType GRAD_STATE = new PointerType("30");
	public static final PointerType GRAD_SIZE = new PointerType("31");
	public static final PointerType GRAD_LIGHT = new PointerType("32");
	public static final PointerType GRAD_GENDER = new PointerType("33");
	public static final PointerType GRAD_TEMPERATURE = new PointerType("34");
	public static final PointerType GRAD_COLOR = new PointerType("35");
	public static final PointerType GRAD_TIME = new PointerType("36");
	public static final PointerType GRAD_QUALITY = new PointerType("37");
	public static final PointerType GRAD_ACTION = new PointerType("38");
	public static final PointerType GRAD_MANNER = new PointerType("39");
	public static final PointerType MERO_COMPONENT_OBJECT = new PointerType("40");
	public static final PointerType MERO_MEMBER_COLLECTION = new PointerType("41");
	public static final PointerType MERO_STUFF_OBJECT = new PointerType("42");
	public static final PointerType MERO_FEATURE_ACTIVITY = new PointerType("43");
	public static final PointerType MERO_PLACE_AREA = new PointerType("44");
	public static final PointerType MERO_PHASE_STATE = new PointerType("45");
	public static final PointerType MERO_PORTION_MASS = new PointerType("46");
	public static final PointerType MERO_RESOURCE_PROCESS = new PointerType("47");
	public static final PointerType MERO_POSITION_AREA = new PointerType("48");
	public static final PointerType HOLO_COMPONENT_OBJECT = new PointerType("49");
	public static final PointerType HOLO_MEMBER_COLLECTION = new PointerType("50");
	public static final PointerType HOLO_STUFF_OBJECT = new PointerType("51");
	public static final PointerType HOLO_FEATURE_ACTIVITY = new PointerType("52");
	public static final PointerType HOLO_PLACE_AREA = new PointerType("53");
	public static final PointerType HOLO_PHASE_STATE = new PointerType("54");
	public static final PointerType HOLO_PORTION_MASS = new PointerType("55");
	public static final PointerType HOLO_RESOURCE_PROCESS = new PointerType("56");
	public static final PointerType HOLO_POSITION_AREA = new PointerType("57");
	public static final PointerType CAUSATIVE = new PointerType("58");

/**
 * Overrides: toString in class java.lang.Object
 */
	public java.lang.String toString() { // Overrides: toString in class java.lang.Object
		return(type);
	}
	
	public static void initialize() {}

	/**
	 * Get the label for the PointerType.
	 * @return Name of the PointerType. 
	 */
	public java.lang.String getLabel() {
		return(type);
	}

	/**
	 * Returns true if type is symmetric to this pointer type.
	 * @param type  
	 * @return boolean telling whether the relation is symmetric or not.
	 */
	public static boolean isSymmetric(PointerType type) { //Returns true if type is symmetric to this pointer type.
		return(false);
	}

	public PointerType getSymmetricType() { //Returns the pointer type that is symmetric to this type.
		return(null);
	}

	public static PointerType getPointerTypeForKey(java.lang.String key) { 
	//Return the PointerType whose key matches key.
		PointerType PT = new PointerType(key);
		return(PT);
	}
	
	public boolean appliesTo(POS pos) { //Whether or not this PointerType can be associated with pos
		String tmp = pos.toString();
		
		if (posSet.contains(tmp)) return(true);
		else return(false);			
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PointerType other = (PointerType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
