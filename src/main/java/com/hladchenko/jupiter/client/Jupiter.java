package com.hladchenko.jupiter.client;

import com.hladchenko.jupiter.response.JupiterResponse;

public interface Jupiter {

    JupiterResponse get(String host);

    JupiterResponse head(String host);

}
