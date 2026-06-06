package com.invoice.api.dto;

public class DtoInvoiceItem {

    private String gtin;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
    private Double taxes;
    private Double total;

    public DtoInvoiceItem() {}

    public DtoInvoiceItem(String gtin, Integer quantity, Double unitPrice, Double subtotal, Double taxes, Double total) {
        this.gtin = gtin;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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
}