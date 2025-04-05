package org.digi2nomad.translationmemory.service.dto;

import java.util.List;

import org.digi2nomad.translationmemory.data.dao.TranslationmemoryUnit;

import lombok.Data;

/**
 * The unit of translation memory and its variants,
 * which are translations of the same unit in different 
 * languages. This class is used to transfer data between
 * the service layer, the web/controller layer.
 */
@Data
public class TranslationmemoryUnitDTO {
	
	
	/**
	 * @param allTUs
	 * @return
	 */
	public static List<TranslationmemoryUnitDTO> from(Iterable<TranslationmemoryUnit> allTUs) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param unit
	 * @return
	 */
	public static TranslationmemoryUnit to(TranslationmemoryUnitDTO unit) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param tu
	 * @return
	 */
	public static TranslationmemoryUnitDTO from(TranslationmemoryUnit tu) {
		// TODO Auto-generated method stub
		return null;
	}

}
