package naumova.courework;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Tests {

	public CourseWork courseWork = new CourseWork();

	@Test
	/**
	 * Test without includes
	 */
	public void test1() {
		String result = CourseWork.readFromFile("./test_result/test1.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test1",
				"./c_code/h/test1"));
	}

	@Test
	/**
	 * Test with one include
	 */
	public void test2() {
		String result = CourseWork.readFromFile("./test_result/test2.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test2",
				"./c_code/h/test2"));
	}

	@Test
	/**
	 * Test with three includes
	 */
	public void test3() {
		String result = CourseWork.readFromFile("./test_result/test3.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test3",
				"./c_code/h/test3"));
	}

	@Test
	/**
	 * Test with six files and seven includes
	 */
	public void test4() {
		String result = CourseWork.readFromFile("./test_result/test4.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test4",
				"./c_code/h/test4"));
	}

	@Test
	/**
	 * Test with six files and eight includes
	 */
	public void test5() {
		String result = CourseWork.readFromFile("./test_result/test5.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test5",
				"./c_code/h/test5"));
	}

	@Test
	/**
	 * Test with six files and eight includes. There are commented 
	 * includes in files menuCmdID.h and resource.h by two slashes
	 */
	public void test6() {
		String result = CourseWork.readFromFile("./test_result/test6.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test6",
				"./c_code/h/test6"));
	}

	@Test
	/**
	 * Test with six files and eight includes. There are commented 
	 * includes in files menuCmdID.h by slash+star and resource.h by two slashes
	 */
	public void test7() {
		String result = CourseWork.readFromFile("./test_result/test7.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test7",
				"./c_code/h/test7"));
	}

	@Test
	/**
	 * Test with a lot of files
	 */
	public void test8() {
		String result = CourseWork.readFromFile("./test_result/test8.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test8",
				"./c_code/h/test8"));
	}
	@Test
	public void test9()
	{
		List<FileCourse> expectedList = new ArrayList<FileCourse>();
		FileCourse encodingMapperCpp = new FileCourse("EncodingMapper.cpp");
		encodingMapperCpp.addInclude("precompiledHeaders.h");
		encodingMapperCpp.addInclude("EncodingMapper.h");
		encodingMapperCpp.addInclude("Scintilla.h");
		expectedList.add(encodingMapperCpp);
		FileCourse encodingMapperH = new FileCourse("EncodingMapper.h"); 
		expectedList.add(encodingMapperH);
		assertEquals(CourseWork.print(expectedList), courseWork.parseDirectory("./c_code/cpp/test3",
				"./c_code/h/test3"));
	}
}
