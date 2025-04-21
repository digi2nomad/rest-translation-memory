package org.digi2nomad.translationmemory.service.dto;

import java.time.Instant;
import java.util.ArrayList;
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
		if (i != null) {
			ArrayList<TranslationProjectDTO> ret = new ArrayList<>();
			i.iterator().forEachRemaining((p) -> {
				TranslationProjectDTO dto = new TranslationProjectDTO();
				dto.setId(p.getId());
				dto.setName(p.getName());
				dto.setDescription(p.getDescription());
				dto.setCreateDate(p.getCreateDate());
				ret.add(dto);
			});
			return ret;
		}
		return null;
	}

	/**
	 * @param project
	 * @return
	 */
	public static TranslationProjectDTO from(TranslationProject project) {
		TranslationProjectDTO ret = new TranslationProjectDTO();
		ret.setId(project.getId());
		ret.setName(project.getName());
		ret.setDescription(project.getDescription());
		ret.setCreateDate(project.getCreateDate());
		return ret;
	}

	/**
	 * @param project
	 * @return
	 */
	public static TranslationProject to(TranslationProjectDTO project) {
		TranslationProject ret = new TranslationProject();
		ret.setId(project.getId());
		ret.setName(project.getName());
		ret.setDescription(project.getDescription());
		ret.setCreateDate(project.getCreateDate());
		return ret;
	}

	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param createDate
	 */
	public TranslationProjectDTO(Long id, 
			String name, 
			String description, 
			Instant createDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createDate = createDate;
	}
	
	/**
	 * default constructor
	 */
	public TranslationProjectDTO() {
	}

}
