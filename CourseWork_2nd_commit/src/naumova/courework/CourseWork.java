package naumova.courework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseWork {

	public List<FileCourse> files;

	/**
	 * Empty constructor
	 */
	public CourseWork() {
		files = new ArrayList<FileCourse>();
	}

	/**
	 * Method return String with file content.
	 * 
	 * @param pathToFile
	 *            - Path to file which is red
	 * @return String with file content
	 */
	public static String readFromFile(String pathToFile) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(pathToFile));

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

	/**
	 * Method return list of includes in file content
	 * 
	 * @param fileContent
	 *            - String with file content
	 * @return list of includes in file content
	 */
	public List<String> getAllHeadersInFile(String fileContent) {
		List<String> includesList = new ArrayList<String>();
		String[] lines = fileContent.split("\r\n");// split to lines
		// pattern for regular expression
		String re1 = "#include \"";
		String re2 = "((?:[a-z][a-z0-9_]+).h)";
		String re3 = "\"";
		Pattern p = Pattern.compile(re1 + re2 + re3, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		boolean comments = false;// indicator for comments slash+star
		for (String line : lines) {
			Matcher m = p.matcher(line);
			if (line.contains("/*")) {
				comments = true;
			} else if (line.contains("*/")) {
				comments = false;
			}
			while (m.find() && !comments) {
				if (line.contains("//")) {// if line contains two slashes before
											// include then skip this line
					int indexComment = line.indexOf("//");
					int indexSharp = line.indexOf("#");
					if (indexComment > indexSharp) {
						includesList.add(m.group(1).trim());
					}
				} else {
					includesList.add(m.group(1).trim());
				}
			}
		}
		return includesList;
	}

	/**
	 * Method return result string: nameOfFile:includes
	 * 
	 * @param pathToCpp
	 *            - path to files with .cpp extension
	 * @param pathToH
	 *            - path to files with .h extension
	 * @return
	 */
	public String parseDirectory(String pathToCpp, String pathToH) {
		String fileName;
		File folderCpp = new File(pathToCpp);
		File folderH = new File(pathToH);
		File[] listOfFilesCpp = folderCpp.listFiles();
		File[] listOfFilesH = folderH.listFiles();

		for (int i = 0; i < listOfFilesCpp.length; i++) {

			if (listOfFilesCpp[i].isFile()) {
				fileName = listOfFilesCpp[i].getName();
				if (fileName.toLowerCase().endsWith(".cpp")) {
					FileCourse tempFile = new FileCourse();
					String fileContent = readFromFile(listOfFilesCpp[i]
							.getPath());
					tempFile.setFileName(fileName);
					tempFile.setIncludes(getAllHeadersInFile(fileContent));
					files.add(tempFile);
				}
			}
		}
		for (int i = 0; i < listOfFilesH.length; i++) {

			if (listOfFilesH[i].isFile()) {
				fileName = listOfFilesH[i].getName();
				if (fileName.toLowerCase().endsWith(".h")) {
					FileCourse tempFile = new FileCourse();
					String fileContent = readFromFile(listOfFilesH[i].getPath());
					tempFile.setFileName(fileName);
					tempFile.setIncludes(getAllHeadersInFile(fileContent));
					files.add(tempFile);
				}
			}
		}
		return print();
	}

	/**
	 * Method print result to console, to result string from files(list of
	 * FileCourse - member of CourseWork)
	 * 
	 * @return result string
	 */
	public String print() {
		String result = "";
		for (FileCourse fileCourse : files) {
			System.out.println(fileCourse.getFileName() + ":");
			result += fileCourse.getFileName() + ":" + "\r\n";
			for (String include : fileCourse.getIncludes()) {
				System.out.println("\t" + include);
				result += "\t" + include + "\r\n";
			}
		}
		return result;
	}

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		CourseWork courseWork = new CourseWork();
		courseWork.parseDirectory("./c_code/cpp/test3", "./c_code/h/test3");
	}

}
