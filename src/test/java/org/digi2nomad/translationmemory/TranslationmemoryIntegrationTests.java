package org.digi2nomad.translationmemory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.digi2nomad.translationmemory.data.dao.TranslationProject;
import org.digi2nomad.translationmemory.service.dto.TranslationmemoryUnitDTO;
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
	static void jsonAssertEqualsEscapeIdnCreateDate (String expected, String actual) 
			throws JSONException {
		JSONAssert.assertEquals(expected, actual, 
				new CustomComparator(
					JSONCompareMode.LENIENT,
					new Customization("id", (o1, o2) -> true),
					new Customization("createDate", (o1, o2) -> true)));
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
		jsonAssertEqualsEscapeIdnCreateDate (expected, response.getBody()); 
		
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
		JSONAssert.assertEquals(expected, actual, 
					new CustomComparator(
						JSONCompareMode.LENIENT,
						new Customization("id", (o1, o2) -> true),
						new Customization("createDate", (o1, o2) -> true)));
		
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
		jsonAssertEqualsEscapeIdnCreateDate (expected, response.getBody()); 
		
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
		jsonAssertEqualsEscapeIdnCreateDate (expected, response.getBody()); 
		
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
		jsonAssertEqualsEscapeIdnCreateDate (expected, response.getBody()); 

		
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
		jsonAssertEqualsEscapeIdnCreateDate (expected, response.getBody());
		
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
	
}
