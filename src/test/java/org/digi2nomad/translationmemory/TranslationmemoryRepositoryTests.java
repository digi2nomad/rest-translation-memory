package org.digi2nomad.translationmemory;

import static org.assertj.core.api.Assertions.assertThat;

import org.digi2nomad.translationmemory.data.TranslationmemoryRepository;
import org.digi2nomad.translationmemory.data.dao.Language;
import org.digi2nomad.translationmemory.data.dao.SegmentType;
import org.digi2nomad.translationmemory.data.dao.TranslationProject;
import org.digi2nomad.translationmemory.data.dao.TranslationmemoryUnit;
import org.digi2nomad.translationmemory.data.dao.TranslationmemoryVariant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TranslationmemoryRepositoryTests {
	
	@Autowired
	TranslationmemoryRepository repo;

	@Test
	void contextLoads() {
	}
	
	@Test
	void testCreateProject() {
		TranslationProject project1 = new TranslationProject("test_create_project", "description");
		project1 = repo.addProject(project1);
		assertThat(project1).isNotNull();
		assertThat(project1.getId()).isNotNull();
		assertThat(project1.getName()).isNotNull();
		assertThat(project1.getDescription()).isNotNull();
		assertThat(project1.getCreateDate()).isNotNull();
		System.out.println("Created project: " + project1);
		
		TranslationProject project2 = repo.findProject(project1.getId());
		assertThat(project2).isNotNull();
		assertThat(project2.getId()).isEqualTo(project1.getId());
		assertThat(project2.getName()).isEqualTo(project1.getName());
		assertThat(project2.getDescription()).isEqualTo(project1.getDescription());
		//assertThat(project2.getCreateDate()).isEqualTo(project1.getCreateDate()); loss of precision
		System.out.println("Found project: " + project2);
	}
	
	@Test
	void testCreateProjectsWithTUsAndTuvs() {
		//
		// create project 1
		//
		TranslationProject proj1 = new TranslationProject("test_create_tus_tuvs_with_proj1", "description");
		proj1 = repo.addProject(proj1);
		assertThat(proj1).isNotNull();
		System.out.println("Created project: " + proj1);

		TranslationmemoryUnit tu11 = new TranslationmemoryUnit(proj1.getId(), new SegmentType());
		tu11 = repo.addTU(proj1.getId(), tu11);
		assertThat(tu11).isNotNull();
		System.out.println("Created TU: " + tu11);
		
		TranslationmemoryUnit tu12 = repo.findTU(proj1.getId(), tu11.getId());
		assertThat(tu12).isNotNull();
		System.out.println("Found TU: " + tu12);
		
		TranslationmemoryVariant tuv11 = new TranslationmemoryVariant(tu11.getId(), new Language(), "segment");
		tuv11 = repo.addTUV(proj1.getId(), tu11.getId(), tuv11);
		assertThat(tuv11).isNotNull();
		System.out.println("Created TUV: " + tuv11);
		
		//
		// create project 2
		//
		TranslationProject proj2 = new TranslationProject("test_create_tus_tuvs_with_proj2", "description");
		proj2 = repo.addProject(proj2);
		assertThat(proj2).isNotNull();
		System.out.println("Created project: " + proj2);

		TranslationmemoryUnit tu21 = new TranslationmemoryUnit(proj2.getId(), new SegmentType());
		tu21 = repo.addTU(proj2.getId(), tu21);
		assertThat(tu21).isNotNull();
		System.out.println("Created TU: " + tu21);
		
		TranslationmemoryUnit tu22 = repo.findTU(proj2.getId(), tu21.getId());
		assertThat(tu22).isNotNull();
		System.out.println("Found TU: " + tu22);
		
		TranslationmemoryVariant tuv21 = new TranslationmemoryVariant(tu21.getId(), new Language(), "segment");
		tuv21 = repo.addTUV(proj2.getId(), tu21.getId(), tuv21);
		assertThat(tuv21).isNotNull();
		System.out.println("Created TUV: " + tuv21);
		
		//
		// traverse all Projects, TUs and TUVs
		//
		Iterable<TranslationProject> projects = repo.findAllProjects();
		projects.forEach(System.out::println); 
		for (TranslationProject project : projects) {
			assertThat(project).isNotNull();
			Iterable<TranslationmemoryUnit> tus = repo.findAllTUs(project.getId());
			for (TranslationmemoryUnit tu : tus) {
				System.out.println("TU: " + tu);
				Iterable<TranslationmemoryVariant> tuvs = repo.findAllTUVs(project.getId(), tu.getId());
				tuvs.forEach(tuv -> System.out.println("TUV: " + tuv));
			}
		}
		
	}
	
	@Test
	void testAddTUVwithDuplicatedLanguage() {
		TranslationProject project1 = new TranslationProject("test_add_tuv_with_deplicated_language", "description");
		project1 = repo.addProject(project1);
		assertThat(project1).isNotNull();
		System.out.println("Created project: " + project1);
		
		TranslationmemoryUnit tu1 = new TranslationmemoryUnit(project1.getId(), new SegmentType());
		tu1 = repo.addTU(project1.getId(), tu1);
		assertThat(tu1).isNotNull();
		System.out.println("Created TU: " + tu1);
		
		TranslationmemoryVariant tuv1 = new TranslationmemoryVariant(tu1.getId(), new Language(), "segment1");
		tuv1 = repo.addTUV(project1.getId(), tu1.getId(), tuv1);
		assertThat(tuv1).isNotNull();
		System.out.println("Created TUV: " + tuv1);
		
		try {
			TranslationmemoryVariant tuv2 = new TranslationmemoryVariant(tu1.getId(), new Language(), "segment2");
			tuv2 = repo.addTUV(project1.getId(), tu1.getId(), tuv2);
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("TranslationmemoryVariant's language already exists");
		}

	}	
	
	@Test
	void testFindMatchedTU() {
		TranslationProject project1 = new TranslationProject("test_find_matched_tu", "description");
		project1 = repo.addProject(project1);
		assertThat(project1).isNotNull();
		System.out.println("Created project: " + project1);
		
		TranslationmemoryUnit tu1 = new TranslationmemoryUnit(project1.getId(), new SegmentType());
		tu1 = repo.addTU(project1.getId(), tu1);
		assertThat(tu1).isNotNull();
		System.out.println("Created TU: " + tu1);
		
		TranslationmemoryVariant tuv1 = new TranslationmemoryVariant(tu1.getId(), new Language(), "S1234567890");
		tuv1 = repo.addTUV(project1.getId(), tu1.getId(), tuv1);
		assertThat(tuv1).isNotNull();
		System.out.println("Created TUV: " + tuv1);
		
		TranslationmemoryUnit tu2 = repo.findMatchedTU(project1.getId(), 80L, new Language(), "S12356789");
		assertThat(tu2).isNotNull();
		System.out.println("Found matched TU: " + tu2);
	}
	
	@Test
	void testDeleteProject() {
		TranslationProject project1 = new TranslationProject("test_delete_project", "description");
		project1 = repo.addProject(project1);
		assertThat(project1).isNotNull();
		System.out.println("Created project: " + project1);
		
		TranslationProject project2 = repo.findProject(project1.getId());
		assertThat(project2).isNotNull();
		System.out.println("Found project: " + project2);
		
		repo.deleteProject(project2.getId());
		System.out.println("Deleted project: " + project2);
		
		TranslationProject project3 = repo.findProject(project1.getId());
		assertThat(project3).isNull();
		System.out.println("Project not found: " + project3);
	}
	
	@Test
	void testDeleteTU() {
		TranslationProject project1 = new TranslationProject("test_delete_tu", "description");
		project1 = repo.addProject(project1);
		assertThat(project1).isNotNull();
		System.out.println("Created project: " + project1);
		
		TranslationmemoryUnit tu1 = new TranslationmemoryUnit(project1.getId(), new SegmentType());
		tu1 = repo.addTU(project1.getId(), tu1);
		assertThat(tu1).isNotNull();
		System.out.println("Created TU: " + tu1);
		
		TranslationmemoryUnit tu2 = repo.findTU(project1.getId(), tu1.getId());
		assertThat(tu2).isNotNull();
		System.out.println("Found TU: " + tu2);
		
		repo.deleteTU(project1.getId(), tu2.getId());
		System.out.println("Deleted TU: " + tu2);
		
		TranslationmemoryUnit tu3 = repo.findTU(project1.getId(), tu1.getId());
		assertThat(tu3).isNull();
		System.out.println("TU not found: " + tu3);
	}
	
	@Test
	void testDeleteTUV() {
		TranslationProject project1 = new TranslationProject("test_delete_tuv", "description");
		project1 = repo.addProject(project1);
		assertThat(project1).isNotNull();
		System.out.println("Created project: " + project1);
		
		TranslationmemoryUnit tu1 = new TranslationmemoryUnit(project1.getId(), new SegmentType());
		tu1 = repo.addTU(project1.getId(), tu1);
		assertThat(tu1).isNotNull();
		System.out.println("Created TU: " + tu1);
		
		TranslationmemoryVariant tuv1 = new TranslationmemoryVariant(tu1.getId(), new Language(), "segment");
		tuv1 = repo.addTUV(project1.getId(), tu1.getId(), tuv1);
		assertThat(tuv1).isNotNull();
		System.out.println("Created TUV: " + tuv1);
		
		TranslationmemoryVariant tuv2 = repo.findTUV(project1.getId(), tu1.getId(), tuv1.getId());
		assertThat(tuv2).isNotNull();
		System.out.println("Found TUV: " + tuv2);
		
		repo.deleteTUV(project1.getId(), tu1.getId(), tuv2.getId());
		System.out.println("Deleted TUV: " + tuv2);
		
		TranslationmemoryVariant tuv3 = repo.findTUV(project1.getId(), tu1.getId(), tuv1.getId());
		assertThat(tuv3).isNull();
		System.out.println("TUV not found: " + tuv3);
	}	
	
	@Test
	void testUpdateProject() {
		TranslationProject project1 = new TranslationProject("test_update_project", "description");
		project1 = repo.addProject(project1);
		assertThat(project1).isNotNull();
		System.out.println("Created project: " + project1);
		
		TranslationProject project2 = repo.findProject(project1.getId());
		assertThat(project2).isNotNull();
		System.out.println("Found project: " + project2);
		
		project2.setName("test_update_project2");
		project2.setDescription("description2");
		repo.updateProject(project2);
		System.out.println("Updated project: " + project2);
		
		TranslationProject project3 = repo.findProject(project1.getId());
		assertThat(project3).isNotNull();
		assertThat(project3.getName()).isEqualTo("test_update_project2");
		assertThat(project3.getDescription()).isEqualTo("description2");
		System.out.println("Found project: " + project3);
	}
	
	@Test
	void testUpdateTUV() {
		TranslationProject project1 = new TranslationProject("test_update_tuv", "description");
		project1 = repo.addProject(project1);
		assertThat(project1).isNotNull();
		System.out.println("Created project: " + project1);
		
		TranslationmemoryUnit tu1 = new TranslationmemoryUnit(project1.getId(), new SegmentType());
		tu1 = repo.addTU(project1.getId(), tu1);
		assertThat(tu1).isNotNull();
		System.out.println("Created TU: " + tu1);
		
		TranslationmemoryVariant tuv1 = new TranslationmemoryVariant(tu1.getId(), new Language(), "segment");
		tuv1 = repo.addTUV(project1.getId(), tu1.getId(), tuv1);
		assertThat(tuv1).isNotNull();
		System.out.println("Created TUV: " + tuv1);
		
		TranslationmemoryVariant tuv2 = repo.findTUV(project1.getId(), tu1.getId(), tuv1.getId());
		assertThat(tuv2).isNotNull();
		System.out.println("Found TUV: " + tuv2);
		
		tuv2.setSegment("segment2");
		repo.updateTUV(project1.getId(), tu1.getId(), tuv2);
		System.out.println("Updated TUV: " + tuv2);
		
		TranslationmemoryVariant tuv3 = repo.findTUV(project1.getId(), tu1.getId(), tuv1.getId());
		assertThat(tuv3).isNotNull();
		assertThat(tuv3.getSegment()).isEqualTo("segment2");
		System.out.println("Found TUV: " + tuv3);
	}
	
	@Test
	void testFindAllProjectsNull() {
		Iterable<TranslationProject> projects = repo.findAllProjects();
		projects.forEach(System.out::println); 
		for (TranslationProject project : projects) {
			assertThat(project).isNotNull();
		}
	}
	
	@Test
	void testFindAllTUsNull() {
		Iterable<TranslationmemoryUnit> tus = repo.findAllTUs(null);
		if (tus == null) {
			System.out.println("No TUs found");
			return;
		}
		tus.forEach(System.out::println); 
		for (TranslationmemoryUnit tu : tus) {
			assertThat(tu).isNotNull();
		}
	}
	
	@Test
	void testFindAllTUVsNull() {
		Iterable<TranslationmemoryVariant> tuvs = repo.findAllTUVs(null, null);
		if (tuvs == null) {
			System.out.println("No TUVs found");
			return;
		}
		tuvs.forEach(System.out::println); 
		for (TranslationmemoryVariant tuv : tuvs) {
			assertThat(tuv).isNotNull();
		}
	}	
	
	@Test
	void testFindAllLanguages() {
		Iterable<Language> langs = repo.findAllLanguages();
		langs.forEach(lang -> System.out.println("" + lang)); 
		for (Language lang : langs) {
			assertThat(lang).isNotNull();
			assertThat(lang.getId()).isNotNull();
			assertThat(lang.getLangcode()).isNotNull();
			assertThat(lang.getLanguage()).isNotNull();
		}
	}
	
	@Test
	void testFindAllSegmentTypes() {
		Iterable<SegmentType> segTypes = repo.findAllSegmentTypes();
		segTypes.forEach(System.out::println); 
		for (SegmentType segType : segTypes) {
			assertThat(segType).isNotNull();
			assertThat(segType.getId()).isNotNull();
			assertThat(segType.getType()).isNotNull();
		}
	}
	
	
	
}
