package com.hladchenko.mars;

import com.fasterxml.jackson.databind.JsonNode;
import com.hladchenko.jupiter.client.Jupiter;
import com.hladchenko.jupiter.client.JupiterClient;
import com.hladchenko.jupiter.response.JupiterResponse;

import java.util.*;

public class GoToMars {

    private static final Jupiter jupiter = new JupiterClient();
    private static final String TOKEN = "";
    private static final String PATH = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=%s".formatted(TOKEN);

    public static void main(String[] args) {

        JupiterResponse response = jupiter.get(PATH);

        JsonNode jsonBody = response.getJsonBody().get("photos");

        List<String> srcList = getImageSourceList(jsonBody);

        Optional<JupiterResponse> max = srcList
                .parallelStream()
                .map(jupiter::head)
                .map(GoToMars::check301)
                .max(Comparator.comparing(JupiterResponse::getContentLength));

        max.ifPresent(jupiterResponse -> System.out.println(jupiterResponse.getRequestURL()));
    }

    private static List<String> getImageSourceList(JsonNode node) {
        Iterator<JsonNode> elements = node.elements();
        List<String> list = new ArrayList<>();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            String imageURL = element.get("img_src").asText();
            list.add(imageURL);
        }
        return list;
    }

    public static JupiterResponse check301(JupiterResponse response) {
        if (response.getResponseCode() == 301) {
            response = jupiter.head(response.getLocation());
        }
        return response;
    }
}