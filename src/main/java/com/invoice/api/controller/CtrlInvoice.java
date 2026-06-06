package com.invoice.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoInvoiceCreated;
import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.entity.Invoice;
import com.invoice.api.service.SvcInvoice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/invoice")
@Tag(name = "Invoice", description = "Administración de facturas")
public class CtrlInvoice {

    @Autowired
    SvcInvoice svc;

    @GetMapping
    @Operation(summary = "Consulta de facturas")
    public ResponseEntity<List<DtoInvoiceList>> findAll() {
        return ResponseEntity.ok(svc.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(svc.findById(id));
    }

    @PostMapping
    @Operation(summary = "Creación de factura")
    public ResponseEntity<DtoInvoiceCreated> create() {
        return ResponseEntity.ok(svc.create());
    }
}