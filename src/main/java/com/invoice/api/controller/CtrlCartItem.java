package com.invoice.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoCartItem;
import com.invoice.api.entity.CartItem;
import com.invoice.api.service.SvcCartItem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cart-item")
@Tag(name = "Cart Item", description = "Administración del carrito de compras")
public class CtrlCartItem {

    @Autowired
    SvcCartItem svc;

    @PostMapping
    @Operation(summary = "Agregar al carrito", description = "Agrega un producto al carrito de compras")
    public ResponseEntity<ApiResponse> add(@RequestBody DtoCartItem dto) {
        return ResponseEntity.ok(svc.add(dto));
    }

    @GetMapping
    @Operation(summary = "Consultar carrito", description = "Consulta los productos en el carrito")
    public ResponseEntity<List<CartItem>> findAll() {
        return ResponseEntity.ok(svc.findAll());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar artículo", description = "Elimina un artículo del carrito por id")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(svc.deleteById(id));
    }

    @DeleteMapping
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los artículos del carrito")
    public ResponseEntity<ApiResponse> deleteAll() {
        return ResponseEntity.ok(svc.deleteAll());
    }
}