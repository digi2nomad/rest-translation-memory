package org.digi2nomad.translationmemory.service;

import java.util.List;

import org.digi2nomad.translationmemory.TranslationProject;
import org.digi2nomad.translationmemory.data.TranslationmemoryRepository;
import org.springframework.stereotype.Service;

@Service
public class TranslationmemoryService {
	
	private final TranslationmemoryRepository translationmemoryRepository;
	
	public TranslationmemoryService(TranslationmemoryRepository translationmemoryRepository) {
		this.translationmemoryRepository = translationmemoryRepository;
	}
	
	public List<TranslationProject> getProjects() {
		Iterable<TranslationProject> i = translationmemoryRepository.findAllProjects();
		return (List<TranslationProject>) i;
	}

	public TranslationProject getProject(Long id) {
		return translationmemoryRepository.findProject(id);
	}

	public TranslationProject createProject(TranslationProject project) {
		return translationmemoryRepository.addProject(project);
	}

	public TranslationProject updateProject(Long id, TranslationProject project) {
		project.setId(id);
		translationmemoryRepository.updateProject(project);
		return project;
	}

	public void deleteProject(Long id) {
		TranslationProject project = translationmemoryRepository.findProject(id);
		translationmemoryRepository.deleteProject(project);
	}

}
