package com.sumscope.qpwb.ncd.data.model.webserver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WebBondRequest implements Serializable {
    private String token;
    private String brokerId;
    private String institutionCodes;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_client_id")
    private String tokenClientId;
    @JsonProperty("token_username")
    private String tokenUsername;
    private String session;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getInstitutionCodes() {
        return institutionCodes;
    }

    public void setInstitutionCodes(String institutionCodes) {
        this.institutionCodes = institutionCodes;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenClientId() {
        return tokenClientId;
    }

    public void setTokenClientId(String tokenClientId) {
        this.tokenClientId = tokenClientId;
    }

    public String getTokenUsername() {
        return tokenUsername;
    }

    public void setTokenUsername(String tokenUsername) {
        this.tokenUsername = tokenUsername;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
