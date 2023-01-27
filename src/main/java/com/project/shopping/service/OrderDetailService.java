package com.project.shopping.service;

import com.project.shopping.model.OrderDetail;
import com.project.shopping.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail create(OrderDetail orderDetail){
        return orderDetailRepository.save(orderDetail);
    }
}
