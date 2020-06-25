package com.codegym.repository;

import com.codegym.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Page<Order> findAllByCustomerId(Long customerId, Pageable pageable);
}