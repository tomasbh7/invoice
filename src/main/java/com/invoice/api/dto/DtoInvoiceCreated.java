package com.invoice.api.dto;

import java.util.List;

public class DtoInvoiceCreated {

    private String invoiceId;
    private Double subtotal;
    private Double taxes;
    private Double total;
    private List<DtoInvoiceItem> items;

    public DtoInvoiceCreated() {}

    public DtoInvoiceCreated(String invoiceId, Double subtotal, Double taxes, Double total, List<DtoInvoiceItem> items) {
        this.invoiceId = invoiceId;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
        this.items = items;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DtoInvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<DtoInvoiceItem> items) {
        this.items = items;
    }
}