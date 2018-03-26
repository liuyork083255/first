package com.sumscope.qpwb.ncd.web.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by mengyang.sun on 2018/1/29.
 */
@ApiModel(value = "订阅ncd")
public class NcdSubCondition {
    @ApiModelProperty(value = "用户id", position = 1)
    @NotEmpty(message = "用户id can not be null")
    private String userId;
    @ApiModelProperty(value = "token", position = 2)
    @NotEmpty(message = "token can not be null")
    private String token;
    @ApiModelProperty(value = "类型(all/favourite/filter)", position = 3)
    @Pattern(regexp = "all|favourite|filter",message = "参数status取值范围 all|favourite|filter")
    @NotEmpty(message = "type can not be null")
    private String type;
    @ApiModelProperty(value = "经济商id", position = 4)
    @NotEmpty(message = "brokerId can not be null")
    private String brokerId;
    @ApiModelProperty(value = "经济商id", position = 5)
    @NotEmpty(message = "access_token can not be null")
    @JsonProperty("access_token")
    private String accessToken;
    @ApiModelProperty(value = "tokenClientId", position = 6)
    @NotEmpty(message = "token_client_id can not be null")
    @JsonProperty("token_client_id")
    private String tokenClientId;
    @ApiModelProperty(value = "token_username", position = 7)
    @NotEmpty(message = "token_username can not be null")
    @JsonProperty("token_username")
    private String tokenName;
    @ApiModelProperty(value = "session", position = 8)
    @NotEmpty(message = "session can not be null")
    private String session;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
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

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
