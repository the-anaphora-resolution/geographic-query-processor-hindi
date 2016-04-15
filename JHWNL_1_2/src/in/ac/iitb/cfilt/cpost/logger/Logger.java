package in.ac.iitb.cfilt.cpost.logger;

public class Logger {
	private static String logString = "";
	private static String consoleLogString = "";
	private Logger(){
		
	}
	public static void println(String log){
//		consoleLogString+=log+"\n";
//		logString += log.replaceAll("\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;") + "<br/>";
		
	}
	public static void println(){
//		logString += "<br/>";
//		consoleLogString+="\n";
	}
	public static void print(String log){
//		consoleLogString+=log;
//		logString += log.replaceAll("\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	}
	public static String getLogString(){
		return logString;
	}
	public static String getConsoleLogString(){
		return consoleLogString;
	}
	public static void reset(){
		logString = "";
		consoleLogString="";
	}
}