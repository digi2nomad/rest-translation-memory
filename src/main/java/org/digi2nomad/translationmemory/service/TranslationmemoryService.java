package org.digi2nomad.translationmemory.service;

import java.util.List;

import org.digi2nomad.translationmemory.TranslationProject;
import org.digi2nomad.translationmemory.TranslationmemoryUnit;
import org.digi2nomad.translationmemory.TranslationmemoryUnitAndVariants;
import org.digi2nomad.translationmemory.TranslationmemoryUnitVariant;
import org.digi2nomad.translationmemory.data.TranslationmemoryRepository;
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
	public List<TranslationProject> getProjects() {
		Iterable<TranslationProject> i = translationmemoryRepository.findAllProjects();
		return (List<TranslationProject>) i;
	}

	/**
	 * @param projId
	 * @return
	 */
	public TranslationProject getProject(Long projId) {
		return translationmemoryRepository.findProject(projId);
	}

	/**
	 * @param newProject
	 * @return
	 */
	public TranslationProject createProject(TranslationProject newProject) {
		return translationmemoryRepository.addProject(newProject);
	}

	/**
	 * @param projId
	 * @param updateProject
	 * @return
	 */
	public TranslationProject updateProject(Long projId, TranslationProject updateProject) {
		updateProject.setId(projId);
		translationmemoryRepository.updateProject(updateProject);
		return updateProject;
	}

	/**
	 * @param projId
	 */
	public void deleteProject(Long projId) {
		TranslationProject project = translationmemoryRepository.findProject(projId);
		translationmemoryRepository.deleteProject(project);
	}

	/**
	 * @param projId
	 * @return
	 */
	public List<TranslationmemoryUnit> getUnits(Long projId) {
		try {
			TranslationProject project = translationmemoryRepository.findProject(projId);
			return (List<TranslationmemoryUnit>)translationmemoryRepository.findAllTUs(project);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param projId
	 * @param unit
	 * @return
	 */
	public TranslationmemoryUnit createUnit(Long projId, TranslationmemoryUnit unit) {
		try {
			TranslationProject project = translationmemoryRepository.findProject(projId);
			unit.setTranslationProject(project);
			return translationmemoryRepository.addTU(unit);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	public TranslationmemoryUnit getUnit(Long projId, Long unitId) {
		try {
			@SuppressWarnings("unused")
			TranslationProject project = translationmemoryRepository.findProject(projId);
			TranslationmemoryUnit unit = translationmemoryRepository.findTU(unitId);
			//TODO: check unit.getTranslationProject().equals(project) and throw exception if not
			return unit;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param projId
	 * @param unitId
	 */
	public void deleteUnit(Long projId, Long unitId) {
		// TODO Auto-generated method stub
	}

	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	public List<TranslationmemoryUnitVariant> getVariants(Long projId, Long unitId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param projId
	 * @param unitId
	 * @param variant
	 * @return
	 */
	public TranslationmemoryUnitVariant createVariant(Long projId, Long unitId, TranslationmemoryUnitVariant variant) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 * @return
	 */
	public TranslationmemoryUnitVariant getVariant(Long projId, Long unitId, Long variantId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 * @param variant
	 * @return
	 */
	public TranslationmemoryUnitVariant updateVariant(Long projId, Long unitId, Long variantId,
			TranslationmemoryUnitVariant variant) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 */
	public void deleteVariant(Long projId, Long unitId, Long variantId) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param projId
	 * @param ratio
	 * @param srclang
	 * @return
	 */
	public TranslationmemoryUnitAndVariants retrieveMatchedUnitAndVariants(Long projId, Long ratio, String srclang) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	public TranslationmemoryUnitAndVariants retrieveUnitAndVariants(Long projId, Long unitId) {
		// TODO Auto-generated method stub
		return null;
	}

}
