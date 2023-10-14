package com.hladchenko.jupiter.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hladchenko.jupiter.request.JupiterRequest;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicJupiterResponse implements JupiterResponse {

    private final JupiterRequest request;
    private Map<String, String> headers;
    private List<String> body;
    private int responseCode = 0;

    public BasicJupiterResponse(JupiterRequest request) {
        this.request = request;
    }

    public BasicJupiterResponse(JupiterResponse response, List<String> responseList) {
        this.request = response.getRequest();
        parseResponse(responseList);
    }

    @Override
    public JupiterRequest getRequest() {
        return this.request;
    }

    @Override
    public int getResponseCode() {
        return this.responseCode;
    }

    @Override
    public String getHeader(String headerName) {
        if (this.headers == null) return "";
        return this.headers.get(headerName);
    }

    @Override
    public List<String> getBody() {
        return this.body;
    }

    @SneakyThrows
    @Override
    public JsonNode getJsonBody() {
        if (this.body == null || this.body.isEmpty()) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(this.body.get(0));
    }

    private void parseResponse(List<String> lines) {
        if (!lines.isEmpty()) {

            String[] firstLine = lines.get(0).split(" ");
            this.responseCode = Integer.parseInt(firstLine[1]);

            int headersEndIndex = lines.indexOf("");

            List<String> headersList = lines.subList(2, headersEndIndex);
            this.headers = new HashMap<>();
            for (String header : headersList) {
                String[] headerArr = header.split(": ");
                String key = headerArr[0];
                String value = headerArr[1];
                this.headers.put(key, value);
            }

            this.body = lines.subList(headersEndIndex + 1, lines.size());
        }
    }
}
