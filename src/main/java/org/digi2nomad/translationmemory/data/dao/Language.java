package org.digi2nomad.translationmemory.data.dao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * The language of the translation memory unit's variants, 
 * which are translations of the same unit in different languages.
 */
@Data
public class Language {
	
	/**
	 * @param id
	 * @param langcode
	 * @param language
	 */
	public Language(long id, String langcode, String language) {
		this.id = id;
		this.langcode = langcode;
		this.language = language;
	}
	
	/**
	 * The default is English
	 */
	public Language() {
		this.id = 103L;
		this.langcode = "en";
		this.language = "English";
	}

	private long id;
	
	@NotNull
	@Size(max=3, message="LangCode must be less than 3 long")
	private String langcode;

	@NotNull
	@Size(max=20, message="Language must be less than 20 long")
	private String language;

}
