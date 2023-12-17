package com.local.mateo.model;

import java.util.List;

public class ResponseModel {
    private Object data;
    private List<ErrorModel> errors;
    private String status;
    private int httpCode;

    public void setData(Object data) {
        this.data = data;
    }
    public void setErrors(List<ErrorModel> errors) {
        this.errors = errors;
    }
    public void setStatus(String status) {
        status = status;
    }
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
    public Object getData() {
        return data;
    }
    public List<ErrorModel> getErrors() {
        return errors;
    }
    public String getStatus() {
        return status;
    }
    public int getHttpCode() {
        return httpCode;
    }
}
