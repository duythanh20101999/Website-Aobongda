package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Order[] getOrderById(Order order);
}
