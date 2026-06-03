package com.invoice.api.dto;

public class DtoCartItem {

    private String gtin;
    private Integer quantity;

    public DtoCartItem() {}

    public String getGtin() { return gtin; }
    public void setGtin(String gtin) { this.gtin = gtin; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}