package com.hladchenko.mars;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class GoToMars {

    private static final String token = "";
    private final Socket socket;

    public GoToMars(String host, int port) {
        socket = SocketUtil.getSocket(host, port);
    }

    public static void main(String[] args) {
        GoToMars mars = new GoToMars("api.nasa.gov", 80);
        mars.sendRequest();
        mars.printResponse();
    }

    @SneakyThrows
    public void sendRequest() {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        PrintWriter printWriter = new PrintWriter(outputStreamWriter);
        String request = """
                GET /mars-photos/api/v1/rovers/curiosity/photos?sol=%s HTTP/1.1
                Host: api.nasa.gov
                Connection: close
                                    
                """.formatted(token);
        printWriter.write(request);
        printWriter.flush();
    }

    @SneakyThrows
    private void printResponse() {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.lines().forEach(System.out::println);
    }

    @SneakyThrows
    private void closeSocketConnection() {
        socket.close();
    }
}