package com.phocos.product.model;

import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCartItem, Integer> {
    // 这里可以添加自定义的查询方法或其他需要的方法
}