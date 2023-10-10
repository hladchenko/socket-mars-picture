package com.hladchenko.mars;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class Main {

    private static final String token = "";

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }


    @SneakyThrows
    private void sendRequest() {
        try (Socket socket = new Socket("api.nasa.gov", 80)) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter printWriter = new PrintWriter(outputStreamWriter);
            String request = """
                    GET /mars-photos/api/v1/rovers/curiosity/photos?sol=%s HTTP/1.1
                    Host: api.nasa.gov
                    Connection: close
                                        
                    """.formatted(token);
            printWriter.write(request);
            printWriter.flush();

            printResponse(socket);
        }
    }

    @SneakyThrows
    private void printResponse(Socket socket) {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.lines().forEach(System.out::println);
    }
}