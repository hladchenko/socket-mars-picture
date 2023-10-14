package com.hladchenko.jupiter.response;

import com.hladchenko.jupiter.request.JupiterRequest;

import java.util.List;

public interface JupiterResponse {

    public JupiterRequest getRequest();

    public int getResponseCode();

    public String getHeader(String headerName);

    public List<String> getBody();
}
