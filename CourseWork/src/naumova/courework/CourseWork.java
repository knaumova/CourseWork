package naumova.courework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
		boolean isInclude = false, isIncludeName = false, isComments = false, isExtension = false;
		for (String line : lines) {
			StringBuilder sb = new StringBuilder();
			char[] charArray = line.toCharArray();
			// isComments = false;
			isInclude = false;
			isIncludeName = false;
			for (int i = 0; i < charArray.length; i++) {
				switch (charArray[i]) {
				case '/':
					if (i == 0) {
						break;
					}
					if (charArray[i - 1] == '/') {
						i = charArray.length - 1;
					} else {
						isComments = false;
					}
					break;
				case '*':
					if (!(i == 0)) {

						if (charArray[i - 1] == '/') {
							isComments = true;
							i = charArray.length - 1;
						}
					} else if (i == charArray.length - 1) {
						if ((charArray[i + 1] == '/')) {
							isComments = false;
						}
					}
					break;
				case '#':
					if (isComments) {
						break;
					}
					sb = new StringBuilder();
					isInclude = true;
					// sb.append('#');
					break;
				case 'i':
					if (isIncludeName) {
						sb.append('i');
					} else if (!(isInclude && charArray[i - 1] == '#')) {
						isInclude = false;
						sb = new StringBuilder();
					}

					break;
				case 'n':
					if (isIncludeName) {
						sb.append('n');
					} else if (!(isInclude && charArray[i - 1] == 'i')) {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case 'c':
					if (isIncludeName) {
						sb.append('c');
					} else if (!(isInclude && charArray[i - 1] == 'n')) {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case 'l':
					if (isIncludeName) {
						sb.append('l');
					} else if (!(isInclude && charArray[i - 1] == 'c')) {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case 'u':
					if (isIncludeName) {
						sb.append('u');
					} else if (!(isInclude && charArray[i - 1] == 'l')) {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case 'd':
					if (isIncludeName) {
						sb.append('d');
					} else if (!(isInclude && charArray[i - 1] == 'u')) {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case 'e':
					if (isIncludeName) {
						sb.append('e');
					} else if (!(isInclude && charArray[i - 1] == 'd')) {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case ' ':
					if (!(isInclude && charArray[i - 1] == 'e')) {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case '"':
					if (isInclude && charArray[i - 1] == ' ') {
						// sb.append('"');
						isInclude = false;
						isIncludeName = true;
					} else {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case '.':
					if (isIncludeName) {
						sb.append('.');
						isIncludeName = false;
						isExtension = true;
					} else {
						isIncludeName = false;
						sb = new StringBuilder();
					}
					break;
				case 'h':
					if (isIncludeName) {
						sb.append('h');
					} else if (isExtension) {
						sb.append('h');
						isExtension = false;
						if (i + 1 < charArray.length) {
							if (charArray[i + 1] == '"') {
								includesList.add(sb.toString());
							}
						}
					} else {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				default:
					if (isIncludeName && isInSet(charArray[i])) {
						sb.append(charArray[i]);
					} else {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				}
			}
		}
		return includesList;
	}

	private boolean isInSet(char c) {
		c = String.valueOf(c).toLowerCase().toCharArray()[0];
		if (c == '_' || c == '-')
			return true;
		for (char i = 'a'; i <= 'z'; i++) {
			if (i == c)
				return true;
		}
		for (char i = '0'; i <= '9'; i++) {
			if (i == c)
				return true;
		}
		return false;
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
		return print(files);
	}

	/**
	 * Method print result to console, to result string from files(list of
	 * FileCourse - member of CourseWork)
	 * 
	 * @return result string
	 */
	public static String print(List<FileCourse> files) {
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
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CourseWork courseWork = new CourseWork();
		courseWork.parseDirectory("./c_code/cpp/test3", "./c_code/h/test3");
	}

}
