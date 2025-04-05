package org.digi2nomad.translationmemory.service.dto;

import java.time.Instant;
import java.util.List;

import org.digi2nomad.translationmemory.data.dao.TranslationProject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * This class is used to transfer data between the service layer and the web/controller layer.
 */
@Data
public class TranslationProjectDTO {

	private Long id;
	
	@NotBlank
	@Size(max=50, message="Name must be less than 50 long")
	private String name;
	
	@NotBlank
	@Size(max=250, message="Description must be less than 250 long")
	private String description;
	
	private Instant createDate;	
	
	/**
	 * @param i
	 * @return
	 */
	public static List<TranslationProjectDTO> from(Iterable<TranslationProject> i) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param project
	 * @return
	 */
	public static TranslationProjectDTO from(TranslationProject project) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param newProject
	 * @return
	 */
	public static TranslationProject to(TranslationProjectDTO newProject) {
		// TODO Auto-generated method stub
		return null;
	}

	public TranslationProjectDTO(long l, String string, String string2, Instant now) {
		// TODO Auto-generated constructor stub
	}

	

}
