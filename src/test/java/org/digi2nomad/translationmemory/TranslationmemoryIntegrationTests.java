package org.digi2nomad.translationmemory;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.http.HttpStatusCode;
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
		HttpStatusCode statusCode = response.getStatusCode();
		assert(statusCode.is4xxClientError());
		String actual = response.getBody();
		JSONAssert.assertEquals(expected, actual, new CustomComparator(
				JSONCompareMode.LENIENT,
				new Customization("timestamp", (o1, o2) -> true)));
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
		String actual = response.getBody();
		JSONAssert.assertEquals(expected, actual, 
					new CustomComparator(
						JSONCompareMode.LENIENT,
						new Customization("id", (o1, o2) -> true),
						new Customization("createDate", (o1, o2) -> true)));
		
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
				
		actual = response.getBody();
		List<TranslationProject> actualProjects 
			= mapper.readValue(actual, new TypeReference<List<TranslationProject>>() {});
		
		JSONArray expectedJson = new JSONArray(expected);
		JSONArray actualJson = new JSONArray(actual);
		JSONAssert.assertEquals(expectedJson, actualJson, false);
		/*
		 * JSONAssert.assertEquals(expected, actual, new CustomComparator(
		 * JSONCompareMode.LENIENT, new Customization("id", (o1, o2) -> true), new
		 * Customization("createDate", (o1, o2) -> true)));
		 */
	}
	
}
