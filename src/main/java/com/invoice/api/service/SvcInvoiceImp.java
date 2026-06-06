package com.invoice.api.service;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.invoice.api.dto.*;
import com.invoice.api.entity.*;
import com.invoice.api.repository.*;
import com.invoice.commons.util.JwtDecoder;
import com.invoice.exception.ApiException;
import com.invoice.exception.DBAccessException;

@Service
public class SvcInvoiceImp implements SvcInvoice {

    @Autowired
    private RepoInvoice repo;

    @Autowired
    private RepoCartItem repoCartItem;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public List<DtoInvoiceList> findAll() {
        return null; // no afecta punto 2
    }

    @Override
    public Invoice findById(String id) {
        return null; // no afecta punto 2
    }

    @Override
    @Transactional
    public DtoInvoiceCreated create() {

        try {

            Integer userId = jwtDecoder.getUserId();

            List<CartItem> cartItems =
                    repoCartItem.findAllByUser_id(userId);

            if (cartItems.isEmpty()) {
                throw new ApiException(
                        HttpStatus.BAD_REQUEST,
                        "El carrito está vacío"
                );
            }

            RestTemplate restTemplate = new RestTemplate();

            String invoiceId = UUID.randomUUID().toString();

            double total = 0.0;
            double taxes = 0.0;
            double subtotal = 0.0;

            List<InvoiceItem> invoiceItems = new ArrayList<>();

            List<DtoInvoiceItem> dtoItems = new ArrayList<>();

            for (CartItem cartItem : cartItems) {

                ResponseEntity<Map> response =
                        restTemplate.exchange(
                                "http://localhost:8081/product/gtin/" + cartItem.getGtin(),
                                HttpMethod.GET,
                                HttpEntity.EMPTY,
                                Map.class
                        );

                Map product = response.getBody();

                if (product == null) {
                    throw new ApiException(
                            HttpStatus.NOT_FOUND,
                            "Producto no encontrado: " + cartItem.getGtin()
                    );
                }

                Integer stock = Integer.parseInt(product.get("stock").toString());

                if (stock < cartItem.getQuantity()) {
                    throw new ApiException(
                            HttpStatus.BAD_REQUEST,
                            "Stock insuficiente para " + cartItem.getProduct_name()
                    );
                }

                double itemTotal = cartItem.getUnit_price() * cartItem.getQuantity();
                double itemTaxes = itemTotal * 0.16;
                double itemSubtotal = itemTotal - itemTaxes;

                InvoiceItem item = new InvoiceItem();
                item.setInvoice_item_id(UUID.randomUUID().toString());
                item.setInvoice_id(invoiceId);
                item.setGtin(cartItem.getGtin());
                item.setQuantity(cartItem.getQuantity());
                item.setUnit_price(cartItem.getUnit_price());
                item.setSubtotal(itemSubtotal);
                item.setTaxes(itemTaxes);
                item.setTotal(itemTotal);

                invoiceItems.add(item);

                dtoItems.add(new DtoInvoiceItem(
                        cartItem.getGtin(),
                        cartItem.getQuantity(),
                        cartItem.getUnit_price(),
                        itemSubtotal,
                        itemTaxes,
                        itemTotal
                ));

                total += itemTotal;
                taxes += itemTaxes;
                subtotal += itemSubtotal;
            }

            Invoice invoice = new Invoice();
            invoice.setInvoice_id(invoiceId);
            invoice.setUser_id(userId);
            invoice.setCreated_at(LocalDateTime.now().toString());
            invoice.setSubtotal(subtotal);
            invoice.setTaxes(taxes);
            invoice.setTotal(total);
            invoice.setItems(invoiceItems);

            repo.save(invoice);

            for (CartItem cartItem : cartItems) {

                Map<String, Integer> body = new HashMap<>();
                body.put("quantity", cartItem.getQuantity());

                restTemplate.exchange(
                        "http://localhost:8081/product/stock/" + cartItem.getGtin(),
                        HttpMethod.PATCH,
                        new HttpEntity<>(body),
                        String.class
                );
            }
            repoCartItem.deleteAllByUser_id(userId);

            return new DtoInvoiceCreated(
                    invoiceId,
                    subtotal,
                    taxes,
                    total,
                    dtoItems
            );

        } catch (ApiException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new DBAccessException();
        }
    }
}