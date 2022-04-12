package com.miu.mpp_project.repository;

import com.miu.mpp_project.model.OrderMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderMain,Integer> {

    OrderMain findByOrderId(Long orderId);

    List<OrderMain> findAllByOrderStatusOrderByCreateTimeDesc(Integer orderStatus);


    List<OrderMain> findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(String buyerEmail);

    List<OrderMain> findAllByOrderByOrderStatusAscCreateTimeDesc();

    List<OrderMain> findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(String buyerPhone);
}
