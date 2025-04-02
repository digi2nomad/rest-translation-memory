package org.digi2nomad.translationmemory.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.digi2nomad.translationmemory.TranslationProject;
import org.digi2nomad.translationmemory.TranslationmemoryUnit;
import org.digi2nomad.translationmemory.TranslationmemoryUnitAndVariants;
import org.digi2nomad.translationmemory.TranslationmemoryUnitVariant;
import org.digi2nomad.translationmemory.service.TranslationmemoryService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * The REST controller for the translation memory.
 */
@RestController
public class TranslationmemoryRestController {
	
	private final TranslationmemoryService translationmemoryService;
	
	/**
	 * @param translationmemoryService
	 */
	public TranslationmemoryRestController(TranslationmemoryService translationmemoryService) {
		this.translationmemoryService = translationmemoryService;
	}
	
	/**
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
	/**
	 * @return
	 */
	@GetMapping("/projects")
	public @ResponseBody List<TranslationProject> getProjects() {
		return translationmemoryService.getProjects();
	}
	
	/**
	 * @param project
	 * @return
	 */
	@PostMapping("/projects")
	public @ResponseBody TranslationProject createProject(@Valid @RequestBody TranslationProject project) {
		return translationmemoryService.createProject(project);
	}
	
	/**
	 * @param projId
	 * @return
	 */
	@GetMapping("/projects/{id}")
	public @ResponseBody TranslationProject getProject(@PathVariable Long projId) {
		return translationmemoryService.getProject(projId);
	}
	
	/**
	 * @param projId
	 * @param project
	 * @return
	 */
	@PutMapping("/projects/{id}")
	public @ResponseBody TranslationProject updateProject(@PathVariable Long projId, @Valid @RequestBody TranslationProject project) {
		return translationmemoryService.updateProject(projId, project);
	}
	
	/**
	 * @param projId
	 */
	@DeleteMapping("/projects/{id}")
	public void deleteProject(@PathVariable Long projId) {
		translationmemoryService.deleteProject(projId);
	}
	
	/**
	 * @param projId
	 * @return
	 */
	@GetMapping("/projects/{id}/units")
	public @ResponseBody List<TranslationmemoryUnit> getUnits(@PathVariable Long projId) {
		return translationmemoryService.getUnits(projId);
	}
	
	/**
	 * @param projId
	 * @param unit
	 * @return
	 */
	@PostMapping("/projects/{id}/units")
	public @ResponseBody TranslationmemoryUnit createUnit(@PathVariable Long projId, @Valid @RequestBody TranslationmemoryUnit unit) {
		return translationmemoryService.createUnit(projId, unit);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	@GetMapping("/projects/{id}/units/{unitId}")
	public @ResponseBody TranslationmemoryUnit getUnit(@PathVariable Long projId, @PathVariable Long unitId) {
		return translationmemoryService.getUnit(projId, unitId);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param unit
	 * @return
	 *//*
	@PutMapping("/projects/{id}/units/{unitId}")	
	public @ResponseBody TranslationmemoryUnit updateUnit(@PathVariable Long projId, @PathVariable Long unitId, @Valid @RequestBody TranslationmemoryUnit unit) {
		return translationmemoryService.updateUnit(projId, unitId, unit);
	}*/
	
	/**
	 * @param projId
	 * @param unitId
	 */
	@DeleteMapping("/projects/{id}/units/{unitId}")
	public void deleteUnit(@PathVariable Long projId, @PathVariable Long unitId) {
		translationmemoryService.deleteUnit(projId, unitId);
	}	

	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	@GetMapping("/projects/{id}/units/{unitId}/variants")
	public @ResponseBody List<TranslationmemoryUnitVariant> getVariants(@PathVariable Long projId, @PathVariable Long unitId) {
		return translationmemoryService.getVariants(projId, unitId);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param variant
	 * @return
	 */
	@PostMapping("/projects/{id}/units/{unitId}/variants")
	public @ResponseBody TranslationmemoryUnitVariant createVariant(@PathVariable Long projId, @PathVariable Long unitId, @Valid @RequestBody TranslationmemoryUnitVariant variant) {
		return translationmemoryService.createVariant(projId, unitId, variant);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 * @return
	 */
	@GetMapping("/projects/{id}/units/{unitId}/variants/{variantId}")
	public @ResponseBody TranslationmemoryUnitVariant getVariant(@PathVariable Long projId, @PathVariable Long unitId, @PathVariable Long variantId) {
		return translationmemoryService.getVariant(projId, unitId, variantId);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 * @param variant
	 * @return
	 */
	@PutMapping("/projects/{id}/units/{unitId}/variants/{variantId}")
	public @ResponseBody TranslationmemoryUnitVariant updateVariant(@PathVariable Long projId, @PathVariable Long unitId, @PathVariable Long variantId, @Valid @RequestBody TranslationmemoryUnitVariant variant) {
		return translationmemoryService.updateVariant(projId, unitId, variantId, variant);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 */
	@DeleteMapping("/projects/{id}/units/{unitId}/variants/{variantId}")
	public void deleteVariant(@PathVariable Long projId, @PathVariable Long unitId, @PathVariable Long variantId) {
		translationmemoryService.deleteVariant(projId, unitId, variantId);
	}
	
	/**
	 * @param projId
	 * @param ratio
	 * @param srclang
	 * @return
	 */
	@GetMapping("/projects/{id}/matchedunit/{ratio}/language/{language}")
	public @ResponseBody TranslationmemoryUnitAndVariants retrieveMatchedUnitAndVariant(@PathVariable Long projId, @PathVariable Long ratio, @PathVariable String srclang) {
		return translationmemoryService.retrieveMatchedUnitAndVariants(projId, ratio, srclang);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	@GetMapping("/projects/{id}/unitandvariants/{unitId}")
	public @ResponseBody TranslationmemoryUnitAndVariants retrieveUnitAndVariants(@PathVariable Long projId, @PathVariable Long unitId) {
		return translationmemoryService.retrieveUnitAndVariants(projId, unitId);
	}
	
}
 