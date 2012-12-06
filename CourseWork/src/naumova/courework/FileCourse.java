package naumova.courework;

import java.util.ArrayList;
import java.util.List;

public class FileCourse {

	private String fileName;
	private List<String> includes;

	public FileCourse() {
		includes = new ArrayList<String>();
	}

	public FileCourse(String _fileName) {
		fileName = _fileName;
		includes = new ArrayList<String>();
	}

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
