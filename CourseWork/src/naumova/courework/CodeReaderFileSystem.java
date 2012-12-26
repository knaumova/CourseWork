package naumova.courework;

import java.io.BufferedReader;
import java.io.FileReader;

public class CodeReaderFileSystem implements CodeReader{

	private String path = "";
	
	
	public CodeReaderFileSystem(String path)
	{
		this.path = path;
	}
	
	public CodeReaderFileSystem() {
	}

	@Override
	/**
	 * Method return String with file content.
	 * 
	 * @param nameOfFile
	 *            - Name of file which is read
	 * @return String with file content
	 */
	public String readFromFile(String nameOfFile) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path + nameOfFile));

			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			String ls = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			reader.close();
			return stringBuilder.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
