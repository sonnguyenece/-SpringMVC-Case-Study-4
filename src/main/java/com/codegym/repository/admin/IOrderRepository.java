package com.codegym.repository.admin;

import com.codegym.model.admin.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends PagingAndSortingRepository<Order,Long> {

    Page<Order>findAllByReceiverNameContaining(String search,Pageable pageable);

}