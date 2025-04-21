package org.digi2nomad.translationmemory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.digi2nomad.translationmemory.data.dao.TranslationProject;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryUnitDTO;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryVariantDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(classes = TranslationmemoryApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TranslationmemoryIntegrationTests {
	
	@LocalServerPort
	private int port;
	
	/**
	 * @param expected
	 * @param actual
	 * @throws JSONException
	 */
	static void jsonAssertEqualsEscapeTimestamp (String expected, String actual) 
			throws JSONException {
		JSONAssert.assertEquals(expected, actual, new CustomComparator(
				JSONCompareMode.LENIENT,
				new Customization("timestamp", (o1, o2) -> true)));
	}
	
	/**
	 * @param expected
	 * @param actual
	 * @throws JSONException
	 */
	static void jsonAssertEqualsEscapeIdnDate (String expected, String actual) 
			throws JSONException {
		JSONAssert.assertEquals(expected, actual, 
				new CustomComparator(
					JSONCompareMode.LENIENT,
					new Customization("id", (o1, o2) -> true),
					new Customization("createDate", (o1, o2) -> true),
					new Customization("useDate", (o1, o2) -> true)));
	}
	
	@Test
	public void testCreateProjectWithMalformedJson() throws JSONException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("{", headers);
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = 
		"{" +
			  "\"timestamp\" : \"2025-04-01T00:27:48.213+00:00\"," +
			  "\"status\" : 400," + 
			  "\"error\" : \"Bad Request\"," +
			  "\"path\" : \"/projects\"" +
		"}";
		assert(response.getStatusCode().is4xxClientError());
		jsonAssertEqualsEscapeTimestamp (expected, response.getBody());
	}
	
	@Test
	public void testCreateProjectWithMissingDescription() throws JSONException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("{\"name\":\"EV Update\"}", headers);
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = "{\"description\":\"must not be blank\"}";
		String actual = response.getBody();
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	@Test
	public void testCreateProjectWithMissingName() throws JSONException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("{\"description\":\"trending news of electric vehicles\"}", headers);
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = "{\"name\":\"must not be blank\"}";
		String actual = response.getBody();
		JSONAssert.assertEquals(expected, actual, false);
	}
	
	@Test
	public void testCreateProjectWithMissingNameAndDescription() throws JSONException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("{}", headers);
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = "{\"name\":\"must not be blank\",\"description\":\"must not be blank\"}";
		String actual = response.getBody();
		JSONAssert.assertEquals(expected, actual, false);
	}
		
	//TODO @Test
	public void testCreateAndRetrieveProjects() throws JSONException, JsonMappingException, JsonProcessingException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1: {"name": "EV Update", "description": "trending news of electric vehicles"}
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(
				"{" +
						"\"name\":\"EV Update\"," +
						"\"description\":\"trending news of electric vehicles\"" +
				"}", headers);		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"name\" : \"EV Update\"," +
						"\"description\" : \"trending news of electric vehicles\"," + 
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 
		
		// Create a project 2: {"name": "Semiconductor Update", "description": "trending news of semiconductors industry"}
		entity = new HttpEntity<String>(
				"{" +
						"\"name\":\"Semiconductor Update\"," +
						"\"description\":\"trending news of semiconductors industry\"" +
				"}", headers);		
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		// Retrieve the projects 
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		entity = new HttpEntity<String>(null, headers);
		
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		expected = 
				"[ {" +
					"\"id\" : 1115955523795045497," +
					"\"name\" : \"EV Update\"," +
					"\"description\" : \"trending news of electric vehicles\"," +
					"\"createDate\" : \"2025-04-01T01:50:25.131345Z\"" +
				"}, {" +
					"\"id\" : 3220065024962940893," +
					"\"name\" : \"Semiconductor Update\"," +
					"\"description\" : \"trending news of semiconductors industry\"," +
					"\"createDate\" : \"2025-04-01T01:50:25.212056Z\"" +
				"} ]";
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<TranslationProject> expectedProjects 
			= mapper.readValue(expected, new TypeReference<List<TranslationProject>>() {});
				
		List<TranslationProject> actualProjects 
			= mapper.readValue(response.getBody(), new TypeReference<List<TranslationProject>>() {});
		
		JSONArray expectedJson = new JSONArray(expected);
		JSONArray actualJson = new JSONArray(response.getBody());
		JSONAssert.assertEquals(expectedJson, actualJson, false);
		/*
		 * jsonAssertEqualsEspcapeIdnCreateDate (expected, response.getBody()); 
		 */
	}
	
	@Test
	public void testRetrieveAndDeleteProjectById() throws JSONException, JsonMappingException, JsonProcessingException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1: {"name": "EV Update", "description": "trending news of electric vehicles"}
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(
				"{" +
						"\"name\":\"EV Update\"," +
						"\"description\":\"trending news of electric vehicles\"" +
				"}", headers);		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"name\" : \"EV Update\"," +
						"\"description\" : \"trending news of electric vehicles\"," + 
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		String actual = response.getBody();
		jsonAssertEqualsEscapeIdnDate (expected, actual);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationProject actualProject 
			= mapper.readValue(actual, TranslationProject.class);
		Long projectId = actualProject.getId();
		
		// Retrieve the project
		url = "http://localhost:"+ port + "/projects/" + projectId;
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		entity = new HttpEntity<String>(null, headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		expected = 
				"{" +
					"\"id\" : 3782510073853855622," +
					"\"name\" : \"EV Update\"," +
					"\"description\" : \"trending news of electric vehicles\"," +
					"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 
		
		//
		// Delete the project
		//
		url = "http://localhost:"+ port + "/projects/" + projectId;
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(null, headers);
		
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		
		expected = 	"Project deleted successfully";
		actual = response.getBody();
		assertThat(expected).isEqualTo(actual);
	} // testRetrieveAndDeleteProjectById
	
	@Test
	public void testCreateAndRetrieveAndDeleteTUById () throws JSONException, JsonMappingException, JsonProcessingException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1: {"name": "EV Update", "description": "trending news of electric vehicles"}
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(
				"{" +
						"\"name\":\"EV Update\"," +
						"\"description\":\"trending news of electric vehicles\"" +
				"}", headers);		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"name\" : \"EV Update\"," +
						"\"description\" : \"trending news of electric vehicles\"," + 
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationProject actualProject 
			= mapper.readValue(response.getBody(), TranslationProject.class);
		Long projectId = actualProject.getId();
		
		// Create a translation unit
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(
				"{" +
					  "\"projId\": " + projectId + "," +
					  "\"segmentType\": { " +
					    "\"type\": \"sentence\"" +
					  "}" +
				"}", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationmemoryUnitDTO actualTU
			= mapper.readValue(response.getBody(), TranslationmemoryUnitDTO.class);
		Long uId = actualTU.getId();
		Long segmentTypeId = actualTU.getSegmentType().getId();
		expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"projId\" :" + projectId + "," +
						"\"segmentType\" : {" +
							"\"id\" : " + segmentTypeId + "," +
							"\"type\" : \"sentence\"," +
							"\"description\" : \"A sentence\"" +
							"}," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 

		
		//
		// Retrieve the translation unit
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + uId;
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		entity = new HttpEntity<String>(null, headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"projId\" :" + projectId + "," +
						"\"segmentType\" : {" +
							"\"id\" : " + segmentTypeId + "," +
							"\"type\" : \"sentence\"," +
							"\"description\" : \"A sentence\"" +
							"}," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody());
		
		//
		// Delete the translation unit
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + uId;
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(null, headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		expected = 	"TU deleted successfully";
		assertThat(response.getBody()).isEqualTo(expected);		
		
	} // testCreateAndRetrieveAndDeleteTUById
	
	@Test
	public void testCreateAndRetrieveAndDeleteTUVById() 
			throws JSONException, JsonMappingException, JsonProcessingException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1: {"name": "EV Update", "description": "trending news of electric vehicles"}
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(
				"{" +
						"\"name\":\"EV Update\"," +
						"\"description\":\"trending news of electric vehicles\"" +
				"}", headers);		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"name\" : \"EV Update\"," +
						"\"description\" : \"trending news of electric vehicles\"," + 
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationProject actualProject 
			= mapper.readValue(response.getBody(), TranslationProject.class);
		Long projectId = actualProject.getId();
		
		// Create a translation unit
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(
				"{" +
					  "\"projId\": " + projectId + "," +
					  "\"segmentType\": { " +
					    "\"type\": \"sentence\"" +
					  "}" +
				"}", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationmemoryUnitDTO actualTU
			= mapper.readValue(response.getBody(), TranslationmemoryUnitDTO.class);
		Long tuId = actualTU.getId();
		Long segmentTypeId = actualTU.getSegmentType().getId();
		expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"projId\" :" + projectId + "," +
						"\"segmentType\" : {" +
							"\"id\" : " + segmentTypeId + "," +
							"\"type\" : \"sentence\"," +
							"\"description\" : \"A sentence\"" +
							"}" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 

		//
		// Create a translation unit variant
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + tuId + "/variants";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(
				"{" +
						  "\"tuId\":" + tuId +"," +
						  "\"language\": {" +
						    "\"langcode\": \"en-US\"" +
						  "}," +
						  "\"segment\": \"string\"" +
				"}", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationmemoryVariantDTO actualTUV
			= mapper.readValue(response.getBody(), TranslationmemoryVariantDTO.class);
		Long tuvId = actualTUV.getId();
		expected = 
				"{" +
						"\"id\" : " + tuvId + "," +
						"\"tuId\" :" + tuId + "," +
						"\"language\" : {" +
						    "\"id\" : 103," +
						    "\"langcode\" : \"en-US\"," +
						    "\"language\" : \"English\"" +
						"}," +
						"\"segment\" : \"string\"," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"," +
						"\"useDate\" : null," +
						"\"useCount\" : 0," +
						"\"reviewed\" : false" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody());
		
		//
		// Retrieve the translation unit variant
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + tuId + "/variants/" + tuvId;
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		entity = new HttpEntity<String>(null, headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		expected = 
				"{" +
						"\"id\" : " + tuvId + "," +
						"\"tuId\" :" + tuId + "," +
						"\"language\" : {" +
						    "\"id\" : 103," +
						    "\"langcode\" : \"en-US\"," +
						    "\"language\" : \"English\"" +
						"}," +
						"\"segment\" : \"string\"," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"," +
						"\"useDate\" : null," +
						"\"useCount\" : 0," +
						"\"reviewed\" : false" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody());
		
		//
		// Update the translation unit variant
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + tuId + "/variants/" + tuvId;
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(
				"{" +
						  "\"tuId\":" + tuId +"," +
						  "\"language\": {" +
						    "\"langcode\": \"en-US\"" +
						  "}," +
						  "\"segment\": \"string updated\"" +
				"}", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		actualTUV
			= mapper.readValue(response.getBody(), TranslationmemoryVariantDTO.class);
		expected = 
				"{" +
						"\"id\" : " + tuvId + "," +
						"\"tuId\" :" + tuId + "," +
						"\"language\" : {" +
						    "\"id\" : 103," +
						    "\"langcode\" : \"en-US\"," +
						    "\"language\" : \"English\"" +
						"}," +
						"\"segment\" : \"string updated\"," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"," +
						"\"useDate\" : \"2025-03-31T23:16:37.199148900Z\"," +
						"\"useCount\" : 0," +
						"\"reviewed\" : false" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody());

		//
		// Delete the translation unit variant
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + tuId + "/variants/" + tuvId;
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(null, headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		expected = 	"TUV deleted successfully";
		assertThat(response.getBody()).isEqualTo(expected);
		
	} // testCreateAndRetrieveAndDeleteTUVById
	
	@Test
	void testRetrieveMatchedUnitAndItsVariants() throws JSONException, JsonMappingException, JsonProcessingException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1: {"name": "EV Update", "description": "trending news of electric vehicles"}
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(
				"{" +
						"\"name\":\"EV Update\"," +
						"\"description\":\"trending news of electric vehicles\"" +
				"}", headers);		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"name\" : \"EV Update\"," +
						"\"description\" : \"trending news of electric vehicles\"," + 
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationProject actualProject 
			= mapper.readValue(response.getBody(), TranslationProject.class);
		Long projectId = actualProject.getId();
		
		// Create a translation unit
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(
				"{" +
					  "\"projId\": " + projectId + "," +
					  "\"segmentType\": { " +
					    "\"type\": \"sentence\"" +
					  "}" +
				"}", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationmemoryUnitDTO actualTU
			= mapper.readValue(response.getBody(), TranslationmemoryUnitDTO.class);
		Long tuId = actualTU.getId();
		Long segmentTypeId = actualTU.getSegmentType().getId();
		expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"projId\" :" + projectId + "," +
						"\"segmentType\" : {" +
							"\"id\" : " + segmentTypeId + "," +
							"\"type\" : \"sentence\"," +
							"\"description\" : \"A sentence\"" +
							"}" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 

		//
		// Create a translation unit variant
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + tuId + "/variants";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(
				"{" +
						  "\"tuId\":" + tuId +"," +
						  "\"language\": {" +
						    "\"langcode\": \"en-US\"" +
						  "}," +
						  "\"segment\": \"string\"" +
				"}", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationmemoryVariantDTO actualTUV
			= mapper.readValue(response.getBody(), TranslationmemoryVariantDTO.class);
		Long tuvId = actualTUV.getId();
		expected = 
				"{" +
						"\"id\" : " + tuvId + "," +
						"\"tuId\" :" + tuId + "," +
						"\"language\" : {" +
						    "\"id\" : 103," +
						    "\"langcode\" : \"en-US\"," +
						    "\"language\" : \"English\"" +
						"}," +
						"\"segment\" : \"string\"," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"," +
						"\"useDate\" : null," +
						"\"useCount\" : 0," +
						"\"reviewed\" : false" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody());
		
		//
		// Retrieve the matched unit and its variants
		//
		Long matchRatioThreshold = 80L;
		url = "http://localhost:"+ port + "/projects/" + projectId + "/matchedunit/" + matchRatioThreshold + "/language/en-US";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>("\"string\"", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
			
		TranslationmemoryUnitDTO actualMatchedTU
			= mapper.readValue(response.getBody(), TranslationmemoryUnitDTO.class);
		List<TranslationmemoryVariantDTO> actualMatchedTUVs 
			= actualMatchedTU.getVariants();
		assertThat(actualMatchedTUVs.size()).isEqualTo(1);
		actualMatchedTUVs.forEach(tuv -> {
			assertThat(tuv.getTuId()).isEqualTo(tuId);
			assertThat(tuv.getLanguage().getLangcode()).isEqualTo("en-US");
		});
		expected = 
				"{" +
						"\"id\" : " + tuId + "," +
						"\"projId\" :" + projectId + "," +
						"\"segmentType\" : {" +
							"\"id\" : " + segmentTypeId + "," +
							"\"type\" : \"sentence\"," +
							"\"description\" : \"A sentence\"" +
							"}," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
						/*"\"variants\" : [ {" +
							    "\"id\" : " + tuvId + "," +
							    "\"tuId\" : " + tuId + "," +
							    "\"language\" : {" +
							      "\"id\" : 103," +
							      "\"langcode\" : \"en-US\"," +
							      "\"language\" : \"English\"" +
							    "}," +
							    "\"segment\" : \"string\"," +
							    "\"createDate\" : \"2025-04-21T00:13:32.215740Z\"," +
							    "\"useDate\" : null," +
							    "\"useCount\" : 0, " +
							    "\"reviewed\" : false" +
						"} ]" +*/
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody());
	} // testRetrieveMatchedUnitAndItsVariants
	
	@Test
	void testGetUnitAndItsVariants() throws JSONException, JsonMappingException, JsonProcessingException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1: {"name": "EV Update", "description": "trending news of electric vehicles"}
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(
				"{" +
						"\"name\":\"EV Update\"," +
						"\"description\":\"trending news of electric vehicles\"" +
				"}", headers);		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"name\" : \"EV Update\"," +
						"\"description\" : \"trending news of electric vehicles\"," + 
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationProject actualProject 
			= mapper.readValue(response.getBody(), TranslationProject.class);
		Long projectId = actualProject.getId();
		
		// Create a translation unit
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(
				"{" +
					  "\"projId\": " + projectId + "," +
					  "\"segmentType\": { " +
					    "\"type\": \"sentence\"" +
					  "}" +
				"}", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationmemoryUnitDTO actualTU
			= mapper.readValue(response.getBody(), TranslationmemoryUnitDTO.class);
		Long tuId = actualTU.getId();
		Long segmentTypeId = actualTU.getSegmentType().getId();
		expected = 
				"{" +
						"\"id\" : 3782510073853855622," +
						"\"projId\" :" + projectId + "," +
						"\"segmentType\" : {" +
							"\"id\" : " + segmentTypeId + "," +
							"\"type\" : \"sentence\"," +
							"\"description\" : \"A sentence\"" +
							"}" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody()); 

		//
		// Create a translation unit variant
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + tuId + "/variants";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(
				"{" +
						  "\"tuId\":" + tuId +"," +
						  "\"language\": {" +
						    "\"langcode\": \"en-US\"" +
						  "}," +
						  "\"segment\": \"string\"" +
				"}", headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TranslationmemoryVariantDTO actualTUV
			= mapper.readValue(response.getBody(), TranslationmemoryVariantDTO.class);
		Long tuvId = actualTUV.getId();
		expected = 
				"{" +
						"\"id\" : " + tuvId + "," +
						"\"tuId\" :" + tuId + "," +
						"\"language\" : {" +
						    "\"id\" : 103," +
						    "\"langcode\" : \"en-US\"," +
						    "\"language\" : \"English\"" +
						"}," +
						"\"segment\" : \"string\"," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"," +
						"\"useDate\" : null," +
						"\"useCount\" : 0," +
						"\"reviewed\" : false" +
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody());
		
		//
		// Retrieve the unit and its variants
		//
		url = "http://localhost:"+ port + "/projects/" + projectId + "/units/" + tuId + "/variants";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(null, headers);
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
			
		actualTU
			= mapper.readValue(response.getBody(), TranslationmemoryUnitDTO.class);
		List<TranslationmemoryVariantDTO> actualTUVs 
			= actualTU.getVariants();
		assertThat(actualTUVs.size()).isEqualTo(1);
		actualTUVs.forEach(tuv -> {
			assertThat(tuv.getTuId()).isEqualTo(tuId);
			assertThat(tuv.getLanguage().getLangcode()).isEqualTo("en-US");
		});
		expected = 
				"{" +
						"\"id\" : " + tuId + "," +
						"\"projId\" :" + projectId + "," +
						"\"segmentType\" : {" +
							"\"id\" : " + segmentTypeId + "," +
							"\"type\" : \"sentence\"," +
							"\"description\" : \"A sentence\"" +
							"}," +
						"\"createDate\" : \"2025-03-31T23:16:37.199148900Z\"" +
						/*"\"variants\" : [ {" +
							    "\"id\" : " + tuvId + "," +
							    "\"tuId\" : " + tuId + "," +
							    "\"language\" : {" +
							      "\"id\" : 103," +
							      "\"langcode\" : \"en-US\"," +
							      "\"language\" : \"English\"" +
							    "}," +
							    "\"segment\" : \"string\"," +
							    "\"createDate\" : \"2025-04-21T00:13:32.215740Z\"," +
							    "\"useDate\" : null," +
							    "\"useCount\" : 0, " +
							    "\"reviewed\" : false" +
						"} ]" +*/
				"}";
		jsonAssertEqualsEscapeIdnDate (expected, response.getBody());
		
	} // testGetUnitAndItsVariants
}
