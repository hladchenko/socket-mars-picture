package com.hladchenko.mars;

public record Response<T>(int code, T object) {

}
