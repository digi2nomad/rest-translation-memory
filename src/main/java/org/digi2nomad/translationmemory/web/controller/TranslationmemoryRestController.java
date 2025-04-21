package org.digi2nomad.translationmemory.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.digi2nomad.translationmemory.service.TranslationmemoryService;
import org.digi2nomad.translationmemory.service.dto.TranslationProjectDTO;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryUnitDTO;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryVariantDTO;
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
	public @ResponseBody List<TranslationProjectDTO> getProjects() {
		return translationmemoryService.getProjects();
	}
	
	/**
	 * @param project
	 * @return
	 */
	@PostMapping("/projects")
	public @ResponseBody TranslationProjectDTO createProject(
			@Valid @RequestBody TranslationProjectDTO project) {
		return translationmemoryService.createProject(project);
	}
	
	/**
	 * @param projId
	 * @return
	 */
	@GetMapping("/projects/{id}")
	public @ResponseBody TranslationProjectDTO getProject(@PathVariable("id") Long projId) {
		return translationmemoryService.getProject(projId);
	}
	
	/**
	 * @param projId
	 * @param project
	 * @return
	 */
	@PutMapping("/projects/{id}")
	public @ResponseBody TranslationProjectDTO updateProject(@PathVariable("id") Long projId, 
			@Valid @RequestBody TranslationProjectDTO project) {
		return translationmemoryService.updateProject(projId, project);
	}
	
	/**
	 * @param projId
	 */
	@DeleteMapping("/projects/{id}")
	public @ResponseBody String deleteProject(@PathVariable("id") Long projId) {
		return translationmemoryService.deleteProject(projId);
	}
	
	/**
	 * @param projId
	 * @return
	 */
	@GetMapping("/projects/{id}/units")
	public @ResponseBody List<TranslationmemoryUnitDTO> getUnits(@PathVariable("id") Long projId) {
		return translationmemoryService.getUnits(projId);
	}
	
	/**
	 * @param projId
	 * @param unit
	 * @return
	 */
	@PostMapping("/projects/{id}/units")
	public @ResponseBody TranslationmemoryUnitDTO createUnit(@PathVariable("id") Long projId, 
			@Valid @RequestBody TranslationmemoryUnitDTO unit) {
		return translationmemoryService.createUnit(projId, unit);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	@GetMapping("/projects/{id}/units/{uId}")
	public @ResponseBody TranslationmemoryUnitDTO getUnit(@PathVariable("id") Long projId, 
			@PathVariable("uId") Long unitId) {
		return translationmemoryService.getUnit(projId, unitId);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param unit
	 * @return
	 *//*
	@PutMapping("/projects/{id}/units/{unitId}")	
	public @ResponseBody TranslationmemoryUnitDTO updateUnit(@PathVariable Long projId, 
		@PathVariable Long unitId, @Valid @RequestBody TranslationmemoryUnitDTO unit) {
		return TranslationmemoryUnitDTO.from(translationmemoryService.updateUnit(projId, unitId, unit));
	}*/
	
	/**
	 * @param projId
	 * @param unitId
	 */
	@DeleteMapping("/projects/{id}/units/{uId}")
	public @ResponseBody String deleteUnit(@PathVariable("id") Long projId, @PathVariable("uId") Long unitId) {
		return translationmemoryService.deleteUnit(projId, unitId);
	}	

	/**
	 * @param projId
	 * @param unitId
	 * @return
	 */
	@GetMapping("/projects/{id}/units/{uId}/variants")
	public @ResponseBody TranslationmemoryUnitDTO getVariants(@PathVariable("id") Long projId, 
			@PathVariable("uId") Long unitId) {
		return translationmemoryService.retrieveUnitAndItsVariants(projId, unitId);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param variant
	 * @return
	 */
	@PostMapping("/projects/{id}/units/{uId}/variants")
	public @ResponseBody TranslationmemoryVariantDTO createVariant(@PathVariable("id") Long projId, 
			@PathVariable("uId") Long unitId, @Valid @RequestBody TranslationmemoryVariantDTO variant) {
		return translationmemoryService.createVariant(projId, unitId, 
				variant);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 * @return
	 */
	@GetMapping("/projects/{id}/units/{uId}/variants/{uvId}")
	public @ResponseBody TranslationmemoryVariantDTO getVariant(@PathVariable("id") Long projId, 
			@PathVariable("uId") Long unitId, @PathVariable("uvId") Long variantId) {
		return translationmemoryService.getVariant(projId, unitId, variantId);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 * @param variant
	 * @return
	 */
	@PutMapping("/projects/{id}/units/{uId}/variants/{uvId}")
	public @ResponseBody TranslationmemoryVariantDTO updateVariant(@PathVariable("id") Long projId, 
				@PathVariable("uId") Long unitId, 
				@PathVariable("uvId") Long variantId, 
				@Valid @RequestBody 
				TranslationmemoryVariantDTO variant) {
		return translationmemoryService.updateVariant(
				projId, 
				unitId, 
				variantId, 
				variant);
	}
	
	/**
	 * @param projId
	 * @param unitId
	 * @param variantId
	 */
	@DeleteMapping("/projects/{id}/units/{uId}/variants/{uvId}")
	public @ResponseBody String deleteVariant(@PathVariable("id") Long projId, 
			@PathVariable("uId") Long unitId, @PathVariable("uvId") Long variantId) {
		return translationmemoryService.deleteVariant(projId, unitId, variantId);
	}
	
	/**
	 * @param projId
	 * @param ratio
	 * @param srclang
	 * @return
	 */
	@PostMapping("/projects/{id}/matchedunit/{ratio}/language/{language}")
	public @ResponseBody TranslationmemoryUnitDTO retrieveMatchedUnitAndItsVariants(
			@PathVariable("id") Long projId, 
			@PathVariable("ratio") Long matchRatioThreshold, 
			@PathVariable("language") String srclang,
			@RequestBody String segment) {
		return translationmemoryService.retrieveMatchedUnitAndItsVariants(projId, matchRatioThreshold, srclang, segment);
	}
	
}
 