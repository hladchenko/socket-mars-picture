package com.hladchenko.mars;

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
    public static void sendSSLRequest(String host, String path) {
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

//        JSONObject jsonResponse = getJsonResponse();
        printResponse();
        closeSocketConnection();

//        return jsonResponse;
    }

    @SneakyThrows
    private static void printResponse() {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.lines().forEach(System.out::println);
    }

    @SneakyThrows
    private static void getJsonResponse() {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String> list = bufferedReader.lines().toList();

//        if (list.indexOf("") + 1 <= list.size()) {
//            jsonObject = new JSONObject(list.get(list.indexOf("") + 1));
//        }
//
//        return jsonObject;
    }

    @SneakyThrows
    private static void closeSocketConnection() {
        socket.close();
    }
}
