package ar.com.tecnoaccion.reporteria.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
    @JsonProperty("codigo")
    @JsonAlias({"errorCode"})
    private Integer errorCode;

    @JsonProperty("error")
    @JsonAlias({"errorMessage"})
    private String errorMessage;

    public ErrorMessage(){}

    public ErrorMessage(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}