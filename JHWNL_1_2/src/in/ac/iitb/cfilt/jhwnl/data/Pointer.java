/**
Author: Manish Sinha
Department of Computer Science and Engineering
Indian Institute of Technology Bombay, Mumbai, India
*/

package in.ac.iitb.cfilt.jhwnl.data;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;

import java.util.Vector;
/**
 * A Pointer encodes a lexical or semantic relationship between WordNet entities. A lexical relationship holds 
 * between Words; a semantic relationship holds between Synsets. Relationships are directional: the two roles 
 * of a relationship are the source and target. Relationships are typed: the type of a relationship is a 
 * PointerType, and can be retrieved via getType. 
 */
public class Pointer implements java.io.Serializable {


	private static final long serialVersionUID = -4762855825854253312L;
	private int sourceIndex;
	private int targetIndex;
	private int targetIndexGradation;// for gradation -- akshay
	private PointerType type;
	private PointerTarget source;
	//private PointerTarget Target;
	//private PointerTarget Target1;// for gradation -- akshay
	private long targetOffset;
	private long targetOffsetGradation;// for gradation -- akshay
	private POS targetPos;
	private long ontopointer; 
	private long sourceOffset;
	private static Vector<String> tree = new Vector<String>();
	/**
	 * Constructor for relations other than gradation and ontology nodes.
	 * @param source Source synset of type <code>PointerTarget</code>
	 * @param index index of the source synset.
	 * @param pointerType <code>PointerType</code> is the type of relation.
	 * @param targetPOS <code>POS</code> of the target synset.
	 * @param targetOffset offset of the word in the data_txt file
	 * @param targetIndex index of the target word
	 */
	public Pointer(PointerTarget source,int index,PointerType pointerType,POS targetPOS,long targetOffset,int targetIndex, long srcOffset) {
		try{
			sourceIndex = index;
			type = pointerType;
			this.source = source;
			this.targetOffset = targetOffset;
			this.targetPos = targetPOS;
			this.targetIndex = targetIndex;
			sourceOffset = srcOffset;
//			System.out.println(Source +" " + SourceIndex + " " + Type + " " + pos + " " + TargetOffset + " " + TargetIndex + " " + source.getOffset());
		}catch(Exception pointerex){
			//System.out.println("erro r9274 "+ pointerex);
		}
		
	}

	/** EXTRA CODE
	 * Constructor for relations other than gradation and ontology nodes.
	 * @param source Source synset of type <code>PointerTarget</code>
	 * @param index index of the source synset.
	 * @param pointerType <code>PointerType</code> is the type of relation.
	 * @param targetPOS <code>POS</code> of the target synset.
	 * @param targetOffset offset of the word in the data_txt file
	 * @param targetIndex index of the target word
	 */
/*	public Pointer(PointerTarget source,int index,PointerType pointerType,POS targetPOS,long targetOffset,int targetIndex, Long wordLexRelWord) {
		try{
			SourceIndex = index;
			Type = pointerType;
			Source = source;
			TargetOffset = targetOffset;
			pos = targetPOS;
			TargetIndex = targetIndex;
			
//			System.out.println(Source +" " + SourceIndex + " " + Type + " " + pos + " " + TargetOffset + " " + TargetIndex + " " + source.getOffset());
		}catch(Exception pointerex){
			//System.out.println("erro r9274 "+ pointerex);
		}
		
	}
*/
	
	/* Following constructor added to handle gradation.		-akshay.*/
/**
 * Constructor for Gradation relation.
 */
	public Pointer(PointerTarget source,int index,PointerType pointerType,POS targetPOS,long targetOffset,int targetIndex, long targetOffsetGradation, int targetIndexGradation) {
		try{
			sourceIndex = index;
			type = pointerType;
			this.source = source;
			this.targetOffset = targetOffset;
			this.targetPos = targetPOS;
			this.targetIndex = targetIndex;
			this.targetIndexGradation = targetIndexGradation;
			this.targetOffsetGradation = targetOffsetGradation;
		}catch(Exception pointerex){
			//System.out.println("erro r9274 "+ pointerex);
		}
		
	}

/////////// Following constructor is for Ontology Nodes. \\\\\\\\\\\\\
	/**
	 * Constructor for ontology nodes.
	 */
	public Pointer(int index, PointerType pointertype, long ontopointerValue){
		try{
			sourceIndex = index;
			ontopointer = ontopointerValue;
			type = pointertype;
		}catch(Exception pointerex){
			
		}
	}
	/**
	 * Get the source index of this pointer.
	 * @return index of the Source synset.
	 */
	public int getSourceIndex() { // Get the source index of this pointer.
		return(sourceIndex);
	}

	/**
	 * Get relation type
	 * @return <code>PointerType</code>.
	 */
	public PointerType getType() {
		return(type);
	}
/**
 * Whether the relation is a lexical relation.
 * @return true if relation is lexical, false otherwise.
 */
	public boolean isLexical() {
		if (sourceIndex == 0) return(false);
		else return(true);		
	}

/*	public boolean isGradation() {
		if (SourceIndex == 0) return(false);
		else return(true);		
	}
*/
	/**
	 * Get the source of this pointer.
	 */
	public PointerTarget getSource() {  // Get the source of this pointer.
		return(source);
	}

