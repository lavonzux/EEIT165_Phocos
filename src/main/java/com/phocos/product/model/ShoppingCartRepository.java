package com.phocos.product.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCartItem, Integer> {
	List<ShoppingCartItem> findByMemberID(int memberId);
}