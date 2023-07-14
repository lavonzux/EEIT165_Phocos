package com.phocos.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.product.model.ShoppingCartItem;
import com.phocos.product.model.ShoppingCartRepository;

@Service
public class ShoppingCartService {
    // 注入你的SQL Server数据库访问组件，例如使用JPA或MyBatis等
	@Autowired
	private ShoppingCartRepository shopRepo;

	public void storeShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
		shopRepo.saveAll(shoppingCartItems);
	}
	
	// 取得修改資料
	// 用ID查詢
    public List<ShoppingCartItem> getByMemberId(int memberId) {
        return shopRepo.findByMemberID(memberId);
    }
    
    public double calculateTotalPrice(int memberID) {
        List<ShoppingCartItem> items = shopRepo.findByMemberID(memberID);
        int totalPrice = 0;

        for (ShoppingCartItem item : items) {
            totalPrice += item.getPrice();
        }

        return totalPrice;
    }
    
    public double calculateTotalPrice(List<ShoppingCartItem> shoppingCartItems) {
        int totalPrice = 0;

        for (ShoppingCartItem item : shoppingCartItems) {
            totalPrice += item.getPrice();
        }

        return totalPrice;
    }
}