package com.susion.boring.read.mvp.entity;

import java.util.List;

/**
 * Created by susion on 17/3/16.
 */
public class JokeList {

    private String reason;
    private int error_code;
    private List<Joke> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<Joke> getResult() {
        return result;
    }

    public void setResult(List<Joke> result) {
        this.result = result;
    }
}
