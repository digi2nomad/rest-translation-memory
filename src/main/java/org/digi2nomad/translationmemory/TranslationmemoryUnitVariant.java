package org.digi2nomad.translationmemory;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TranslationmemoryUnitVariant {
	
	private Long id;
	
	@NotNull
	private TranslationmemoryUnit tu;
	
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
	 * @param tu
	 * @param language
	 * @param segment
	 * @param createDate
	 * @param useDate
	 * @param useCount
	 * @param reviewed
	 */
	public TranslationmemoryUnitVariant(Long id, 
			TranslationmemoryUnit tu, 
			Language language, 
			String segment, 
			Instant createDate, 
			Instant useDate, 
			int useCount,
			boolean reviewed) {
		this.id = id;
		this.tu = tu;
		this.language = language;
		this.segment = segment;
		this.createDate = createDate;
		this.useDate = useDate;
		this.useCount = useCount;
		this.reviewed = reviewed; 
	}
	
	/**
	 * @param tu
	 * @param language
	 * @param segment
	 */
	public TranslationmemoryUnitVariant(
			TranslationmemoryUnit tu, 
			Language language, 
			String segment) {
		this.tu = tu;
		this.language = language;
		this.segment = segment;
	}

}
