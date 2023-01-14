package com.project.shopping.service;

import com.project.shopping.model.OrderDetail;
import com.project.shopping.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderDetail create(OrderDetail orderDetail){
        return orderDetailRepository.save(orderDetail);
    }
}
