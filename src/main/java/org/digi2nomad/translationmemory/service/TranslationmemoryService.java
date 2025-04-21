package org.digi2nomad.translationmemory.service;

import java.util.List;

import org.digi2nomad.translationmemory.data.TranslationmemoryRepository;
import org.digi2nomad.translationmemory.data.dao.Language;
import org.digi2nomad.translationmemory.service.dto.TranslationProjectDTO;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryUnitDTO;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryVariantDTO;
import org.springframework.stereotype.Service;

/**
 * service layer for the translation memory
 */
@Service
public class TranslationmemoryService {
	
	private final TranslationmemoryRepository translationmemoryRepository;
	
	/**
	 * @param translationmemoryRepository
	 */
	public TranslationmemoryService(TranslationmemoryRepository translationmemoryRepository) {
		this.translationmemoryRepository = translationmemoryRepository;
	}
	
	/**
	 * @return
	 */
	public List<TranslationProjectDTO> getProjects() {
		List<TranslationProjectDTO> dtos 
			= TranslationProjectDTO.from(translationmemoryRepository.findAllProjects());
		return dtos;
	}

	/**
	 * @param projId
	 * @return
	 */
	public TranslationProjectDTO getProject(Long projId) {
		return TranslationProjectDTO.from(translationmemoryRepository.findProject(projId));
	}

	/**
	 * @param newProject
	 * @return
	 */
	public TranslationProjectDTO createProject(TranslationProjectDTO newProject) {
		return TranslationProjectDTO.from(
				translationmemoryRepository.addProject(TranslationProjectDTO.to(newProject)));
	}

	/**
	 * @param projId
	 * @param updateProject
	 * @return
	 */
	public TranslationProjectDTO updateProject(Long projId, TranslationProjectDTO updateProject) {
		updateProject.setId(projId);
		translationmemoryRepository.updateProject(TranslationProjectDTO.to(updateProject));
		return updateProject;
	}

	/**
	 * @param projId
	 */
	public String deleteProject(Long projId) {
		return translationmemoryRepository.deleteProject(projId);
	}

	/**
	 * @param projId
	 * @return
	 */
	public List<TranslationmemoryUnitDTO> getUnits(Long projId) {
		return TranslationmemoryUnitDTO.from(
				translationmemoryRepository.findAllTUs(projId));
	}

	/**
	 * @param projId
	 * @param unit
	 * @return
	 */
	public TranslationmemoryUnitDTO createUnit(Long projId, TranslationmemoryUnitDTO unit) {
		return TranslationmemoryUnitDTO.from(
				translationmemoryRepository.addTU(projId, TranslationmemoryUnitDTO.to(unit)));
	}

	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	public TranslationmemoryUnitDTO getUnit(Long projId, Long unitId) {
		return TranslationmemoryUnitDTO.from(
				translationmemoryRepository.findTU(projId, unitId));
	}

	/**
	 * @param projId
	 * @param unitId
	 */
	public String deleteUnit(Long projId, Long unitId) {
		return translationmemoryRepository.deleteTU(projId, unitId);
	}

	/**
	 * @param projId
	 * @param unitId
	 * @return
	 *//*
	public List<TranslationmemoryVariantDTO> getVariants(Long projId, Long unitId) {
		return TranslationmemoryVariantDTO.from(
				translationmemoryRepository.findAllTUVs(projId, unitId));
	}*/

	/**
	 * @param projId
	 * @param unitId
	 * @param variant
	 * @return
	 */
	public TranslationmemoryVariantDTO createVariant(
			Long projId, 
			Long unitId, 
			TranslationmemoryVariantDTO variant) {
		return TranslationmemoryVariantDTO.from( 
				translationmemoryRepository.addTUV(
						projId, 
						unitId, 
						TranslationmemoryVariantDTO.to(variant)));
	}

	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 * @return
	 */
	public TranslationmemoryVariantDTO getVariant(
			Long projId, 
			Long unitId, 
			Long variantId) {
		return TranslationmemoryVariantDTO.from( 
				translationmemoryRepository.findTUV(
						projId, 
						unitId, 
						variantId));
	}

	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 * @param variant
	 * @return
	 */
	public TranslationmemoryVariantDTO updateVariant(
			Long projId, 
			Long unitId, 
			Long variantId,
			TranslationmemoryVariantDTO variant) {
		variant.setId(variantId);
		return TranslationmemoryVariantDTO.from( 
				translationmemoryRepository.updateTUV(
						projId, 
						unitId, 
						TranslationmemoryVariantDTO.to(variant)));
	}

	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 */
	public String deleteVariant(Long projId, Long unitId, Long variantId) {
		return translationmemoryRepository.deleteTUV(projId, unitId, variantId);
	}
	
	/**
	 * @param projId
	 * @param ratio
	 * @param srclangcode
	 * @return
	 */
	public TranslationmemoryUnitDTO retrieveMatchedUnitAndItsVariants(Long projId, 
			Long matchRatioThreshold, 
			String srclangcode, 
			String segment) {
		Language sourceLanguage = translationmemoryRepository.findLanguage(srclangcode);
		if (sourceLanguage == null) {
			throw new IllegalArgumentException("Language code not found: " + srclangcode);
		}
		TranslationmemoryUnitDTO unit = TranslationmemoryUnitDTO.from( 
				translationmemoryRepository.findMatchedTU(projId,  
						matchRatioThreshold, sourceLanguage, segment));
		if (unit == null) {
			throw new IllegalArgumentException("Unit not found: " + segment);
		}
		unit.setVariants(
				TranslationmemoryVariantDTO.from(
						translationmemoryRepository.findAllTUVs(projId, unit.getId())));
		return unit;
	}

	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	public TranslationmemoryUnitDTO retrieveUnitAndItsVariants(Long projId, Long unitId) {
		TranslationmemoryUnitDTO unit = TranslationmemoryUnitDTO.from(
				translationmemoryRepository.findTU(projId, unitId));
		if (unit == null) {
			throw new IllegalArgumentException("Unit not found: " + unitId);
		}
		unit.setVariants(
				TranslationmemoryVariantDTO.from(
						translationmemoryRepository.findAllTUVs(projId, unitId)));
		return unit;
	}

}
