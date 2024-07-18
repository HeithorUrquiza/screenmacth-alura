package com.alura.screenmatch.services;

public interface IDataConversor {
    <T> T getData(String json, Class<T> tClass);
}

