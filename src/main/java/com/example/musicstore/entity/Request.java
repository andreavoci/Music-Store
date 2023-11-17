package com.example.musicstore.entity;

import lombok.Data;

@Data
public class Request<T> {
    private long id;
    private T body;
}
