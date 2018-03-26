package com.sumscope.cdh.webbond.test;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumscope.cdh.webbond.model.GetDictionaryRequest;

/*
 * Test code for reference, do not use.
 */
public class TestClient {

	private static final String SERVER_ADDRESS = "http://172.16.66.170:8888/api";
	private static final String LOGIN_REQUEST = "{ \"User\": \"QDP\", \"Password\": \"000000\", \"ExpireInSeconds\": 600 }";
	private static final String RUNAPI_REQUEST = "{ "
			+ "\"DataSourceId\": 100, "
			+ "\"ApiName\": \"HOLIDAY_INFO\", "
			+ "\"User\": \"QDP\", "
			+ "\"Codes\": [], "
			+ "\"Conditions\": null, "
			+ "\"ApiVersion\": \"N\", "
			+ "\"StartDate\": \"\", "
			+ "\"EndDate\": \"\", "
			+ "\"StartPage\": 1, "
			+ "\"PageSize\": 3, "
			+ "\"Columns\": null, "
			+ "\"ResultFormat\": \"json\" }";

	private void testRaw() {
		System.out.println("--- testing Raw ---");
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(SERVER_ADDRESS);
		String loginResponse = webTarget.path("login")
				.request()
				.post(Entity.entity(LOGIN_REQUEST, MediaType.APPLICATION_JSON), String.class);
		System.out.println(loginResponse);
		String runapiResponse = webTarget.path("runapi")
				.request()
				.post(Entity.entity(RUNAPI_REQUEST, MediaType.APPLICATION_JSON), String.class);
		System.out.println(runapiResponse);
	}

	private void testJSON() throws IOException {
		System.out.println("--- testing JSON ---");
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(SERVER_ADDRESS);

		GetDictionaryRequest getDictRequest = new GetDictionaryRequest();
		getDictRequest.setUser("test_user");
		getDictRequest.setUserToken("test_user_token");

		String getDictResponse = webTarget.path("getdictionary")
				.request()
				.post(Entity.entity(getDictRequest, MediaType.APPLICATION_JSON), String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(getDictResponse);
		System.out.println(String.format("getdictionary: %s", root.asText()));
	}

	public static void main(String[] args) throws IOException {
		TestClient client = new TestClient();
		//client.testRaw();
		client.testJSON();
	}

}
