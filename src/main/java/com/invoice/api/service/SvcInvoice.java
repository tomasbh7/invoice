package com.invoice.api.service;

import java.util.List;

import com.invoice.api.dto.DtoInvoiceCreated;
import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.entity.Invoice;

public interface SvcInvoice {

    List<DtoInvoiceList> findAll();

    Invoice findById(String id);

    DtoInvoiceCreated create();
}