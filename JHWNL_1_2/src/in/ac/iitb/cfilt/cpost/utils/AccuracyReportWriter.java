package in.ac.iitb.cfilt.cpost.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

public class AccuracyReportWriter {
	private String filename = "AccuracyReport";
	public PrintStream out = null;
	
	public AccuracyReportWriter(){
		this.filename = filename + "." + new Timestamp(System.currentTimeMillis()).toString().replaceAll(" ", ".");
		try {
			out = new PrintStream(new FileOutputStream(this.filename) ,true, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AccuracyReportWriter(String filename){
		this.filename = filename + "." + new Timestamp(System.currentTimeMillis()).toString().replaceAll(" ", ".");
		try {
			out = new PrintStream(new FileOutputStream(this.filename) ,true, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void done(double accuracy){
		new File(filename).renameTo(new File(filename+"|"+accuracy));
	}
}