	/**
	 * Get the Ontology nodes for this pointer.
	 * @return offset of the ontology node in from the synset data string.
	 */
	public long getOntoPointer(){
		return ontopointer;
	}
	
	/**
	 * Get the offset of the target within the target synset
	 * @return offset of the target synset from within the synset data string. For gradation relation this returns the Pre-Intemediate target. 
	 */
	public long getTargetOffset() {
	//Get the offset of the target within the target synset
		return(targetOffset);
	}
//Following method added to get 2nd offset of gradation -akshay.
	/**
	 * This method is for the gradation relation. Get the offset of the Post-intermediate target within the target synset.
	 * @return offset of the Post-Intermediate target from within the synset data string.   
	 */
	public long getTargetOffset1() {
		//Get the offset of the target within the target synset
			return(targetOffsetGradation);
		}

	/**
	 * Get the synset that is the target of this pointer.
	 * @return <code>Synset</code> which is target of this relation. For gradation relation this returns the Pre-Intermediate target from within the synset data string.
	 * @throws JHWNLException 
	 * @throws Exception exception
	 */
	public Synset getTargetSynset() throws JHWNLException{
	//Get the synset that is the target of this pointer.
		Synset synset;
		if(type.equals(PointerType.ONTO_NODES)) {
			synset = Dictionary.getInstance().getOntoSynset(targetOffset);
		} else {
			synset = Dictionary.getInstance().getSynsetAt(null, targetOffset);//targetPos,targetOffset);
		}
		return synset;
	}
//Following method added to get the 2nd synset of gradation. -akshay.
	/**
	 * This method is for the gradation relation. Get the offset of the Post-intermediate target within the target synset.
	 * @return offset of the Post-Intermediate target.   

	 */
	public Synset getTargetSynset1() throws JHWNLException {
		//Get the synset that is the target of this pointer.
			Synset synset = Dictionary.getInstance().getSynsetAt(targetPos,targetOffsetGradation);		
			return synset;
		}
/**
 * Get the ontology nodes hierarchy for entered word.
 * @return String containing parents of entered lemma till the TOP node, sepearted by semicolon. 
 * @throws JHWNLException 
 */
	public String getOntoTree() throws JHWNLException{
		Synset synset = Dictionary.getInstance().getOntoSynset(ontopointer);
		return synset.getOntoNodes();
	}
	
	/**
	 * Overrides: equals in class java.lang.Object
	 */
	public boolean equals(java.lang.Object object) { // Overrides: equals in class java.lang.Object
		if (this.equals(object)) return(true);
		else return(false);	
	}	
	
/**
 * Get the actual target of this pointer. For gradation relation this gives the Pre-Intermediate target. 
 * @return target <code>Synset</code> from the file data_txt.
 * @throws Exception
 */
	public PointerTarget getTarget() throws Exception { // Get the actual target of this pointer.
		Synset synset = Dictionary.getInstance().getSynsetAt(targetPos,targetOffset); 
		if (this.isLexical()){// || synset.hasAntonym()) {
//			System.out.println("Tgt Idx "+TargetIndex);
				Word w = synset.getWord(targetIndex);
				return(w);
		}
		else {
			return synset;
		}
	}

	/**
	 * Get the antonyms of this pointer.  
	 * @return <code>String</code> from the file data_txt.
	 * @throws Exception 
	 */

