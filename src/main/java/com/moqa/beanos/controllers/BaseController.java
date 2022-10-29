package com.moqa.beanos.controllers;

import com.moqa.beanos.models.api.GenericResponse;
import com.moqa.beanos.models.api.Pagination;
import com.moqa.beanos.models.api.Response;
import com.moqa.beanos.models.api.Status;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class BaseController {


    protected <T> ResponseEntity<Response<T>> formatResponse() {
        return formatResponse(null, (Pagination) null);
    }
    protected <T> ResponseEntity<Response<T>> formatResponse(T result) {
        return formatResponse(result, (Pagination) null);
    }

    protected <T> ResponseEntity<Response<List<T>>> formatResponse(Page<T> result) {
        Pagination pagination = new Pagination(result.getTotalElements(), result.getSize(), result.getNumber());
        return formatResponse(result.getContent(), pagination);
    }

    protected <T> ResponseEntity<Response<T>> formatResponse(T result, Pagination paginationData) {
        return formatResponse(result, new Status(), paginationData);
    }

    protected <T> ResponseEntity<Response<T>> formatResponse(T result, Status status) {
        return formatResponse(result, status, null);
    }

    protected <T> ResponseEntity<Response<T>> formatResponse(T result, Status status, Pagination paginationData) {
        return formatResponse(result, status, paginationData, HttpStatus.OK);
    }

    protected <T> ResponseEntity<Response<T>> formatResponse(T result, Status status, Pagination paginationData, HttpStatus httpStatus) {
        Response<T> resultResponse = new Response<>(
                status,
                result,
                paginationData);
        return new GenericResponse<>(resultResponse, httpStatus).getResponse();
    }
}
