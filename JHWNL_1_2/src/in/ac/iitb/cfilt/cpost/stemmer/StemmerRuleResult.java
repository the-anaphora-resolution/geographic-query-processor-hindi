package in.ac.iitb.cfilt.cpost.stemmer;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class StemmerRuleResult implements Comparable, Serializable{

	private static final long serialVersionUID = -158865165318986691L;

	String root = "";
	String paradigm = "";
	String category = "";
	String ultimateDeletion = "";
	LinkedList<String> suffixList = null;
	
	
	public String getUltimateDeletion() {
		return ultimateDeletion;
	}
	public void setUltimateDeletion(String ultimateDeletion) {
		this.ultimateDeletion = ultimateDeletion;
	}
	public String getParadigm() {
		return paradigm;
	}
	public void setParadigm(String paradigm) {
		this.paradigm = paradigm;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public LinkedList<String> getSuffixList() {
		return suffixList;
	}
	public void setSuffixList(LinkedList<String> suffixList) {
		this.suffixList = suffixList;
	}
	public String getCategory(){
		return category;
	}
	public void setCategory(String category){
		this.category = category;
	}
	public void addSuffix(String suffix){
		suffixList.addFirst(suffix);
	}
	
	public StemmerRuleResult(String root, String paradigm, String ultimateDeletion, LinkedList<String> suffixList) {
		super();
		this.root = root;
		this.paradigm = paradigm;
		this.ultimateDeletion = ultimateDeletion;
		this.suffixList = suffixList;
	}
	
//	public StemmerRuleResult(String root, String paradigm, String ultimateDeletion, String suffix) {
//		super();
//		this.root = root;
//		this.paradigm = paradigm;
//		this.ultimateDeletion = ultimateDeletion;
//		this.suffixList = new LinkedList<String>();
//		this.suffixList.add(suffix);
//	}
	
	public StemmerRuleResult(String root, String paradigm, String category, String ultimateDeletion, String suffix) {
		super();
		this.root = root;
		this.paradigm = paradigm;
		this.category = category;
		this.ultimateDeletion = ultimateDeletion;
		this.suffixList = new LinkedList<String>();
		this.suffixList.add(suffix);
	}
	
	public StemmerRuleResult(String root, String paradigm, String category, String ultimateDeletion, LinkedList<String> suffixList) {
		super();
		this.root = root;
		this.paradigm = paradigm;
		this.category = category;
		this.ultimateDeletion = ultimateDeletion;
		this.suffixList = suffixList;
	}
	
	public String toString(){
		String retVal = null;
		retVal = "Root : " + this.root + " Paradigm : " + this.paradigm + " Category : " + this.category + " ultimateDeletion : " + this.ultimateDeletion + " Suffixes : ";
		if(this.suffixList != null){
			for(Iterator<String> suffixListIter = this.suffixList.iterator(); suffixListIter.hasNext();){
				retVal += suffixListIter.next() + "#"; 
			}
		}
		retVal = retVal.substring(0, retVal.length() - 1);
		return retVal;
	}
	
	public int getSuffixSize(){
		if(this.suffixList == null)
			return 0;
		else{
			int retVal = this.suffixList.size();
			if(this.suffixList.size() == 1 && this.suffixList.element() == ""){
				retVal--;
			}
			if(this.ultimateDeletion != ""){
				retVal++;
			}
			return retVal;
		}
	}
	
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof StemmerRuleResult))
			return false;
		StemmerRuleResult that = (StemmerRuleResult)o;
		return 
			this.paradigm.equals(that.paradigm) && 
			this.root.equals(that.root) &&
			this.ultimateDeletion.equals(that.ultimateDeletion) &&
			((this.category == null && that.category == null) || (this.category!=null && this.category.equals(that.category))) &&
			this.suffixList.equals(that.suffixList);
	}

	public int compareTo(Object o) {
		return this.toString().compareTo(o.toString());
	}
	
	public static void main(String args[]){
		LinkedList<String> test = new LinkedList<String>();
		test.addFirst("Dinesh");
		test.addFirst("Bhaskarrao");
		test.addFirst("Gadge");
		Iterator iter = test.iterator();
		while(iter.hasNext()){
			System.out.println((String)iter.next());
		}
	}
}
