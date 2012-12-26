package naumova.courework;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class Tests {

	public CourseWork courseWork = new CourseWork();
	public CodeReaderFileSystem codeReaderFileSystem = new CodeReaderFileSystem();

	@Test
	/**
	 * Test without includes
	 */
	public void withoutIncludeTest() {
		String result = codeReaderFileSystem
				.readFromFile("./test_result/test1.txt");
		assertEquals(result, courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test1"),
				new CodeReaderFileSystem("./c_code/test1/")));
	}

	@Test
	/**
	 * Test with one include
	 */
	public void withIncludeTest() {
		String result = codeReaderFileSystem
				.readFromFile("./test_result/test2.txt");
		assertEquals(result, courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test2"),
				new CodeReaderFileSystem("./c_code/test2/")));
	}

	@Test
	/**
	 * Test with three includes
	 */
	public void withThreeIncludesTest() {
		String result = codeReaderFileSystem
				.readFromFile("./test_result/test3.txt");
		assertEquals(result, courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test3"),
				new CodeReaderFileSystem("./c_code/test3/")));
	}

	@Test
	/**
	 * Test with six files and seven includes
	 */
	public void sixFilesSevenIncludesTest() {
		String result = codeReaderFileSystem
				.readFromFile("./test_result/test4.txt");
		assertEquals(result, courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test4"),
				new CodeReaderFileSystem("./c_code/test4/")));
	}

	@Test
	/**
	 * Test with six files and eight includes
	 */
	public void sixFilesEightIncludesTest() {
		String result = codeReaderFileSystem
				.readFromFile("./test_result/test5.txt");
		assertEquals(result, courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test5"),
				new CodeReaderFileSystem("./c_code/test5/")));
	}

	@Test
	/**
	 * Test with six files and eight includes. There are commented 
	 * includes in files menuCmdID.h and resource.h by two slashes
	 */
	public void commentedIncludesTwoSlashesTest() {
		String result = codeReaderFileSystem
				.readFromFile("./test_result/test6.txt");
		assertEquals(result, courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test6"),
				new CodeReaderFileSystem("./c_code/test6/")));
	}

	@Test
	/**
	 * Test with six files and eight includes. There are commented 
	 * includes in files menuCmdID.h by slash+star and resource.h by two slashes
	 */
	public void commentedIncludesSlasheMultiplicationTest() {
		String result = codeReaderFileSystem
				.readFromFile("./test_result/test7.txt");
		assertEquals(result, courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test7"),
				new CodeReaderFileSystem("./c_code/test7/")));
	}

	@Test
	/**
	 * Test with a lot of files
	 */
	public void manyFilesTest() {
		String result = codeReaderFileSystem
				.readFromFile("./test_result/test8.txt");
		assertEquals(result, courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test8"),
				new CodeReaderFileSystem("./c_code/test8/")));
	}

	@Test
	/**
	 * Test3 but result was set in code
	 */
	public void withThreeIncludesTest2() {
		List<FileCourse> expectedList = new ArrayList<FileCourse>();
		FileCourse encodingMapperCpp = new FileCourse("EncodingMapper.cpp");
		encodingMapperCpp.addInclude("precompiledHeaders.h");
		encodingMapperCpp.addInclude("EncodingMapper.h");
		encodingMapperCpp.addInclude("Scintilla.h");
		expectedList.add(encodingMapperCpp);
		FileCourse encodingMapperH = new FileCourse("EncodingMapper.h");
		expectedList.add(encodingMapperH);
		assertEquals(CourseWork.print(expectedList), courseWork.parseDirectory(CourseWork.getNamesOfFiles("./c_code/test3"),
				new CodeReaderFileSystem("./c_code/test3/")));
	}

	@Test

	public void withThreeIncludesTest3() {
		List<FileCourse> expectedList = new ArrayList<FileCourse>();
		List<FileCourse> actualList = new ArrayList<FileCourse>();
		FileCourse encodingMapperCpp = new FileCourse("EncodingMapper.cpp");
		encodingMapperCpp.addInclude("precompiledHeaders.h");
		encodingMapperCpp.addInclude("EncodingMapper.h");
		encodingMapperCpp.addInclude("Scintilla.h");
		expectedList.add(encodingMapperCpp);
		FileCourse encodingMapperH = new FileCourse("EncodingMapper.h");
		expectedList.add(encodingMapperH);
		FileCourse actualFileCourseCpp = new FileCourse("EncodingMapper.cpp");
		String actualCppFileContent = "#include \"precompiledHeaders.h\"\r\n"
				+ "#include \"EncodingMapper.h\"\r\n"
				+ "#include \"Scintilla.h\"\r\n";
		actualFileCourseCpp.setIncludes(courseWork.getAllHeadersInFile(actualCppFileContent));
		actualList.add(actualFileCourseCpp);
		FileCourse actualFileCourseH = new FileCourse("EncodingMapper.h");
		String actualHFileContent = "";
		actualFileCourseH.setIncludes(courseWork.getAllHeadersInFile(actualHFileContent));
		actualList.add(actualFileCourseH);
		assertEquals(CourseWork.print(expectedList), CourseWork.print(actualList));
	}

	@Test
	public void getAllHeadersInFileTest() {
		String codeWithHeaders = "#include <stdio.h>\r\n"
				+ "#include \"precompiledHeaders.h\"\r\n"
				+ "#include \"EncodingMapper.h\"\r\n"
				+ "#include \"Scintilla.h\"\r\n";
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("stdio.h");
		expectedList.add("precompiledHeaders.h");
		expectedList.add("EncodingMapper.h");
		expectedList.add("Scintilla.h");
		assertEquals(expectedList,
				courseWork.getAllHeadersInFile(codeWithHeaders));
	}

	@Test
	public void getAllHeadersInFileWithCommentsTest() {
		String codeWithHeaders = "#include <stdio.h>\r\n"
				+ "//#include \"precompiledHeaders.h\"\r\n"
				+ "#include \"EncodingMapper.h\"\r\n"
				+ "#include \"Scintilla.h\"\r\n";
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("stdio.h");
		expectedList.add("EncodingMapper.h");
		expectedList.add("Scintilla.h");
		assertEquals(expectedList,
				courseWork.getAllHeadersInFile(codeWithHeaders));
	}

	@Test
	public void getAllHeadersInFileWithOtherCommentsTest() {
		String codeWithHeaders = "#include <stdio.h>\r\n"
				+ "/*#include \"precompiledHeaders.h\"\r\n"
				+ "#include \"EncodingMapper.h\"\r\n*/"
				+ "#include \"Scintilla.h\"\r\n";
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("stdio.h");
		expectedList.add("Scintilla.h");
		assertEquals(expectedList,
				courseWork.getAllHeadersInFile(codeWithHeaders));
	}
	
	@Test
	public void codeReaderInterfaceImplemenationTest() {
		HashMap<String, String> fileContents = new HashMap<String,String>();
		fileContents.put("main.c", "#include <stdio.h>");
		fileContents.put("stdio.h", "#include <foo.h>\r\n" + "int printf(const"
		+"char *, ...);");
		fileContents.put("foo.h", "/*blah blah blah*/");
		List<String> fileNames = new ArrayList<String>();
		fileNames.add("main.c");
		fileNames.add("stdio.h");
		assertEquals("main.c:\r\n\t" +
				"stdio.h\r\n" +
				"stdio.h:\r\n\t" +
				"foo.h\r\n" +
				"foo.h:\r\n",
				courseWork.parseDirectory(fileNames, new CodeReaderFromMemory(fileContents)));
	}
	
}
