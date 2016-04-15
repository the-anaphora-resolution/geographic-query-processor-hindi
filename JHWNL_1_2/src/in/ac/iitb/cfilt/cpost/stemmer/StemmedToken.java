package in.ac.iitb.cfilt.cpost.stemmer;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

public class StemmedToken implements Serializable {

	private static final long serialVersionUID = 8424882257677766336L;

	private String token;
	private Vector<StemmerRuleResult> stemmedOutputs;
	
	public StemmedToken(String token){
		this.token = token;
		stemmedOutputs = new Vector<StemmerRuleResult>();
	}
	
	public void setToken(String token){
		this.token = token;
	}
	
	public void sortResults(){
		Collections.sort(stemmedOutputs);
	}
	
	public String getToken(){
		return token;
	}
	
	public boolean addStemmerOutput(StemmerRuleResult srresult){
		return stemmedOutputs.add(srresult);
	}
	
	public boolean addAllStemmerOutputs(Vector<StemmerRuleResult> srresult){
		return stemmedOutputs.addAll(srresult);
	}
	
	public Vector<StemmerRuleResult> getStemmedOutputs(){
		return stemmedOutputs;
	}

	public void clearOutputs() {
		stemmedOutputs.clear();
	}
	
	public String getAmbiguityScheme(){
		String retVal = "";
		
		HashSet<String> categorySet = new HashSet<String>();
		for(StemmerRuleResult stemmedOutput : stemmedOutputs){
			String cat = stemmedOutput.getCategory().trim();
			if(!cat.equals("unknown")){
				categorySet.add(cat);
			}
		}
		Vector<String> categoryList = new Vector<String>(categorySet);
		Collections.sort(categoryList);
		for(int i = 0; i < categoryList.size(); i++){
			retVal += categoryList.get(i) + "*";
		}
		if(!retVal.equals(""))
			retVal = retVal.substring(0, retVal.length() - 1);
		return retVal;
	}
}