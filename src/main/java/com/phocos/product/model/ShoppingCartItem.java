package com.phocos.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ShoppingCartItem")
public class ShoppingCartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartid")
	private int cartid;
	
	@Column(name = "productId")
    private int productId;
    
	@Column(name = "model")
    private String model;
    
	@Column(name = "brand")
    private String brand;
    
	@Column(name = "price")
    private double price;
    

    // 构造函数、getter和setter方法

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "productId=" + productId +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
