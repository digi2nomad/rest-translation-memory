package org.digi2nomad.translationmemory;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The unit of translation memory. This class is used to transfer data between
 * the service layer and the controller layer and to store data in the database.
 */
@Data
public class TranslationmemoryUnit {
	
	
	private Long id;
	
	private TranslationProject translationProject;

	@NotNull	
	private SegmentType segmentType;
	
	private Instant createDate;	

	/**
	 * @param id
	 * @param project
	 * @param segmentType
	 * @param createDate
	 */
	public TranslationmemoryUnit(Long id, 
			TranslationProject project, 
			SegmentType segmentType, 
			Instant createDate) {
		this.id = id;
		this.translationProject = project;
		this.segmentType = segmentType;
		this.createDate = createDate;	
	}
	
	/**
	 *
	 * @param project
	 * @param segmentType
	 */
	public TranslationmemoryUnit(
			TranslationProject project, 
			SegmentType segmentType) { 
		this.translationProject = project;
		this.segmentType = segmentType;
	}

	
}
