package com.hladchenko.mars;

public class GoToMars {

    private static final String token = "";

    public static void main(String[] args) {
        String host = "api.nasa.gov";
        String path = "/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=%s".formatted(token);

        SocketUtil.sendSSLRequest(host, path);
    }
}