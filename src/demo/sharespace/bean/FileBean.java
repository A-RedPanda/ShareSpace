package demo.sharespace.bean;

import java.io.Serializable;

public class FileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4567470827709851261L;

	private String fileId;
	
	private String fileName;
	
	private String filePath;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
