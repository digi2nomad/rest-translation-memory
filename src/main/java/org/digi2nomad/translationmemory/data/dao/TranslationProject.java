package org.digi2nomad.translationmemory.data.dao;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Translation project entity. This class is used to transfer data between
 * the service layer and the controller layer and to store data in the database.
 */
@Data
public class TranslationProject {
	
	private Long id;
	
	@NotBlank
	@Size(max=50, message="Name must be less than 50 long")
	private String name;
	
	@NotBlank
	@Size(max=250, message="Description must be less than 250 long")
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
