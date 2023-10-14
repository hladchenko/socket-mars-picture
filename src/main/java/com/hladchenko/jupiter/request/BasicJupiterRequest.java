package com.hladchenko.jupiter.request;

import java.net.URI;

public class BasicJupiterRequest implements JupiterRequest {

    private final URI uri;

    public BasicJupiterRequest(URI uri) {
        this.uri = uri;
    }

    @Override
    public String getPath() {
        return this.uri.getPath();
    }

    @Override
    public String getQuery() {
        return this.uri.getQuery();
    }

    @Override
    public String getPathAndQuery() {
        return String.format("%s?%s", getPath(), getQuery());
    }

    @Override
    public URI getURI() {
        return this.uri;
    }

    @Override
    public String getHost() {
        return this.uri.getHost();
    }
}
