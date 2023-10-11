package com.hladchenko.mars;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GoToMars {

    private static final String token = "";

    public static void main(String[] args) {
        String host = "api.nasa.gov";
        String path = "/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=%s".formatted(token);

        JsonNode jsonNode = SocketUtil.sendSSLRequest(host, path);

        Iterator<JsonNode> elements = jsonNode.elements();

        List<String> list =  new ArrayList<>();

        while (elements.hasNext()) {
            JsonNode element = elements.next();
            String imageURL = element.get("img_src").asText();
            list.add(imageURL);
        }


        URI uri = URI.create(list.get(0));

        String s = SocketUtil.sendRequestAndGetHeaders(uri.getHost(), uri.getPath());

        System.out.println(list);
    }
}