package naumova.courework;

import java.util.ArrayList;
import java.util.List;

public class FileCourse {

	private String fileName;
	private List<String> includes;
/**
 * Default constructor
 */
	public FileCourse() {
		includes = new ArrayList<String>();
	}
/**
 * Constructor with parameter - fileName
 * @param _fileName
 */
	public FileCourse(String _fileName) {
		fileName = _fileName;
		includes = new ArrayList<String>();
	}
/**
 * Method adds include's name
 * @param _include
 */
	public void addInclude(String _include) {
		includes.add(_include);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getIncludes() {
		return includes;
	}

	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}

}
