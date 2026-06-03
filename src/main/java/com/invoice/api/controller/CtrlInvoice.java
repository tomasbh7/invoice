package com.invoice.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.api.dto.ApiResponse;
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
	@Operation(summary = "Consulta de facturas", description = "Administrador consulta todas las facturas. Cliente consulta sus facturas.")
	public ResponseEntity<List<DtoInvoiceList>> findAll() {	
		return ResponseEntity.ok(svc.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Invoice> findById(@PathVariable("id") String id) {		
	    return ResponseEntity.ok(svc.findById(id));
	}
	
	@GetMapping("/test-token")
	public ResponseEntity<String> testToken() {
	    // Genera un token de prueba manualmente
	    io.jsonwebtoken.Claims claims = io.jsonwebtoken.Jwts.claims();
	    claims.setSubject("testuser");
	    claims.put("id", 1);
	    claims.put("roles", java.util.List.of(java.util.Map.of("authority", "CUSTOMER")));
	    
	    javax.crypto.SecretKey key = new javax.crypto.spec.SecretKeySpec(
	        java.util.Base64.getDecoder().decode("8J+YjvCfpJPwn5ic8J+YmvCfmI3wn6Ww8J+ZgvCfpKM="), 
	        "HmacSHA256"
	    );
	    
	    String token = io.jsonwebtoken.Jwts.builder()
	        .setClaims(claims)
	        .setExpiration(new java.util.Date(System.currentTimeMillis() + 86400000))
	        .signWith(key)
	        .compact();
	    
	    return ResponseEntity.ok(token);
	}
	
	@PostMapping
	@Operation(summary = "Creación de factura", description = "Cliente crea una factura")
	public ResponseEntity<ApiResponse> create(){
		return ResponseEntity.ok(svc.create());
	}
	
}
