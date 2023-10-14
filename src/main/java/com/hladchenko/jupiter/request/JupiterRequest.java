package com.hladchenko.jupiter.request;

import java.net.URI;

public interface JupiterRequest {

    String getHost();

    String getPath();

    String getQuery();

    String getPathAndQuery();

    URI getURI();
}
