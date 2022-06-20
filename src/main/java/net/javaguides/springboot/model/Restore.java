package net.javaguides.springboot.model;

import lombok.Data;

@Data
public class Restore {

	private String dataBaseName;
	private String host;
	private String userName;
	private String fileName;
	public String getDataBaseName() {
		return dataBaseName;
	}
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}
	public Restore() {
		// TODO Auto-generated constructor stub
	}
	public Restore(String dataBaseName) {
		super();
		this.dataBaseName = dataBaseName;
	}
	

}
