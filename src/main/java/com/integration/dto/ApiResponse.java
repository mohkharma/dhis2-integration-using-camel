package com.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApiResponse implements Serializable   {

//        @JsonProperty("code")
//        private Integer code;
        @JsonProperty("errorCode")
        private String errorCode;
//        @JsonProperty("httpStatus")
//        private String httpStatus;
        @JsonProperty("httpStatusCode")
        private Integer httpStatusCode;
        @JsonProperty("message")
        private String message;
        @JsonProperty("response")
        private String response;

    public ApiResponse() {
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
