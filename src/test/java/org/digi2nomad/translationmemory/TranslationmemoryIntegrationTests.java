package org.digi2nomad.translationmemory;

import java.util.Arrays;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

//@org.junit.runner.RunWith(SpringRunner.class)
@SpringBootTest(classes = TranslationmemoryApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TranslationmemoryIntegrationTests {
	
	@LocalServerPort
	private int port;
	
	@Test
	public void testCreateAndRetrieveProjects() throws JSONException {
		String url = "http://localhost:"+ port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		
		// Create a project 1
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("{\"id\":1,\"name\":\"Project 1\"}", headers);
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		String expected = "{\"name\":\"Project 1\"}";
		String actual = response.getBody();
		JSONAssert.assertEquals(expected, actual, false);
		
		// Retrieve the projects 
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		entity = new HttpEntity<String>(null, headers);
		
		restTemplate = new TestRestTemplate();
		response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		expected = "[{\"id\":1,\"name\":\"Project 1\"},{\"id\":2,\"name\":\"Project 2\"}]";
		actual = response.getBody();
		//assertEquals(expected, response.getBody()/*, false*/);
		JSONAssert.assertEquals(expected, actual, false);
	}
	
}
