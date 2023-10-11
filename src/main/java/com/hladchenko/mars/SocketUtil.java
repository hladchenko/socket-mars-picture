package com.hladchenko.mars;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketUtil {

    private static Socket socket;

    @SneakyThrows
    public static Socket getSocket(String host, int port) {
        if (socket == null) {
            socket = SSLSocketFactory.getDefault().createSocket(host, port);
        }
        return socket;
    }

    @SneakyThrows
    public static JsonNode sendSSLRequest(String host, String path) {
        Socket socket = getSocket(host, 443);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        PrintWriter printWriter = new PrintWriter(outputStreamWriter);
        String request = """
                GET %s HTTP/1.1
                Host: %s
                Connection: close
                                    
                """.formatted(path, host);
        printWriter.write(request);
        printWriter.flush();

        JsonNode jsonNode = getJsonResponse();
//        printResponse();
        closeSocketConnection();

        return jsonNode;
    }

    @SneakyThrows
    private static void printResponse() {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.lines().forEach(System.out::println);
    }

    @SneakyThrows
    private static JsonNode getJsonResponse() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String> list = bufferedReader.lines().toList();

        if (list.indexOf("") + 1 <= list.size()) {
            jsonNode = objectMapper.readTree(list.get(list.indexOf("") + 1));
        }

        return jsonNode;
    }

    @SneakyThrows
    private static void closeSocketConnection() {
        socket.close();
    }
}