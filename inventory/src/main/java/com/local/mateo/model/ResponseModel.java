package com.local.mateo.model;

import java.util.List;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {
    private Object data;
    private List<ErrorModel> errors;
    private String status;
    private Response.Status httpCode;
}
