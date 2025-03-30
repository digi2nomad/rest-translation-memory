package org.digi2nomad.translationmemory;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	public void testGetProjects() throws JSONException {
		String url = "http://localhost:" + port + "/projects";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		String expected = "[{\"id\":1,\"name\":\"Project 1\"},{\"id\":2,\"name\":\"Project 2\"}]";
		//assertEquals(expected, response.getBody()/*, false*/);
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
}
