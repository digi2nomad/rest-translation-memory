package org.digi2nomad.translationmemory.data.dao;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * The unit variant of translation memory of a specific language.
 * This class is used to transfer data between the service layer 
 * and the controller layer and to store data in the database. 
 */
@Data
public class TranslationmemoryVariant {
	
	private Long id;
	
	@NotNull
	private Long tuId;
	
	@NotNull
	private Language language;

	@NotNull
	@Size(max=250, message="Segment must be less than 250 long")
	private String segment;
	
	private Instant createDate;
	
	private Instant useDate;
	
	private int useCount;
	
	private boolean reviewed;

	/**
	 * @param id
	 * @param tuId
	 * @param language
	 * @param segment
	 * @param createDate
	 * @param useDate
	 * @param useCount
	 * @param reviewed
	 */
	public TranslationmemoryVariant(Long id, 
			Long tuId, 
			Language language, 
			String segment, 
			Instant createDate, 
			Instant useDate, 
			int useCount,
			boolean reviewed) {
		this.id = id;
		this.tuId = tuId;
		this.language = language;
		this.segment = segment;
		this.createDate = createDate;
		this.useDate = useDate;
		this.useCount = useCount;
		this.reviewed = reviewed; 
	}
	
	/**
	 * @param tuId
	 * @param language
	 * @param segment
	 */
	public TranslationmemoryVariant(
			Long tuId, 
			Language language, 
			String segment) {
		this.tuId = tuId;
		this.language = language;
		this.segment = segment;
	}

}
