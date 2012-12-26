package naumova.courework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseWork {

	public List<FileCourse> files;


	/**
	 * Empty constructor
	 */
	public CourseWork() {
		files = new ArrayList<FileCourse>();
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
		boolean isInclude = false, isIncludeName = false, isComments = false, isExtension = false, isQuote = false;
		for (String line : lines) {
			StringBuilder sb = new StringBuilder();
			char[] charArray = line.toCharArray();
			isInclude = false;
			isIncludeName = false;
			isQuote = false;
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
						isInclude = false;
						isIncludeName = true;
						isQuote = true;
					} else {
						isInclude = false;
						sb = new StringBuilder();
					}
					break;
				case '<':
					if (isInclude && charArray[i - 1] == ' ') {
						isInclude = false;
						isIncludeName = true;
						isQuote = false;
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
							if (isQuote) {
								if (charArray[i + 1] == '"') {
									includesList.add(sb.toString());
								}
							} else {
								if (charArray[i + 1] == '>') {
									includesList.add(sb.toString());
								}
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

	/**
	 * Method return boolean value if character is possible to be in the file
	 * name
	 * 
	 * @param c
	 *            - character
	 * @return
	 */
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
	 * Method
	 * 
	 * @param names
	 *            - list of file names
	 * @param codeReader
	 *            - one of CodeReader's implementation.
	 * @return
	 */
	public String parseDirectory(List<String> names, CodeReader codeReader) {
		String fileName = "";
		for (int i = 0; i < names.size(); i++) {
			fileName = names.get(i);
			FileCourse tempFile = new FileCourse();
			String fileContent = codeReader.readFromFile(fileName);
			tempFile.setFileName(fileName);
			tempFile.setIncludes(getAllHeadersInFile(fileContent));
			files.add(tempFile);
		}
		return print(files);
	}

	/**
	 * Method print result to console, to result string from files(list of
	 * FileCourse - member of CourseWork
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
	 * Method gets file names with extensions .c, .h, .cpp from set directory
	 * 
	 * @param pathToFiles
	 * @return
	 */
	public static List<String> getNamesOfFiles(String pathToFiles) {
		List<String> fileNames = new ArrayList<String>();
		File folder = new File(pathToFiles);
		File[] listOfFiles = folder.listFiles();
		for (File fileCpp : listOfFiles) {
			if (fileCpp.isFile()) {
				if (fileCpp.getName().toLowerCase().endsWith(".c")
						|| fileCpp.getName().toLowerCase().endsWith(".h")
						|| fileCpp.getName().toLowerCase().endsWith(".cpp"))
					fileNames.add(fileCpp.getName());
			}
		}
		return fileNames;
	}

	/**
	 * Method get directory from console or default directory.
	 * 
	 * @return
	 */
	public static String getDirectoryPath() {
		Scanner lineInput = new Scanner(System.in);
		System.out
				.println("If you want to enter your directory with files of C code then press 1, else press any key:");
		int answer = lineInput.nextInt();
		switch (answer) {
		case 1:
			System.out.println("Enter directory's path in next format: C:/dir1/dir2/ or ./dir/ if your files are situeted in project directory:");
			String result = lineInput.next();
			return result;
		default:
			return "./c_code/";
		}
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CourseWork courseWork = new CourseWork();
		String directoryPath = getDirectoryPath();
		courseWork.parseDirectory(getNamesOfFiles(directoryPath),
				new CodeReaderFileSystem(directoryPath));
	}

}
