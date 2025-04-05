package org.digi2nomad.translationmemory.service.dto;

import org.digi2nomad.translationmemory.data.dao.TranslationmemoryVariant;

import jakarta.validation.Valid;
import lombok.Data;

/**
 * This class is used to transfer data between the service layer, the web/controller layer.
 */
@Data
public class TranslationmemoryVariantDTO {
	
	/**
	 * @param variant
	 * @return
	 */
	public static TranslationmemoryVariant to(@Valid TranslationmemoryVariantDTO variant) {
		// TODO Auto-generated method stub
		return null;
	}

}
