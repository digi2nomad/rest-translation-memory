package org.digi2nomad.translationmemory.service.dto;

import java.time.Instant;
import java.util.List;

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
	
	private TranslationmemoryUnitDTO tu;
	
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

	/**
	 * @param tuvs
	 * @return
	 */
	public static List<TranslationmemoryVariantDTO> from(Iterable<TranslationmemoryVariant> tuvs) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param tuv
	 * @return
	 */
	public static TranslationmemoryVariantDTO from(TranslationmemoryVariant tuv) {
		if (tuv != null) {
			TranslationmemoryVariantDTO ret = new TranslationmemoryVariantDTO();
			ret.setId(tuv.getId());
			//ret.setTuId(tuv.getTuId());
			ret.setLanguage(LanguageDTO.from(tuv.getLanguage()));
			ret.setSegment(tuv.getSegment());
			ret.setCreateDate(tuv.getCreateDate());
			ret.setUseDate(tuv.getUseDate());
			ret.setUseCount(tuv.getUseCount());
			ret.setReviewed(tuv.isReviewed());
			return ret;
		}
		return null;
	}
	

}
