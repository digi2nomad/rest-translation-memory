package org.digi2nomad.translationmemory.service.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.digi2nomad.translationmemory.data.dao.TranslationmemoryUnit;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The unit of translation memory and its variants,
 * which are translations of the same unit in different 
 * languages. This class is used to transfer data between
 * the service layer, the web/controller layer.
 */
@Data
public class TranslationmemoryUnitDTO {

	private Long id;
	
	@NotNull
	private Long projId;

	@NotNull	
	private SegmentTypeDTO segmentType;
	
	private Instant createDate;	

	
	/**
	 * @param tus
	 * @return
	 */
	public static List<TranslationmemoryUnitDTO> from(Iterable<TranslationmemoryUnit> tus) {
		if (tus != null) {
			ArrayList<TranslationmemoryUnitDTO> ret = new ArrayList<>();
			tus.iterator().forEachRemaining((tu) -> {
				TranslationmemoryUnitDTO dto = new TranslationmemoryUnitDTO();
				dto.setId(tu.getId());
				dto.setProjId(tu.getProjId());
				dto.setSegmentType(SegmentTypeDTO.from(tu.getSegmentType()));
				dto.setCreateDate(tu.getCreateDate());
			});
			return ret;
		}
		return null;
	}

	/**
	 * @param tu
	 * @return
	 */
	public static TranslationmemoryUnit to(TranslationmemoryUnitDTO tu) {
		if (tu != null) {
			TranslationmemoryUnit ret = new TranslationmemoryUnit();
			ret.setId(tu.getId());
			ret.setProjId(tu.getProjId());
			ret.setSegmentType(SegmentTypeDTO.to(tu.getSegmentType()));
			ret.setCreateDate(tu.getCreateDate());
			return ret;
		}
		return null;
	}

	/**
	 * @param tu
	 * @return
	 */
	public static TranslationmemoryUnitDTO from(TranslationmemoryUnit tu) {
		if (tu != null) {
			TranslationmemoryUnitDTO ret = new TranslationmemoryUnitDTO();
			ret.setId(tu.getId());
			ret.setProjId(tu.getProjId());
			ret.setSegmentType(SegmentTypeDTO.from(tu.getSegmentType()));
			ret.setCreateDate(tu.getCreateDate());
			return ret;
		}
		return null;
	}

}
