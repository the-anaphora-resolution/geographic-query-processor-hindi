package in.ac.iitb.cfilt.cpost;

import in.ac.iitb.cfilt.cpost.stemmer.StemmedToken;
import in.ac.iitb.cfilt.cpost.stemmer.Stemmer;
import in.ac.iitb.cfilt.cpost.stemmer.StemmerRuleResult;
import in.ac.iitb.cfilt.cpost.utils.UTFConsole;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class StemIt {
	private static Stemmer stemmer;
	
	public static void main(String[] args){
		Vector<String> tokens = new Vector<String>();
		String filename = "";
		String configFile = "";
		
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("--help")){

				System.err.println("SYNTAX -- StemIt -c <configfile>");
				System.exit(0);
			}
			else if(args[i].equals("-c")){
				if(i == args.length){
					System.err.println("configFile name missing");
					System.exit(0);
				}
				else{
					configFile = args[i+1];
				}
			}
			else{
				filename = args[i];
			}
		}
		
		try {
			BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF8"));
			String inputLine = null;
			inputLine = bfr.readLine();
			while(inputLine != null){
				String[] tokenWords = (inputLine.split(" "));
				for (int i = 0; i < tokenWords.length; i++) {
					tokens.add(tokenWords[i]);
				}
				inputLine = bfr.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stem(tokens,configFile);
	}
	public static void stem(Vector<String> tokenList,String configFile){
		System.out.println("Config pvath :"+configFile);
		
		//ConfigReader.read(configFile);
		stemmer = new Stemmer();
		//Vector<StemmedToken> stv;
		//stv = stemmer.stem(tokens);
		for (String token : tokenList){
			StemmedToken stoken = stemmer.stem(token);			
			System.out.println("\n"+token);
			//for (StemmedToken stoken : stv){
				Vector<StemmerRuleResult> strrv = stoken.getStemmedOutputs();
				for (StemmerRuleResult strr:strrv){
					//	System.out.println(strr.toString());
					UTFConsole.out.println("\t"+strr.getRoot() +"\t:" +strr.getParadigm() +
							"\t:"+strr.getSuffixList().size() +"->" + strr.getSuffixList() + 
							"[->" + strr.getSuffixList().getFirst().length());
				}
			//}
		}
	}
	
}