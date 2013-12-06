package com.springapp.mvc.service;

import org.springframework.stereotype.Repository;

@Repository
public class ExampleService {

    private int empty = 200;

    public int getEmpty() {
        empty -= 1;
        return empty;
    }

    public int free() {
        empty += 1;
        return empty;
    }
}
