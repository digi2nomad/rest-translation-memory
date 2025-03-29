package org.digi2nomad.translationmemory.data;

import org.digi2nomad.translationmemory.Language;
import org.digi2nomad.translationmemory.SegmentType;
import org.digi2nomad.translationmemory.TranslationProject;
import org.digi2nomad.translationmemory.TranslationmemoryUnit;
import org.digi2nomad.translationmemory.TranslationmemoryUnitVariant;

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
	public void deleteProject(TranslationProject project) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateProject(TranslationProject project) {
		// TODO Auto-generated method stub
	}

	@Override
	public Iterable<TranslationmemoryUnit> findAllTUs(TranslationProject project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationmemoryUnit findTU(Long tuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationmemoryUnit addTU(TranslationmemoryUnit tu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationmemoryUnit findMatchedTU(TranslationProject project, 
			Language sourceLanguage, String segment, int matchPercent) {
		//TODO: implement the method
		return null;
	}
	
	@Override
	public TranslationmemoryUnit findMatchedTU(TranslationProject project, 
			Language sourceLanguage, String segment) {
		//TODO: implement the method
		return null;
	}
	
	
	@Override
	public void deleteTU(TranslationmemoryUnit tu) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Iterable<TranslationmemoryUnitVariant> findAllTUVs(TranslationmemoryUnit tu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TranslationmemoryUnitVariant addTUV(TranslationmemoryUnitVariant tuv) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TranslationmemoryUnitVariant findTUV(Long tuvId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTUV(TranslationmemoryUnitVariant tuv) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateTUV(TranslationmemoryUnitVariant tuv) {
		// TODO Auto-generated method stub
	}

}
