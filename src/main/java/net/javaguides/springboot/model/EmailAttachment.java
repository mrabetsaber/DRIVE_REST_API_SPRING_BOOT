package net.javaguides.springboot.model;

import lombok.Data;

@Data
public class EmailAttachment {

	 public String name;
	 public String path;
	 public int size;
	 public String from;
	}