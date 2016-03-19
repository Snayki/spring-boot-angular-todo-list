package com.todo.utils.security;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Snayki on 19.03.2016.
 */
public class TokenResponse {
    @JsonProperty
    private String token;

    public TokenResponse() {
    }

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
