package com.hladchenko.mars;

import com.fasterxml.jackson.databind.JsonNode;

public class GoToMars {

    private static final String token = "vitG5CeayGcf74hW1oOr2KQcEZvoNx2useVarV8u";

    public static void main(String[] args) {
        String host = "api.nasa.gov";
        String path = "/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=%s".formatted(token);

        JsonNode jsonNode = SocketUtil.sendSSLRequest(host, path);

        System.out.println(jsonNode);
    }
}