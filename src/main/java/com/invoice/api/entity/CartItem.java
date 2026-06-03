package com.invoice.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cart_item_id;

    @Column(name = "user_id")
    private Integer userId;

    private String gtin;

    private String product_name;

    private Double unit_price;

    private Integer quantity;

    public CartItem() {}

    public CartItem(Integer userId, String gtin, String product_name, Double unit_price, Integer quantity) {
        this.userId = userId;
        this.gtin = gtin;
        this.product_name = product_name;
        this.unit_price = unit_price;
        this.quantity = quantity;
    }

    public Integer getCart_item_id() { return cart_item_id; }
    public void setCart_item_id(Integer cart_item_id) { this.cart_item_id = cart_item_id; }

    public Integer getUser_id() { return userId; }
    public void setUser_id(Integer userId) { this.userId = userId; }

    public String getGtin() { return gtin; }
    public void setGtin(String gtin) { this.gtin = gtin; }

    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }

    public Double getUnit_price() { return unit_price; }
    public void setUnit_price(Double unit_price) { this.unit_price = unit_price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}