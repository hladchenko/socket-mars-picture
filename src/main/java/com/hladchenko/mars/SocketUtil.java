package com.hladchenko.mars;

import lombok.SneakyThrows;

import java.net.Socket;

public class SocketUtil {

    private static Socket socket;

    @SneakyThrows
    public static Socket getSocket(String host, int port) {
        if (socket == null) {
            socket = new Socket(host, port);
        }
        return socket;
    }

}
