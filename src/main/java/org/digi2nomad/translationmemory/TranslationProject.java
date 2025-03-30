package org.digi2nomad.translationmemory;

import java.time.Instant;

import lombok.Data;

/**
 * 
 */
@Data
public class TranslationProject {
	private Long id;
	private String name;
	private String description;
	private Instant createDate;
	
	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param createDate
	 */
	public TranslationProject(Long id, 
			String name, 
			String description, 
			Instant createDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createDate = createDate;
	}
	
	/**
	 * @param name
	 * @param description
	 */
	public TranslationProject(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * default constructor
	 */
	public TranslationProject() {
	}
	
}
