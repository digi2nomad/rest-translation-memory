package org.digi2nomad.translationmemory.data;

import org.digi2nomad.translationmemory.Language;
import org.digi2nomad.translationmemory.SegmentType;
import org.digi2nomad.translationmemory.TranslationProject;
import org.digi2nomad.translationmemory.TranslationmemoryUnit;
import org.digi2nomad.translationmemory.TranslationmemoryUnitVariant;

/**
 * Interface for the TranslationmemoryRepository that provides access to the translation memory data in database 
 */
public interface TranslationmemoryRepository {
	
	
	// default match ratio threshold for fuzzy search
	static final int MATCH_RATIO_THRESHOLD = 80;

	/**
	 * @return
	 */
	Iterable<Language> findAllLanguages();
	
	/**
	 * @param id
	 * @return
	 */
	Language findLanguage(Long id);

	/**
	 * @return
	 */
	Iterable<SegmentType> findAllSegmentTypes();
	
	/**
	 * @param id
	 * @return
	 */
	SegmentType findSegmentType(Long id);
	
	//---------------------------------------------------------
	/**
	 * @return
	 */
	Iterable<TranslationProject> findAllProjects();
	
	/**
	 * @param id
	 * @return
	 */
	TranslationProject findProject(Long id);
	
	/**
	 * @param project
	 * @return
	 */
	TranslationProject addProject(TranslationProject project);
	
	
	/**
	 * @param project
	 */
	void deleteProject(TranslationProject project);
	
	/**
	 * @param project
	 */
	void updateProject(TranslationProject project);
	
	//---------------------------------------------------------
	/**
	 * @param project
	 * @return
	 */
	Iterable<TranslationmemoryUnit> findAllTUs(TranslationProject project);
	
	/**
	 * @param tuId
	 * @return
	 */
	TranslationmemoryUnit findTU(Long tuid);
	

	/**
	 * find a TranslationmemoryUnit matching the segment with higher than the match ratio threshold in the source language, 
	 * only the highest matched is returned.
	 * 
	 * @param project
	 * @param sourceLanguage
	 * @param segment
	 * @param matchRatioThreshold
	 * @return
	 */
	TranslationmemoryUnit findMatchedTU(TranslationProject project, Language sourceLanguage, String segment, int matchRatioThreshold);
	
	/**
	 * With default match ratio threshold
	 * 
	 * @param project
	 * @param sourceLanguage
	 * @param segment
	 * @return
	 */
	TranslationmemoryUnit findMatchedTU(TranslationProject project, Language sourceLanguage, String segment);


	/**
	 * 
	 * @param tu
	 * @return
	 */
	TranslationmemoryUnit addTU(TranslationmemoryUnit tu);
	
	/**
	 * @param tu
	 */
	void deleteTU(TranslationmemoryUnit tu);
	
		
	//---------------------------------------------------------
	/**
	 * @param tu
	 * @return
	 */
	Iterable<TranslationmemoryUnitVariant> findAllTUVs(TranslationmemoryUnit tu);
	
	/**
	 * @param tuvId
	 * @return
	 */
	TranslationmemoryUnitVariant findTUV(Long tuvId);

	/**
	 * @param tu
	 * @param tuv
	 * @return
	 */
	TranslationmemoryUnitVariant addTUV(TranslationmemoryUnitVariant tuv);
	

	/**
	 * @param tuv
	 */
	void deleteTUV(TranslationmemoryUnitVariant tuv);
	
	/**
	 * @param tuv
	 */
	void updateTUV(TranslationmemoryUnitVariant tuv);


}
