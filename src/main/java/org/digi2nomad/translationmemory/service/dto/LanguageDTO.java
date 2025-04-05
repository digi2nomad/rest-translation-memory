package org.digi2nomad.translationmemory.service.dto;

import org.digi2nomad.translationmemory.data.dao.Language;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * This class used to transfer data between the service layer, the web/controller layer.
 */
@Data
public class LanguageDTO {

	private long id;
	
	@NotNull
	@Size(max=3, message="LangCode must be less than 3 long")
	private String langcode;

	@NotNull
	@Size(max=20, message="Language must be less than 20 long")
	private String language;
	
	/**
	 * @param id
	 * @param langcode
	 * @param language
	 */
	public LanguageDTO(long id, String langcode, String language) {
		this.id = id;
		this.langcode = langcode;
		this.language = language;
	}
	
	/**
	 * The default is English
	 */
	public LanguageDTO() {
		this.id = 103L;
		this.langcode = "en";
		this.language = "English";
	}

	/**
	 * @param language
	 * @return
	 */
	public static Language to(LanguageDTO language) {
		if (language != null) {
			return new Language(language.getId(), 
					language.getLangcode(), 
					language.getLanguage());	
		}
		return null;
	}

}
