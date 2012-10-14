package naumova.courework;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.Test;

public class Tests {
	
	public CourseWork courseWork = new CourseWork();

	private String readFromFile(String pathToFile) {
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

	@Test
	public void test1() {
		String result = readFromFile("./test_result/test1.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test1", "./c_code/h/test1"));
	}
	@Test
	public void test2() {
		String result = readFromFile("./test_result/test2.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test2", "./c_code/h/test2"));
	}
	@Test
	public void test3() {
		String result = readFromFile("./test_result/test3.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test3", "./c_code/h/test3"));
	}
	@Test
	public void test4() {
		String result = readFromFile("./test_result/test4.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test4", "./c_code/h/test4"));
	}
	@Test
	public void test5() {
		String result = readFromFile("./test_result/test5.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test5", "./c_code/h/test5"));
	}
	@Test
	public void test6() {
		String result = readFromFile("./test_result/test6.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test6", "./c_code/h/test6"));
	}
	@Test
	public void test7() {
		String result = readFromFile("./test_result/test7.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test7", "./c_code/h/test7"));
	}
	@Test
	public void test8() {
		String result = readFromFile("./test_result/test8.txt");
		assertEquals(result, courseWork.parseDirectory("./c_code/cpp/test8", "./c_code/h/test8"));
	}
	

}
