package com.invoice.api.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoCartItem;
import com.invoice.api.entity.CartItem;
import com.invoice.api.repository.RepoCartItem;
import com.invoice.commons.util.JwtDecoder;
import com.invoice.exception.ApiException;
import com.invoice.exception.DBAccessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.context.request.*;

@Service
public class SvcCartItemImp implements SvcCartItem {

    @Autowired
    private RepoCartItem repo;

    @Autowired
    private JwtDecoder jwtDecoder;

    private static final String PRODUCT_URL = "http://localhost:8081/product/gtin/";

    @Override
    public ApiResponse add(DtoCartItem dto) {
        try {

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<java.util.Map> response;
            try {
                response = restTemplate.exchange(
                    PRODUCT_URL + dto.getGtin(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    java.util.Map.class
                );
            } catch (Exception e) {
                System.out.println("ERROR AL CONSULTAR PRODUCTO: " + e.getMessage());
                throw new ApiException(HttpStatus.NOT_FOUND, "El producto no existe");
            }

            java.util.Map product = response.getBody();

            if (product == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El producto no existe");
            }

            Integer stock = (Integer) product.get("stock");
            if (stock == null || stock < dto.getQuantity()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Stock insuficiente para el producto");
            }

            String productName = (String) product.get("product");
            Double unitPrice = Double.valueOf(product.get("price").toString());

            Integer userId = jwtDecoder.getUserId();

            Optional<CartItem> existing = repo.findByUser_idAndGtin(userId, dto.getGtin());
            if (existing.isPresent()) {
                CartItem item = existing.get();
                item.setQuantity(dto.getQuantity());
                repo.save(item);
                return new ApiResponse("Cantidad actualizada en el carrito");
            }

            CartItem newItem = new CartItem(userId, dto.getGtin(), productName, unitPrice, dto.getQuantity());
            repo.save(newItem);
            return new ApiResponse("Producto agregado al carrito");

        } catch (ApiException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }

    @Override
    public List<CartItem> findAll() {
        try {
            Integer userId = jwtDecoder.getUserId();
            return repo.findAllByUser_id(userId);
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }

    @Override
    public ApiResponse deleteById(Integer id) {
        try {
            if (!repo.existsById(id)) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El artículo no existe en el carrito");
            }
            repo.deleteById(id);
            return new ApiResponse("Artículo eliminado del carrito");
        } catch (ApiException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }

    @Override
    public ApiResponse deleteAll() {
        try {
            Integer userId = jwtDecoder.getUserId();
            repo.deleteAllByUser_id(userId);
            return new ApiResponse("Carrito vaciado");
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
}