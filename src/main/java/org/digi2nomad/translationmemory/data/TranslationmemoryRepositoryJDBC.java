package org.digi2nomad.translationmemory.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Iterator;
import java.util.UUID;

import org.digi2nomad.translationmemory.Language;
import org.digi2nomad.translationmemory.SegmentType;
import org.digi2nomad.translationmemory.TranslationProject;
import org.digi2nomad.translationmemory.TranslationmemoryUnit;
import org.digi2nomad.translationmemory.TranslationmemoryUnitVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Implementation of the TranslationmemoryRepository interface using JDBC.
 */
@Repository	
public class TranslationmemoryRepositoryJDBC implements TranslationmemoryRepository {

	private JdbcTemplate jdbcTemplate;
	
	private static final String SQL_FIND_ALL_LANGUAGES 
		= "select id, langcode, language from Language";
	private static final String SQL_FIND_ALL_SEGMENTTYPES 
		= "select id, type, description from SegmentType";
	
	private static final String SQL_FIND_ALL_PROJECTS 
		= "select id, name, description, createDate from TranslationProject";
	private static final String SQL_FIND_PROJECT 
		= "select id, name, description, createDate from TranslationProject where id = ?";
	private static final String SQL_ADD_PROJECT 
		= "insert into TranslationProject (id, name, description, createDate) values (?, ?, ?, ?)";
	private static final String SQL_DELETE_PROJECT 
		= "delete from TranslationProject where id = ?";
	private static final String SQL_UPDATE_PROJECT 
		= "update TranslationProject set name = ?, description = ? where id = ?";
	
	private static final String SQL_FIND_ALL_TUS 
		= "select id, projId, segmentType, createDate from TranslationUnit where projId = ?";
	private static final String SQL_FIND_TU 
		= "select id, projId, segmentType, createDate from TranslationUnit where id = ?";
	private static final String SQL_ADD_TU 
		= "insert into TranslationUnit (id, projId, segmentType, createDate) values (?, ?, ?, ?)";
	private static final String SQL_DELETE_TU 
		= "delete from TranslationUnit where id = ?";
	
	private static final String SQL_FIND_ALL_TUVS 
		= "select id, tuid, language, segment, createDate, useDate, useCount, reviewed from TranslationUnitVariant where tuid = ?";
	private static final String SQL_FIND_TUV 
		= "select id, tuid, language, segment, createDate, useDate, useCount, reviewed from TranslationUnitVariant where id = ?";
	private static final String SQL_ADD_TUV 
		= "insert into TranslationUnitVariant (id, tuid, language, segment, createDate, useDate, useCount, reviewed) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_TUV 
		= "delete from TranslationUnitVariant where id = ?";
	private static final String SQL_UPDATE_TUV 
		= "update TranslationUnitVariant set segment = ?, useDate = ?, useCount = ?, reviewed = ? where id = ?";

	
	// Caching the languages, segmentTypes 
	private static Iterable<Language> languages;
	private static Iterable<SegmentType> segmentTypes;
	
	private static final Logger log = LoggerFactory.getLogger(TranslationmemoryRepositoryJDBC.class);
	
	/**
	 * @param jdbcTemplate
	 */
	public TranslationmemoryRepositoryJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//---------------------------------------  Language  -----------------------------------------
	/**
	 * @return
	 */
	@Override	
	public Iterable<Language> findAllLanguages() {
		if (languages == null) {
			languages = jdbcTemplate.query(SQL_FIND_ALL_LANGUAGES, this::mapRowToLanguage);
		}
		return jdbcTemplate.query(SQL_FIND_ALL_LANGUAGES, this::mapRowToLanguage);
	}
	
	@Override
	public Language findLanguage(Long id) {
		if (languages == null) {
			languages = jdbcTemplate.query(SQL_FIND_ALL_LANGUAGES, this::mapRowToLanguage);
		}
		Iterator<Language> i = languages.iterator();
		while (i.hasNext()) {
			Language l = (Language) i.next();
			if (l.getId() == id) {
				return l;
			}
		}		
		return null;
	}
	
	//---------------------------------------  Segment Type  ----------------------------------------- 
	/**
	 * find all SegmentTypes.
	 * 
	 * @return
	 */
	@Override	
	public Iterable<SegmentType> findAllSegmentTypes() {
		if (segmentTypes == null) {
			segmentTypes = jdbcTemplate.query(SQL_FIND_ALL_SEGMENTTYPES, this::mapRowToSegmentType);
		}
		return jdbcTemplate.query(SQL_FIND_ALL_SEGMENTTYPES, this::mapRowToSegmentType);
	}
	
	/**
	 * find a SegmentType by its id.
	 */
	@Override
	public SegmentType findSegmentType(Long id) {
		if (segmentTypes == null) {
			segmentTypes = jdbcTemplate.query(SQL_FIND_ALL_SEGMENTTYPES, this::mapRowToSegmentType);
		}
		Iterator<SegmentType> i = segmentTypes.iterator();
		while (i.hasNext()) {
			SegmentType st = (SegmentType) i.next();
			if (st.getId() == id) {
				return st;
			}
		}		
		return null;	
	}
	
