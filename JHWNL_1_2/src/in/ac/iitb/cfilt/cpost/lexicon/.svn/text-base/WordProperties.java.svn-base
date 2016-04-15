package in.ac.iitb.cfilt.cpost.lexicon;

public class WordProperties{
	String paradigm,category;
	
	public WordProperties(String paradigm, String category) {
		this.paradigm = paradigm;
		this.category = category;
	}
	
	public WordProperties(){
		this.paradigm = null;
		this.category = null;
	}
	
	public void setProperties(String par,String cat){
		paradigm = par;
		category = cat;
	}
	
	public String getParadigm(){
		return paradigm;
	}
	
	public String getCategory(){
		return category;
	}
	
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof WordProperties))
			return false;
		WordProperties that = (WordProperties) o;
		return 
			this.category.equals(that.category) &&
			this.paradigm.equals(that.paradigm);
	}
}