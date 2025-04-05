package org.digi2nomad.translationmemory.data.dao;

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
	
	@NotNull
	private Long projId;

	@NotNull	
	private SegmentType segmentType;
	
	private Instant createDate;	

	/**
	 * @param id
	 * @param projId
	 * @param segmentType
	 * @param createDate
	 */
	public TranslationmemoryUnit(Long id, 
			Long projId, 
			SegmentType segmentType, 
			Instant createDate) {
		this.id = id;
		this.projId = projId;
		this.segmentType = segmentType;
		this.createDate = createDate;	
	}
	
	/**
	 *
	 * @param projId
	 * @param segmentType
	 */
	public TranslationmemoryUnit(
			Long projId, 
			SegmentType segmentType) { 
		this.projId = projId;
		this.segmentType = segmentType;
	}

	/**
	 * default constructor
	 */
	public TranslationmemoryUnit() {
	}

	
}
