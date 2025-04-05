package org.digi2nomad.translationmemory.data;

import org.digi2nomad.translationmemory.data.dao.Language;
import org.digi2nomad.translationmemory.data.dao.SegmentType;
import org.digi2nomad.translationmemory.data.dao.TranslationProject;
import org.digi2nomad.translationmemory.data.dao.TranslationmemoryUnit;
import org.digi2nomad.translationmemory.data.dao.TranslationmemoryVariant;

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
	TranslationProject findProject(Long projectId);
	
	/**
	 * @param project
	 * @return
	 */
	TranslationProject addProject(TranslationProject project);
	
	
	/**
	 * @param project
	 */
	void deleteProject(Long projectId);
	
	/**
	 * @param project
	 */
	void updateProject(TranslationProject project);
	
	//---------------------------------------------------------
	/**
	 * @param project
	 * @return
	 */
	Iterable<TranslationmemoryUnit> findAllTUs(Long projectId);
	
	/*
	 * @param projectId
	 * @param tuid
	 * @return
	 */
	TranslationmemoryUnit findTU(Long projectId, Long tuid);
	

	/**
	 * find a TranslationmemoryUnit matching the segment with higher than the match ratio threshold in the source language, 
	 * only the highest matched is returned.
	 * 
	 * @param projectId
	 * @param sourceLanguage
	 * @param segment
	 * @param matchRatioThreshold
	 * @return
	 */
	TranslationmemoryUnit findMatchedTU(Long projectId, Language sourceLanguage, String segment, int matchRatioThreshold);
	
	/**
	 * With default match ratio threshold
	 * 
	 * @param projectId
	 * @param sourceLanguage
	 * @param segment
	 * @return
	 */
	TranslationmemoryUnit findMatchedTU(Long projectId, Language sourceLanguage, String segment);


	/**
	 * @param projId
	 * @param tu
	 * @return
	 */
	TranslationmemoryUnit addTU(Long projId, TranslationmemoryUnit tu);
	
	/**
	 * @param tuId
	 */
	void deleteTU(Long tuId);
	
		
	//---------------------------------------------------------
	/**
	 * @param tuId
	 * @return
	 */
	Iterable<TranslationmemoryVariant> findAllTUVs(Long tuId);
	
	/**
	 * @param tuvId
	 * @return
	 */
	TranslationmemoryVariant findTUV(Long tuvId);

	/**
	 * @param tuId
	 * @param tuv
	 * @return
	 */
	TranslationmemoryVariant addTUV(Long tuId, TranslationmemoryVariant tuv);
	

	/**
	 * @param tuv
	 */
	void deleteTUV(TranslationmemoryVariant tuv);
	
	/**
	 * @param tuv
	 */
	void updateTUV(TranslationmemoryVariant tuv);


}
