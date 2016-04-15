package in.ac.iitb.cfilt.jhwnl.dictionary.file;

public class DictionaryFileType extends java.lang.Object {

	int filetypevalue;
	public DictionaryFileType(int value) {
		filetypevalue = value;
	}

	public static final DictionaryFileType DATA = new DictionaryFileType(1);
	public static final DictionaryFileType INDEX = new DictionaryFileType(2);
	public static final DictionaryFileType EXCEPTION = new DictionaryFileType(3);
	public static final DictionaryFileType[] FILE_TYPES = new DictionaryFileType[4];


	public String toString() {
	
		switch (this.filetypevalue) {

			case 1: return("DATA");
			case 2: return("INDEX");
			case 3: return("EXECEPTION");
			default: return(null); 		

		}
	
	}
}
