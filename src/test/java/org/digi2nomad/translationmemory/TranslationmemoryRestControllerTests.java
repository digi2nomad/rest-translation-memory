package org.digi2nomad.translationmemory;

import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;

import org.digi2nomad.translationmemory.service.TranslationmemoryService;
import org.digi2nomad.translationmemory.service.dto.TranslationProjectDTO;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryUnitDTO;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryVariantDTO;
import org.digi2nomad.translationmemory.web.controller.TranslationmemoryRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests the signitures of the REST controller.
 */
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
	
	@Test
	public void testControllerDeleteProject() {
		when(controller.deleteProject(1L))
			.thenReturn(new String("Project deleted"));
	}
	
	@Test
	public void testControllerGetUnits() {
		when(controller.getUnits(1L))
			.thenReturn(Arrays.asList(new TranslationmemoryUnitDTO(), 
					new TranslationmemoryUnitDTO()));
	}
	
	@Test
	public void testControllerGetUnit() {
		when(controller.getUnit(1L, 1L))
			.thenReturn(new TranslationmemoryUnitDTO());
	}
	
	@Test
	public void testControllerCreateUnit() {
		when(controller.createUnit(1L, new TranslationmemoryUnitDTO()))
			.thenReturn(new TranslationmemoryUnitDTO());
	}
	
	/*
	 * @Test public void testControllerUpdateUnit() { when(controller.updateUnit(1L,
	 * 1L, new TranslationmemoryUnitDTO())) .thenReturn(new
	 * TranslationmemoryUnitDTO()); }
	 */
	
	@Test
	public void testControllerDeleteUnit() {
		when(controller.deleteUnit(1L, 1L))
			.thenReturn(new String("Unit deleted"));
	}
	
	@Test
	public void testControllerGetVariants() {
		when(controller.getVariants(1L, 1L))
			.thenReturn(Arrays.asList(new TranslationmemoryVariantDTO(), 
					new TranslationmemoryVariantDTO()));
	}
	
	@Test
	public void testControllerCreateVariant() {
		when(controller.createVariant(1L, 1L, new TranslationmemoryVariantDTO()))
			.thenReturn(new TranslationmemoryVariantDTO());
	}
	
	@Test
	public void testControllerGetVariant() {
		when(controller.getVariant(1L, 1L, 1L))
			.thenReturn(new TranslationmemoryVariantDTO());
	}
	
	@Test
	public void testControllerUpdateVariant() {
		when(controller.updateVariant(1L, 1L, 1L, new TranslationmemoryVariantDTO()))
			.thenReturn(new TranslationmemoryVariantDTO());
	}
	
	@Test
	public void testControllerDeleteVariant() {
		when(controller.deleteVariant(1L, 1L, 1L))
			.thenReturn(new String("Variant deleted"));
	}
	
	
	
}
