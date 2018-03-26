package com.sumscope.cdh.webbond.utils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Binson Qian (binson.qian@sumscope.com)
 */
public class RestfulClient {
    private static final String RUNAPI_PATH = "/api/runapi";
    private String host;
    private int port;
    private Client client;

    public RestfulClient(String host, int port) {
        this.host = host;
        this.port = port;
        client = ClientBuilder.newClient();
    }

    public void close() {
        client.close();
    }

    public JsonNode request(String content, String path) throws IOException {
        String url;
        if (port == -1) {
            url = String.format("http://%s", host);
        } else {
            url = String.format("http://%s:%d", host, port);
        }
        WebTarget target = client.target(url);
        String response = target.path(path)
                .request()
                .post(Entity.entity(content, MediaType.APPLICATION_JSON), String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response);
    }
    public JsonNode runApi(String content) throws IOException {
        JsonNode node = request(content, RUNAPI_PATH);
        return node.path("resultTable");
    }
}
