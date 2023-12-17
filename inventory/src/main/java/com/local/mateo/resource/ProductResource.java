package com.local.mateo.resource;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import com.local.mateo.entity.ProductEntity;
import com.local.mateo.model.ErrorModel;
import com.local.mateo.model.ResponseModel;
import com.local.mateo.repository.ProductRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject ProductRepository repository;

    @GET
    public ResponseModel findAll(){
        ResponseModel rs = new ResponseModel();
        rs.setData(repository.listAll());
        rs.setStatus("OK");
        return rs;
    }
    @POST
    @Transactional
    public Response create(ProductEntity product) {
        ResponseModel rs = new ResponseModel();
        if (product.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        try {
        repository.persist(product);
        rs.setHttpCode(201);
        rs.setStatus("OK");
        rs.setData(product);
            
        } catch (Exception e) {
            rs.setStatus("ERROR");
            rs.setHttpCode(422);
            ErrorModel er = new ErrorModel();
            er.setCode(rs.getHttpCode());
            er.setMessage(e.getMessage());
            List<ErrorModel> ers = Arrays.asList(er);
            rs.setErrors(ers);
        }
        return Response.ok(rs).status(rs.getHttpCode()).build();
    }

}
