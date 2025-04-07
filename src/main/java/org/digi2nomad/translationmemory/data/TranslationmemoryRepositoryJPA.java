package org.digi2nomad.translationmemory.data;

import org.digi2nomad.translationmemory.data.dao.Language;
import org.digi2nomad.translationmemory.data.dao.SegmentType;
import org.digi2nomad.translationmemory.data.dao.TranslationProject;
import org.digi2nomad.translationmemory.data.dao.TranslationmemoryUnit;
import org.digi2nomad.translationmemory.data.dao.TranslationmemoryVariant;

/**
 * Implementation of the TranslationmemoryRepository using JPA
 */
public class TranslationmemoryRepositoryJPA implements TranslationmemoryRepository {

	@Override
	public Iterable<Language> findAllLanguages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<SegmentType> findAllSegmentTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Language findLanguage(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Language findLanguage(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SegmentType findSegmentType(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<TranslationProject> findAllProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationProject findProject(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationProject addProject(TranslationProject project) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String deleteProject(Long projectId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void updateProject(TranslationProject project) {
		// TODO Auto-generated method stub
	}

	@Override
	public Iterable<TranslationmemoryUnit> findAllTUs(Long projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationmemoryUnit findTU(Long projectId, Long tuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationmemoryUnit addTU(Long projectId, TranslationmemoryUnit tu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationmemoryUnit findMatchedTU(Long projectId, 
			Long matchPercent, Language sourceLanguage, String segment) {
		//TODO: implement the method
		return null;
	}
	
	
	@Override
	public String deleteTU(Long projId, Long tuId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Iterable<TranslationmemoryVariant> findAllTUVs(Long projId, Long tuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationmemoryVariant addTUV(Long projId, Long tuId, TranslationmemoryVariant tuv) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TranslationmemoryVariant findTUV(Long projId, Long tuId, Long tuvId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteTUV(Long projId, Long tuId, Long tuvId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TranslationmemoryVariant updateTUV(Long projId, Long tuId, TranslationmemoryVariant tuv) {
		// TODO Auto-generated method stub
		return null;
	}


}
