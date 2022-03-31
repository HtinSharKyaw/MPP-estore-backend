package com.miu.mpp_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miu.mpp_project.model.Product;
/**
 * @author HtinSharKyaw
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

}
