package naumova.courework;

import java.util.Map;

public class CodeReaderFromMemory implements CodeReader {
	

	private Map<String, String> fileContents;

	public CodeReaderFromMemory(Map<String, String> fileContents) {
	    this.fileContents = fileContents;
	  }
	
	@Override
	/**
	 * Method gets fileContent from map fileContents with key pathToFile
	 */
	public String readFromFile(String pathToFile) {
	    return fileContents.get(pathToFile);
	  }
	
	

}
