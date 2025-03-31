package org.digi2nomad.translationmemory.web.controller;

import java.util.List;

import org.digi2nomad.translationmemory.TranslationProject;
import org.digi2nomad.translationmemory.service.TranslationmemoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslationmemoryRestController {
	
	private final TranslationmemoryService translationmemoryService;
	
	public TranslationmemoryRestController(TranslationmemoryService translationmemoryService) {
		this.translationmemoryService = translationmemoryService;
	}
	
	@GetMapping("/projects")
	public @ResponseBody List<TranslationProject> getProjects() {
		return translationmemoryService.getProjects();
	}
	
	@PostMapping("/projects")
	public @ResponseBody TranslationProject createProject(@RequestBody TranslationProject project) {
		return translationmemoryService.createProject(project);
	}
	
	@GetMapping("/projects/{id}")
	public @ResponseBody TranslationProject getProject(@PathVariable Long id) {
		return translationmemoryService.getProject(id);
	}
	
	@PutMapping("/projects/{id}")
	public @ResponseBody TranslationProject updateProject(@PathVariable Long id, @RequestBody TranslationProject project) {
		return translationmemoryService.updateProject(id, project);
	}
	
	@DeleteMapping("/projects/{id}")
	public void deleteProject(@PathVariable Long id) {
		translationmemoryService.deleteProject(id);
	}

}
 