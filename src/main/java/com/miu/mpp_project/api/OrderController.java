package com.miu.mpp_project.api;


import com.miu.mpp_project.model.OrderMain;
import com.miu.mpp_project.model.Product;
import com.miu.mpp_project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/order")
    public List<OrderMain> orderList(Authentication authentication) {
        List<OrderMain> orderList;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderList = orderService.findByBuyerEmail(authentication.getName());
        } else {
            orderList = orderService.findAll();
        }
        return orderList;
    }


    @PatchMapping("/order/cancel/{id}")
    public ResponseEntity<OrderMain> cancel(@PathVariable("id") Long orderId, Authentication authentication) {
        OrderMain orderMain = orderService.findOne(orderId);
        if (!authentication.getName().equals(orderMain.getBuyerEmail()) && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.cancel(orderId));
    }

    @PatchMapping("/order/finish/{id}")
    public ResponseEntity<OrderMain> finish(@PathVariable("id") Long orderId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.finish(orderId));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity show(@PathVariable("id") Long orderId, Authentication authentication) {
        boolean isCustomer = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        OrderMain orderMain = orderService.findOne(orderId);
        if (isCustomer && !authentication.getName().equals(orderMain.getBuyerEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Collection<Product> items = orderMain.getProducts();
        return ResponseEntity.ok(orderMain);
    }
}
