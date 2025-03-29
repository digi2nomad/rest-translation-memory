package org.digi2nomad.translationmemory;



import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 
 */
@Data
public class TranslationmemoryUnit {

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
	
	private Long id;
	
	private TranslationProject translationProject;

	@NotNull	
	private SegmentType segmentType;
	
	private Instant createDate;
	
}
