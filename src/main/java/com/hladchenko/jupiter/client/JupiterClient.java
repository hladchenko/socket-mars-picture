package com.hladchenko.jupiter.client;

import com.hladchenko.jupiter.request.BasicJupiterRequest;
import com.hladchenko.jupiter.request.JupiterRequest;
import com.hladchenko.jupiter.response.BasicJupiterResponse;
import com.hladchenko.jupiter.response.JupiterResponse;
import lombok.SneakyThrows;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JupiterClient implements Jupiter {

    private static final String GET = "GET";
    private static final String HEAD = "HEAD";

    @SneakyThrows
    private Socket getSocket(String host) {
        return SSLSocketFactory.getDefault().createSocket(host, 443);
    }

    @SneakyThrows
    @Override
    public JupiterResponse get(String host) {
        JupiterRequest request = new BasicJupiterRequest(new URI(host));
        JupiterResponse response = null;
        try (Socket socket = getSocket(request.getHost())) {
            response = sendRequest(GET, socket, request);
        }
        return response;
    }

    @SneakyThrows
    @Override
    public JupiterResponse head(String host) {
        JupiterRequest request = new BasicJupiterRequest(new URI(host));
        JupiterResponse response = null;
        try (Socket socket = getSocket(request.getHost())) {
            response = sendRequest(HEAD, socket, request);
        }
        return response;
    }

    @SneakyThrows
    private JupiterResponse sendRequest(String requestMethod, Socket socket, JupiterRequest request) {
        JupiterResponse response = new BasicJupiterResponse(request);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        PrintWriter printWriter = new PrintWriter(outputStreamWriter);
        String requestString = """
                %s %s HTTP/1.1
                Host: %s
                Connection: close
                                    
                """.formatted(requestMethod, request.getPathAndQuery(), request.getHost());
        printWriter.write(requestString);
        printWriter.flush();

        return getResponse(response, socket);
    }

    @SneakyThrows
    private JupiterResponse getResponse(JupiterResponse response, Socket socket) {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String> list = bufferedReader.lines().toList();
        return new BasicJupiterResponse(response, list);
    }
}
