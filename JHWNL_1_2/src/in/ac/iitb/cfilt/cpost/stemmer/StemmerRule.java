package in.ac.iitb.cfilt.cpost.stemmer;

import in.ac.iitb.cfilt.cpost.lexicon.Wordlist;

public class StemmerRule {
	String paradigm;
	String ultimateInsertion;
	String ultimateDeletion;
	String penultimateInsertion;
	String penultimateDeletion;
	//String stem;
	String suffix;
	int priority;
	int depth = 0;
	
	public static final String DELIMITER = ",";
	public static final boolean MULTIPLE_POSSIBLE = false;
	
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof StemmerRule))
			return false;
		StemmerRule that = (StemmerRule)o;
		return 
			this.paradigm.equals(that.paradigm) && 
			this.ultimateInsertion.equals(that.ultimateInsertion) &&
			this.ultimateDeletion.equals(that.ultimateDeletion) &&
			this.penultimateInsertion.equals(that.penultimateInsertion) &&
			this.penultimateDeletion.equals(that.penultimateDeletion) &&
			this.suffix.equals(that.suffix) &&
			this.priority == that.priority;
	}
	
	public String getParadigm() {
		return paradigm;
	}
	public void setParadigm(String paradigm) {
		this.paradigm = paradigm;
	}
	public String getPenultimateDeletion() {
		return penultimateDeletion;
	}
	public void setPenultimateDeletion(String penultimateDeletion) {
		this.penultimateDeletion = penultimateDeletion;
	}
	public String getPenultimateInsertion() {
		return penultimateInsertion;
	}
	public void setPenultimateInsertion(String penultimateInsertion) {
		this.penultimateInsertion = penultimateInsertion;
	}
	public int getDepth(){
		return this.depth;
	}
	public void setDepth(int depth){
		this.depth = depth;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getUltimateDeletion() {
		return ultimateDeletion;
	}
	public void setUltimateDeletion(String ultimateDeletion) {
		this.ultimateDeletion = ultimateDeletion;
	}
	public String getUltimateInsertion() {
		return ultimateInsertion;
	}
	public void setUltimateInsertion(String ultimateInsertion) {
		this.ultimateInsertion = ultimateInsertion;
	}
	
	public StemmerRule(String paradigm, String suffix, String ultimateInsertion, String ultimateDeletion, String penultimateInsertion, String penultimateDeletion, int priority) {
		this.paradigm = paradigm;
		this.suffix = suffix;
		this.ultimateInsertion = ultimateInsertion;
		this.ultimateDeletion = ultimateDeletion;
		this.penultimateInsertion = penultimateInsertion;
		this.penultimateDeletion = penultimateDeletion;
		this.priority = priority;
	}

	public StemmerRule() {
		// TODO Auto-generated constructor stub
	}
	public StemmerRuleResult applyOn(String token, Wordlist wordlist) {
		// TODO Set paradigm etc too here. 
		StemmerRuleResult retVal = null;
		String result = token;
		try {
			result = this.performUltimateDeletionOn(result);
			result = this.performPenultimateDeletionOn(result);
			result = this.performPenultimateInsertionOn(result);
			result = this.performUltimateInsertionOn(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		retVal = new StemmerRuleResult(result, this.paradigm, wordlist.getCategory(this.paradigm), this.ultimateDeletion, this.suffix);
		
		return retVal;
	}
	
	private String performUltimateInsertionOn(String result) {
		String retVal = result;
		if(ultimateInsertion != null && !ultimateInsertion.equals(""))
			retVal += ultimateInsertion; 
		return retVal;
	}
	private String performPenultimateInsertionOn(String result) {
		String retVal = result;
		if(penultimateInsertion != null && !penultimateInsertion.equals("")){
			if(result.length() > 0)
				retVal = retVal.substring(0,retVal.length()-1) + penultimateInsertion + retVal.substring(retVal.length() - 1);
			else
				retVal = penultimateInsertion;
		}
		return retVal;
	}
	private String performPenultimateDeletionOn(String result) throws Exception {
		String retVal = result;
		if(penultimateDeletion != null && !penultimateDeletion.equals("")){
			int lastIndexOfpud = retVal.lastIndexOf(penultimateDeletion);
			if(!retVal.endsWith(penultimateDeletion)){
				if(lastIndexOfpud != -1){
					retVal = retVal.substring(0,lastIndexOfpud) + retVal.substring(lastIndexOfpud).replaceFirst(penultimateDeletion, "");					
				}
				else{
//					throw new Exception("Token does not have the given penultimate deletion");
				}
			}
			else{ 
				String tempRetVal = retVal.replaceAll(penultimateDeletion + "$","");
				retVal = this.performPenultimateDeletionOn(tempRetVal) + penultimateDeletion;
			}
		} 
		return retVal;
	}
	
	private String performUltimateDeletionOn(String result) throws Exception {
		String retVal = result;
		if(ultimateDeletion != null && !ultimateDeletion.equals("")){
			if(retVal.endsWith(ultimateDeletion)){
				retVal = retVal.replaceAll(ultimateDeletion + "$", "");
			}
			else{
//				throw new Exception("Token does not end with given ultimate deletion");
			}
		} 
		return retVal;
	}
	
	public static void main(String args[]){
		String test = "Dinesh,Gadge,,,hey,hello,,,1";
		String[] tests = test.split(StemmerRule.DELIMITER);
		for(int i = 0; i < tests.length; i++){
			System.out.println("'"+tests[i]+"'" + " " + tests[i].equals(""));
		}
		String t = "Dinesh";
		System.out.println(t);
		System.out.println(t.replaceAll("esh$",""));
		System.out.println(t);
		String retVal = "Dineshesh";
		System.out.println(retVal.substring(0, retVal.length()-1));
	}
	
	
}
