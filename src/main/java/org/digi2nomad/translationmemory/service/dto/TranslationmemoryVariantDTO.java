package org.digi2nomad.translationmemory.service.dto;

import java.time.Instant;

import org.digi2nomad.translationmemory.data.dao.TranslationmemoryVariant;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * This class is used to transfer data between the service layer, the web/controller layer.
 */
@Data
public class TranslationmemoryVariantDTO {

	private Long id;
	
	//@NotNull
	//private Long tuId;
	
	@NotNull
	private LanguageDTO language;

	@NotNull
	@Size(max=250, message="Segment must be less than 250 long")
	private String segment;
	
	private Instant createDate;
	
	private Instant useDate;
	
	private int useCount;
	
	private boolean reviewed;
	
	/**
	 * @param variant
	 * @return
	 */
	public static TranslationmemoryVariant to(@Valid TranslationmemoryVariantDTO variant) {
		if (variant != null) {
			TranslationmemoryVariant ret = new TranslationmemoryVariant();
			ret.setId(variant.getId());
			//ret.setTuId(variant.getTuId());
			ret.setLanguage(LanguageDTO.to(variant.getLanguage()));
			ret.setSegment(variant.getSegment());
			ret.setCreateDate(variant.getCreateDate());
			ret.setUseDate(variant.getUseDate());
			ret.setUseCount(variant.getUseCount());
			ret.setReviewed(variant.isReviewed());
			return ret;
		}
		return null;
	}
	

}
