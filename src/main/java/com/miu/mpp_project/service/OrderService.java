package com.miu.mpp_project.service;


import com.miu.mpp_project.enums.OrderStatusEnum;
import com.miu.mpp_project.enums.ResultEnum;
import com.miu.mpp_project.exceptions.MyException;
import com.miu.mpp_project.model.OrderMain;
import com.miu.mpp_project.model.Product;
import com.miu.mpp_project.repository.OrderRepository;
import com.miu.mpp_project.repository.ProductRepository;
import com.miu.mpp_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;



    public List<OrderMain> findAll() {
        return orderRepository.findAll();
    }


    public List<OrderMain> findByStatus(Integer status) {
        return orderRepository. findAllByOrderStatusOrderByCreateTimeDesc(status);
    }


    public List<OrderMain> findByBuyerEmail(String email) {
        return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email);
    }


    public List<OrderMain> findByBuyerPhone(String phone, Pageable pageable) {
        return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone);
    }


    public OrderMain findOne(Long orderId) {
        OrderMain orderMain = orderRepository.findByOrderId(orderId);
        if(orderMain == null) {
            throw new MyException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }


    @Transactional
    public OrderMain finish(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }


    @Transactional
    public OrderMain cancel(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);

        // todo later Restore Stock
        /*
        Iterable<Product> products = orderMain.getProducts();

        for(Product product : products) {
            Product product = productRepository.findById(product.getId());
            if(product != null) {
                productService.increaseStock(product.getId(), product.getCount());
            }
        }*/
        return orderRepository.findByOrderId(orderId);

    }
}