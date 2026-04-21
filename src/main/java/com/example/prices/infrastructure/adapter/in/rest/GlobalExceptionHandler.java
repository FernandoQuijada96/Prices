package com.example.prices.infrastructure.adapter.in.rest;

import com.example.prices.domain.exception.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingParameter(MissingServletRequestParameterException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setType(URI.create("https://problems.prices-api.com/missing-parameter"));
        problem.setTitle("Missing Parameter");
        problem.setDetail("Required parameter '" + ex.getParameterName() + "' is missing");
        return problem;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setType(URI.create("https://problems.prices-api.com/invalid-parameter"));
        problem.setTitle("Invalid Parameter");
        problem.setDetail("Parameter '" + ex.getName() + "' must be of type " + ex.getRequiredType().getSimpleName());
        return problem;
    }

    @ExceptionHandler(PriceNotFoundException.class)
    public ProblemDetail handlePriceNotFoundException(PriceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setType(URI.create("https://problems.prices-api.com/price-not-found"));
        problem.setTitle("Price Not Found");
        problem.setDetail(String.format("No price found for brandId=%d and productId=%d", ex.getBrandId(), ex.getProductId()));
        return problem;
    }
}