	public String getAntonymPairs() throws Exception{
		Synset synset = Dictionary.getInstance().getSynsetAt(targetPos, sourceOffset);
		if(synset.hasAntonym()){
			return (synset.getWordPairs());
		}
		else return null;
	}
	
//	private Vector fillCombo(Pointer[] pointerarr, int size, Vector<String> b1, String p, int k) {
//		String s = new String();
//		String s1 = new String();
//		int ptype = 0;
//		@SuppressWarnings("unused") String src = "";
//		@SuppressWarnings("unused") String type = "";
//		@SuppressWarnings("unused") String tmp = "";
//		try{
//			for ( int j = 0;j < size;j++ ) {
//				
//				s = "";
////				if(pointerarr[j].getType().toString().contains("ANTO")){
////					String antopair = pointerarr[j].getAntonymPairs();
////					//antopair = antopair.replaceAll("_", " ");
//////					System.out.println("antopair "+antopair);
////					StringTokenizer antotokens = new StringTokenizer(antopair, "; ");
////					while (antotokens.hasMoreTokens()){
////						String pair = antotokens.nextToken("; ");
////						String base = pair.substring(0,pair.indexOf(":"));
////						base = base.replaceAll("_", " ");
////						String targetantonym = pair.substring(pair.indexOf(":")+1);
////						targetantonym = targetantonym.replaceAll("_", " ");
////						if(base.equals(enteredword)){
////							//System.out.println("VOILA " + enteredword + " "+targetantonym);
////							String targetantofrompointer = pointerarr[j].getTarget().toString();
////							targetantofrompointer = targetantofrompointer.replaceAll("_", " ");
////							if(targetantofrompointer.equals(targetantonym)){
////								s += targetantofrompointer + "; ";
////								s += pointerarr[j].getTargetSynset().toString() + "; ";
////								s += pointerarr[j].getTargetSynset().getGloss() + "; ";
////								//System.out.println("IS IT RIGHT? "+s);
////							}
////						}
////					}
////				}
//				if(pointerarr[j].getType().toString().contains("ONTO")){
//					s = pointerarr[j].getOntoTree();
////					System.out.println(">>> "+onto);
//				}
//				else{
//					s += pointerarr[j].getTargetSynset().toString() + "; ";
//					s += pointerarr[j].getTargetSynset().getGloss() + "; ";
//				}
////				if(pointerarr[j].getSource()!=null){
////				src = "#"+pointerarr[j].getSource().toString();
////				s += src;
////				}
//				//s = s.replaceAll("_", " ");
//				if(!(pointerarr[j].getType().toString().contains("ONTO")))
//					s = s.replaceAll(":", ", ");
//				boolean sempty = false;
//				if (s.equals("") || s == null)
//					sempty = true;
//				if((pointerarr[j].getType()!=null) && (!sempty)){
////					if(p.equals("NOUN")){
////						ptype = 1;
////						//System.out.println("IN VECTOR s = "+s);
////						String lnounEntry = pointerarr[j].getType().toString()+"|"+k+"$"+s;
////						if(!lnoun.contains(lnounEntry))
////							lnoun.add(lnounEntry);
////					}
////					else if(p.equals("VERB")){
////						ptype = 2;
////						String lverbEntry = pointerarr[j].getType().toString()+"|"+k+"$"+s;
////						if(!lverb.contains(lverbEntry))
////						lverb.add(lverbEntry);
////					}
////					else if(p.equals("ADJECTIVE")){
////						ptype = 3;
////						String ladjEntry = pointerarr[j].getType().toString()+"|"+k+"$"+s;
////						if(!ladj.contains(ladjEntry))
////						ladj.add(ladjEntry);
////					}
////					else if(p.equals("ADVERB")){
////						ptype = 4;
////						String ladvEntry = pointerarr[j].getType().toString()+"|"+k+"$"+s;
////						if(!ladv.contains(ladvEntry))
////						ladv.add(ladvEntry);
////					}
//					
//					//if(pointerarr[j].getType()!=null)
//					if((pointerarr[j].getType().toString() != null))
//						if(pointerarr[j].getType().toString().contains("HYPE"))
//						{
//							if(!b1.contains(pointerarr[j].getType().toString())) b1.add(pointerarr[j].getType().toString());
//							tree.add(k+"$"+s);
//							gethypertree(pointerarr[j],k);
//							tree.add(p);
//						}
//				}
//			}			
//		}catch(Exception e){}
//		}
		
	@SuppressWarnings("unused")
	private void gethypertree(Pointer p, int j) {
		try {
			String temp="";
			Pointer ptr[];
			Synset s = p.getTargetSynset();
			ptr = s.getPointers();
			for(int i=0;i<ptr.length;i++){
				if((ptr[i].getType() != null))
					if((ptr[i].getType().toString() != null))
						if(ptr[i].getType().toString().contains("HYPE")){
							temp = ptr[i].getTargetSynset().toString()+"; "+ptr[i].getTargetSynset().getGloss();
							//temp = temp.replaceAll("_"," ");
							temp = temp.replaceAll(":", ", ");
							tree.add(j+"$"+temp);
							//srt += "\t"+temp + "\n";
							gethypertree(ptr[i], j);
							tree.add("EOC");
//							System.out.println();
//							for(int idx=0; idx < tree.size(); idx++)
//								System.out.println(">>> "+tree.elementAt(idx));
						}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Following accessor method added for 2nd gradation synset.		-akshay 
	/**
	 * Get the actual target of this pointer. For gradation this gives the Post-Intermediate target.
	 * @return target <code>Synset</code> from the file data_txt.
	 * @throws Exception
	 */

	public PointerTarget getTarget1() throws Exception { // Get the actual target of this pointer.
		Synset synset = Dictionary.getInstance().getSynsetAt(targetPos,targetOffsetGradation); 
		if (this.isLexical()) {
			Word w = synset.getWord(targetIndexGradation);
			return(w);
		}
		else {
			return synset;
		}
	}

	/**
	 * Overrides: toString in class java.lang.Object
	 */
	public java.lang.String toString() {    //Overrides: toString in class java.lang.Object
		String returnstring;
		if (this.isLexical()) {
			returnstring = "LEXICAL";	
			return(returnstring);
		} else {
			returnstring = "SEMANTIC";
			return(returnstring);
		}
	}

}
