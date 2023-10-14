package com.hladchenko.mars;

import com.fasterxml.jackson.databind.JsonNode;
import com.hladchenko.jupiter.client.Jupiter;
import com.hladchenko.jupiter.client.JupiterClient;
import com.hladchenko.jupiter.response.JupiterResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GoToMars {

    private static final String TOKEN = "";
    private static final String PATH = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=%s".formatted(TOKEN);


    public static void main(String[] args) {
        Jupiter jupiter = new JupiterClient();
        JupiterResponse response = jupiter.get(PATH);

        JsonNode jsonBody = response.getJsonBody().get("photos");

        Iterator<JsonNode> elements = jsonBody.elements();
        List<String> list = new ArrayList<>();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            String imageURL = element.get("img_src").asText();
            list.add(imageURL);
        }
        System.out.println(list);
    }
}