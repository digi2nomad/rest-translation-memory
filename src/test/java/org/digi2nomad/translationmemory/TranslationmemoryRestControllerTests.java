package org.digi2nomad.translationmemory;

import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;

import org.digi2nomad.translationmemory.service.TranslationmemoryService;
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
			.thenReturn(Arrays.asList(new TranslationProject(1L, "Project-1", "Project 1", Instant.now()), 
					new TranslationProject(2L, "Project-2", "Project 2", Instant.now())));
	}
	
	@Test
	public void testControllerGetProjects() {
		when(controller.getProjects())
			.thenReturn(Arrays.asList(new TranslationProject(1L, "Project-1", "Project 1", Instant.now()), 
					new TranslationProject(2L, "Project-2", "Project 2", Instant.now())));
	}
	
}
