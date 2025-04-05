package org.digi2nomad.translationmemory;

import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;

import org.digi2nomad.translationmemory.service.TranslationmemoryService;
import org.digi2nomad.translationmemory.service.dto.TranslationProjectDTO;
import org.digi2nomad.translationmemory.web.controller.TranslationmemoryRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TranslationmemoryRestController.class)
public class TranslationmemoryRestControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TranslationmemoryService service;
	
	@MockBean
	private TranslationmemoryRestController controller;
	

	@Test
	public void testServiceGetProjects() {
		when(service.getProjects())
			.thenReturn(Arrays.asList(new TranslationProjectDTO(1L, "Project-1", "Project 1", Instant.now()), 
					new TranslationProjectDTO(2L, "Project-2", "Project 2", Instant.now())));
	}
	
	@Test
	public void testControllerGetProjects() {
		when(controller.getProjects())
			.thenReturn(Arrays.asList(new TranslationProjectDTO(1L, "Project-1", "Project 1", Instant.now()), 
					new TranslationProjectDTO(2L, "Project-2", "Project 2", Instant.now())));
	}
	
	@Test
	public void testControllerGetProject() {
		when(controller.getProject(1L))
			.thenReturn(new TranslationProjectDTO(1L, "Project-1", "Project 1", Instant.now()));
	}
	
	@Test
	public void testControllerCreateProject() {
		when(controller.createProject(new TranslationProjectDTO(1L, "Project-1", "Project 1", Instant.now())))
			.thenReturn(new TranslationProjectDTO(1L, "Project-1", "Project 1", Instant.now()));
	}
	
	@Test
	public void testControllerUpdateProject() {
		when(controller.updateProject(1L, new TranslationProjectDTO(1L, "Project-1", "Project 1", Instant.now())))
			.thenReturn(new TranslationProjectDTO(1L, "Project-1", "Project 1", Instant.now()));
	}

	
}
