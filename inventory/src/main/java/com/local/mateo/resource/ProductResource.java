package com.local.mateo.resource;

import com.local.mateo.entity.DocumentTypeEntity;
import com.local.mateo.entity.ProductEntity;
import com.local.mateo.entity.ProviderEntity;
import com.local.mateo.model.ErrorModel;
import com.local.mateo.model.ResponseModel;
import com.local.mateo.repository.DocumentTypeRepository;
import com.local.mateo.repository.ProductRepository;
import com.local.mateo.repository.ProviderRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject ProductRepository productRepository;
    @Inject ProviderRepository providerRepository;
    @Inject DocumentTypeRepository documentTypeRepository;

    

    @GET
    @Path("init")
    @Transactional
    public Response init(){
        documentTypeRepository.persist(new DocumentTypeEntity(1L, "Cedula de ciudadania", "CC"));
        documentTypeRepository.persist(new DocumentTypeEntity(2L, "Número de identificación tributaria", "NIT"));
        ProviderEntity pe = new ProviderEntity(null, "mateo",documentTypeRepository.findById(1L), "1053829423", "test add", "3147");
        providerRepository.persist(pe);
        Set<ProviderEntity> providers = new HashSet<>();
        providers.add(pe);
        ProductEntity ppe = new ProductEntity(null,"tes1",1.0,true,providers);

        productRepository.persist(ppe);
        return Response.ok().status(Status.CREATED).build();
    }
    @GET
    public Response findAll(){
        ResponseModel rs = new ResponseModel();     
        try {
            rs.setData(productRepository.listAll());
            rs.setHttpCode(Status.OK);
            rs.setStatus("OK");
            rs.setErrors(null);            
        } catch (Exception e) {
            rs.setData(null);
            rs.setStatus("ERROR");
            rs.setHttpCode(Status.INTERNAL_SERVER_ERROR);
            ErrorModel er = new ErrorModel();
            er.setCode(1);
            er.setMessage(e.getMessage());
            List<ErrorModel> ers = Arrays.asList(er);
            rs.setErrors(ers);
        }
        return Response.ok(rs).status(Status.OK).build();
    }

    @GET
    @Path("{id}")
    public Response find_id(@PathParam("id") long id){
        ResponseModel rs = new ResponseModel();   
        try {
            rs.setData(productRepository.findById(id));
            rs.setHttpCode(Status.OK);
            rs.setStatus("OK");
            rs.setErrors(null);            
        } catch (Exception e) {
            rs.setData(null);
            rs.setStatus("ERROR");
            rs.setHttpCode(Status.INTERNAL_SERVER_ERROR);
            ErrorModel er = new ErrorModel();
            er.setCode(1);
            er.setMessage(e.getMessage());
            List<ErrorModel> ers = Arrays.asList(er);
            rs.setErrors(ers);
        }
        return Response.ok(rs).status(Status.OK).build();
    }

    @POST
    @Transactional
    public Response create(ProductEntity product) {
        ResponseModel rs = new ResponseModel();
        if (product.getId() != null) {
            rs.setData(null);
            rs.setStatus("ERROR");
            rs.setHttpCode(Status.BAD_REQUEST);
            ErrorModel er = new ErrorModel();
            er.setCode(2);
            er.setMessage("Id was invalidly set on request.");
            List<ErrorModel> ers = Arrays.asList(er);
            rs.setErrors(ers);
        }
        try {
            productRepository.persist(product);
            rs.setHttpCode(Status.CREATED);
            rs.setStatus("CREATE OK");
            rs.setData(product);
            rs.setErrors(null);              
        } catch (Exception e) {
            rs.setData(null);
            rs.setStatus("ERROR");
            rs.setHttpCode(Status.INTERNAL_SERVER_ERROR);
            ErrorModel er = new ErrorModel();
            er.setCode(1);
            er.setMessage(e.getMessage());
            List<ErrorModel> ers = Arrays.asList(er);
            rs.setErrors(ers);
        }
        return Response.ok(rs).status(rs.getHttpCode()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") long id) {
        ResponseModel rs = new ResponseModel();
        ProductEntity entity =  productRepository.findById(id);
        try {
            if(entity == null){
                rs.setData(null);
                rs.setStatus("ERROR");
                rs.setHttpCode(Status.NOT_FOUND);
                ErrorModel er = new ErrorModel();
                er.setCode(3);
                er.setMessage("El ID no existe");
                List<ErrorModel> ers = Arrays.asList(er);
                rs.setErrors(ers);
            }else{
                productRepository.deleteById(id);
                rs.setHttpCode(Status.OK);
                rs.setStatus("DELETE OK");
                rs.setData(entity);
                rs.setErrors(null);
            }              
        } catch (Exception e) {
            rs.setData(null);
            rs.setStatus("ERROR");
            rs.setHttpCode(Status.INTERNAL_SERVER_ERROR);
            ErrorModel er = new ErrorModel();
            er.setCode(1);
            er.setMessage(e.getMessage());
            List<ErrorModel> ers = Arrays.asList(er);
            rs.setErrors(ers);
        }
        return Response.ok(rs).status(rs.getHttpCode()).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") long id,ProductEntity product) {
        ResponseModel rs = new ResponseModel();
        if (product.getId() != null) {
            rs.setData(null);
            rs.setStatus("ERROR");
            rs.setHttpCode(Status.BAD_REQUEST);
            ErrorModel er = new ErrorModel();
            er.setCode(2);
            er.setMessage("Id was invalidly set on request.");
            List<ErrorModel> ers = Arrays.asList(er);
            rs.setErrors(ers);
        }
        ProductEntity entity =  productRepository.findById(id);
        try {
            if(entity == null){
                rs.setData(null);
                rs.setStatus("ERROR");
                rs.setHttpCode(Status.NOT_FOUND);
                ErrorModel er = new ErrorModel();
                er.setCode(3);
                er.setMessage("El ID no existe");
                List<ErrorModel> ers = Arrays.asList(er);
                rs.setErrors(ers);
            }else{
                if (product.getEnabled() !=  null) entity.setEnabled(product.getEnabled());                
                if (product.getName() !=  null) entity.setName(product.getName());
                if (product.getPrice() != 0) entity.setPrice(product.getPrice());
                if (product.getProviders() !=  null) entity.setProviders(product.getProviders());

                rs.setHttpCode(Status.OK);
                rs.setStatus("UPDATE OK");
                rs.setData(entity);
                rs.setErrors(null);
            }              
        } catch (Exception e) {
            rs.setData(null);
            rs.setStatus("ERROR");
            rs.setHttpCode(Status.INTERNAL_SERVER_ERROR);
            ErrorModel er = new ErrorModel();
            er.setCode(1);
            er.setMessage(e.getMessage());
            List<ErrorModel> ers = Arrays.asList(er);
            rs.setErrors(ers);
        }
        return Response.ok(rs).status(rs.getHttpCode()).build();
    }

}
