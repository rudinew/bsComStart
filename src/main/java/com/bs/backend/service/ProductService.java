package com.bs.backend.service;

import com.bs.backend.domain.Product;
import com.bs.backend.domain.Users;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface ProductService {
    //spring jpa data query
    //https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-introduction-to-query-methods/

    Product getProductByOne(Long cId);
    List<Product> getProductAll();
    List<Product> getProductByUsersOrderByNameAsc(Users users);
    List<Product> getParentProduct(Users users);
    List<Product> getProductAllOrderByNameAsc();
    void saveProduct(Product product, User user);
    void saveAndFlushProduct(Product product, User user);
    void deleteProduct(Long cId);




}

