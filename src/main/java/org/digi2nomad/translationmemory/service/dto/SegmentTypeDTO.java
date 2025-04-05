package org.digi2nomad.translationmemory.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SegmentTypeDTO {
	/**
	 * @param id
	 * @param type
	 * @param description
	 */
	public SegmentTypeDTO(long id, String type, String description) {
		this.id = id;
		this.type = type;
		this.description = description;
	}
	
	/**
	 * The default is a sentence
	 */
	public SegmentTypeDTO() {
		this.id = 3L;
		this.type = "sentence";
		this.description = "A sentence";
	}

	private long id;
	
	@NotNull
	@Size(max=9, message="Type must be less than 9 long")
	private String type;

	@NotNull
	@Size(max=20, message="Description must be less than 20 long")
	private String description;
	
}
