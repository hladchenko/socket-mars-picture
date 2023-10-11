package com.hladchenko.mars;

import com.fasterxml.jackson.databind.JsonNode;

public class GoToMars {

    private static final String token = "";

    public static void main(String[] args) {
        String host = "api.nasa.gov";
        String path = "/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=%s".formatted(token);

        JsonNode jsonNode = SocketUtil.sendSSLRequest(host, path);

        System.out.println(jsonNode);
    }
}