	//---------------------------------------  Project  ------------------------------------
	/**
	 * find all TranslationProjects.
	 * 
	 * @return
	 */
	@Override
	public Iterable<TranslationProject> findAllProjects() {
		return jdbcTemplate.query(SQL_FIND_ALL_PROJECTS, this::mapRowToProject);
	}
	
	/**
	 * find a TranslationProject by its id.
	 */
	@Override
	public TranslationProject findProject(Long id) {
		try {
			//TODO: check if the project exists in cache
			return jdbcTemplate.queryForObject(SQL_FIND_PROJECT, this::mapRowToProject, id);
		} catch (Exception e) {
			log.info("Project not found: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * add a TranslationProject to the database.
	 */
	@Override
	public TranslationProject addProject(TranslationProject project) {
		if (project == null) {
			throw new IllegalArgumentException("Project cannot be null");
		}
		if (project.getId() == null) {
			project.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		}
		if (project.getCreateDate() == null) {
			project.setCreateDate(Instant.now());
		}
		jdbcTemplate.update(
				SQL_ADD_PROJECT,
				project.getId(),
				project.getName(),
				project.getDescription(),
				project.getCreateDate());
		return project;
	}
	
	/**
	 * delete a TranslationProject from the database.
	 */
	@Override 
	@Transactional
	public void deleteProject(TranslationProject project) {
		if (project == null || project.getId() == null) {
			throw new IllegalArgumentException("Project cannot be null");
		}
		Iterable<TranslationmemoryUnit> tus = findAllTUs(project);
		for (TranslationmemoryUnit tu : tus) {
			Iterable<TranslationmemoryUnitVariant> tuvs = findAllTUVs(tu);
			tuvs.forEach(tuv -> deleteTUV(tuv));
			deleteTU(tu);
		}
		jdbcTemplate.update(SQL_DELETE_PROJECT, project.getId());
	}
	
	/**
	 * update a TranslationProject in the database.
	 */
	@Override
	public void updateProject(TranslationProject project) {
		if (project == null || project.getId() == null) {
			throw new IllegalArgumentException("Project cannot be null");
		}
		jdbcTemplate.update(
				SQL_UPDATE_PROJECT,
				project.getName(),
				project.getDescription(),
				project.getId());
	}


    //---------------------------------------  TU  ----------------------------------------- 
	/**
	 * find all TranslationmemoryUnits in a TranslationProject.
	 */
	@Override
	public Iterable<TranslationmemoryUnit> findAllTUs(TranslationProject project) {
		if (project != null) {
			return jdbcTemplate.query(SQL_FIND_ALL_TUS, this::mapRowToTU, project.getId());
		}
		return null;
	}
	
	/**
	 * To find a TranslationmemoryUnit by its id.
	 *
	 * @param tuId
	 * @return
	 */
	@Override
	public TranslationmemoryUnit findTU(Long tuId) {
		try {
			//TODO: check if the TU exists in cache
			return jdbcTemplate.queryForObject(SQL_FIND_TU, this::mapRowToTU, tuId);
		} catch (Exception e) {
			log.info("TU not found: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * To add a TranslationmemoryUnit to a project in the database.
	 *
	 * @param tu
	 * @return
	 */
	@Override
	public TranslationmemoryUnit addTU(TranslationmemoryUnit tu) {
		TranslationProject project = tu.getTranslationProject();
		if (project == null || project.getId() == null) {
			throw new IllegalArgumentException("TranslationProject cannot be null");
		}
		if (tu.getId() == null) {
			tu.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		}
		if (tu.getCreateDate() == null) {
			tu.setCreateDate(Instant.now());
		}
		jdbcTemplate.update( 
				SQL_ADD_TU,
				tu.getId(),
				project.getId(),
				tu.getSegmentType().getId(),	
				tu.getCreateDate());
		return tu;	
	}
	
	/**
	 * only the highest matched is returned.
	 */
	@Override
	public TranslationmemoryUnit findMatchedTU(TranslationProject project, 
			Language sourceLanguage, String segment, int matchRatioThreshold) {
		Iterator<TranslationmemoryUnit> i = findAllTUs(project).iterator();
		TranslationmemoryUnit highestMatchedUnit = null;
		int highestMatchedRatio = 0;
		while (i.hasNext()) {
			TranslationmemoryUnit tu = i.next();
			Iterator<TranslationmemoryUnitVariant> j = findAllTUVs(tu).iterator();
			while (j.hasNext()) {
				TranslationmemoryUnitVariant tuv = j.next();
				if (tuv.getLanguage().equals(sourceLanguage)) {
					int ratio = FuzzySearch.ratio(tuv.getSegment(), segment);
					if (ratio >= matchRatioThreshold && ratio > highestMatchedRatio) {
						highestMatchedUnit = tu;
						highestMatchedRatio = ratio;
					}
				}
			}
		}
		return highestMatchedUnit;
	}
	
	/**
	 * With default match ratio threshold
	 */
	public TranslationmemoryUnit findMatchedTU(TranslationProject project, 
			Language sourceLanguage, String segment) {
		return findMatchedTU(project, sourceLanguage, segment, MATCH_RATIO_THRESHOLD);
	}
	
	/**
	 *
	 */
	@Override
	@Transactional	
	public void deleteTU(TranslationmemoryUnit tu) {
		Iterable<TranslationmemoryUnitVariant> tuvs = findAllTUVs(tu);
		tuvs.forEach(tuv -> deleteTUV(tuv));
		jdbcTemplate.update(SQL_DELETE_TU, tu.getId());
	}
	
	//---------------------------------------  TUV  -----------------------------------------
	/**
	 * find all TranslationmemoryUnitVariants in a TranslationmemoryUnit.
	 * 
	 * @param tu
	 * @return
	 */
	@Override
	public Iterable<TranslationmemoryUnitVariant> findAllTUVs(TranslationmemoryUnit tu) {
		if (tu != null) {
			return jdbcTemplate.query(SQL_FIND_ALL_TUVS, this::mapRowToTUV, tu.getId());
		}
		return null;
	}
	
	/**
	 * To find a TranslationmemoryUnitVariant by its id.
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TranslationmemoryUnitVariant findTUV(Long id) {
		try {
			return jdbcTemplate.queryForObject(SQL_FIND_TUV, this::mapRowToTUV, id);
		} catch (Exception e) {
			log.info("TUV not found: " + e.getMessage());
			return null;	
		}
	}
	
	/**
	 * To add a TranslationmemoryUnitVariant to a TranslationmemoryUnit in the database. 
	 * 
	 * @param tuv
	 * @return
	 */
	@Override
	public TranslationmemoryUnitVariant addTUV(TranslationmemoryUnitVariant tuv) {
		TranslationmemoryUnit tu = tuv.getTu();
		if (tu == null || tu.getId() == null) {
			throw new IllegalArgumentException("TranslationmemoryUnit cannot be null");
		}
		if (tuv.getId() == null) {
			tuv.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		}
		if (tuv.getCreateDate() == null) {
			tuv.setCreateDate(Instant.now());
		}
		Iterable<TranslationmemoryUnitVariant> tuvs = findAllTUVs(tu);
		for (TranslationmemoryUnitVariant t : tuvs) {
			if (t.getLanguage().equals(tuv.getLanguage())) {
				throw new IllegalArgumentException("TranslationmemoryUnitVariant's language already exists");
			}
		}
		jdbcTemplate.update(
				SQL_ADD_TUV,
				tuv.getId(),
				tu.getId(),
				tuv.getLanguage().getId(),
				tuv.getSegment(),
				tuv.getCreateDate(),
				tuv.getUseDate(),
				tuv.getUseCount(),
				tuv.isReviewed());	
		return tuv;	
	}
	
	/**
	 *
	 */
	@Override
	public void deleteTUV(TranslationmemoryUnitVariant tuv) {
		jdbcTemplate.update(SQL_DELETE_TUV, tuv.getId());
	}
	
	/**
	 * update a TranslationmemoryUnitVariant in the database.
	 */
	@Override
	public void updateTUV(TranslationmemoryUnitVariant tuv) {
		jdbcTemplate.update(
				SQL_UPDATE_TUV,
				tuv.getSegment(),
				tuv.getUseDate(),
				tuv.getUseCount(),
				tuv.isReviewed(),
				tuv.getId());
	}
	
	
	//------------------------------------- ROW MAPPERS ---------------------------------------
	
	/**
	 * @param row
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	private TranslationProject mapRowToProject(ResultSet row, int rowNum) throws SQLException {
		return new TranslationProject(
				row.getLong("id"), 
				row.getString("name"), 
				row.getString("description"), 
				row.getTimestamp("createDate").toInstant());
	}
	
	
	/**
	 * 
	 * @param row
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	private TranslationmemoryUnit mapRowToTU(ResultSet row, int rowNum) throws SQLException {
		return new TranslationmemoryUnit(
				row.getLong("id"),
				findProject(row.getLong("projId")),
				findSegmentType(row.getLong("segmentType")), 
				row.getTimestamp("createDate").toInstant());
	}
	
	/**
	 * @param row
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	private TranslationmemoryUnitVariant mapRowToTUV(ResultSet row, int rowNum) throws SQLException {
		return new TranslationmemoryUnitVariant(
				row.getLong("id"), 
				findTU(row.getLong("tuid")), 
				findLanguage(row.getLong("language")), 
				row.getString("segment"), 
				row.getTimestamp("createDate").toInstant(), 
				row.getTimestamp("useDate") == null ? null : row.getTimestamp("useDate").toInstant(), 
				row.getInt("useCount"),
				row.getBoolean("reviewed")); 
	}
	
	/**
	 * @param row
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	private Language mapRowToLanguage(ResultSet row, int rowNum) throws SQLException {
		return new Language(
				row.getLong("id"), 
				row.getString("langcode"), 
				row.getString("language"));
	}

	/**
	 * @param row
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	private SegmentType mapRowToSegmentType(ResultSet row, int rowNum) throws SQLException {
		return new SegmentType(
				row.getLong("id"), 
				row.getString("type"), 
				row.getString("description"));
	}


}
