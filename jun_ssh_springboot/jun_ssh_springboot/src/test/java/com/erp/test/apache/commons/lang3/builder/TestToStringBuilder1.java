package com.erp.test.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestToStringBuilder1 {
	private String id = "id xxxx";
	private String name = "name xxxxx";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		System.out.println(new TestToStringBuilder1().toString());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.getId()).append(this.getName()).toString();
	}

}
