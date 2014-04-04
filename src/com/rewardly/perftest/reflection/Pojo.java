package com.rewardly.perftest.reflection;

import java.util.List;
import java.util.Map;

public class Pojo {
	private Long id;
	private String name;
	private List<String> notes;
	private Map<String, Integer> fields;

	public Pojo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	public Map<String, Integer> getFields() {
		return fields;
	}

	public void setFields(Map<String, Integer> fields) {
		this.fields = fields;
	}

}
