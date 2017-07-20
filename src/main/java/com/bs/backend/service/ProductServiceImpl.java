package com.bs.backend.service;


import com.bs.backend.domain.Product;
import com.bs.backend.domain.Users;
import com.bs.backend.repositories.ProductRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;


    @Override
    public Product getProductByOne(Long cId) {
        return productRepository.findOne(cId);
    }

    @Override
    public List<Product> getProductAll() {
        return productRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<Product> getProductByUsersOrderByNameAsc(Users users) {
        return productRepository.findAllByUsersOrderByNameAsc(users);
    }

    @Override
    public List<Product> getProductAllOrderByNameAsc() {
        return productRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<Product> getParentProduct(Users users) {
        return productRepository.findAllByUsersAndParentProductIsNullOrderByNameAsc(users);
    }

    @Override
    public void saveProduct(Product product, User user) {
        Users users = userService.getUserByLogin(user.getUsername());
        product.setUsers(users);
        product.setDtFrom(new LocalDate()); //дата зміни (поки тут)
        productRepository.save(product);
    }

    @Override
    public void saveAndFlushProduct(Product product, User user) {
        Users users = userService.getUserByLogin(user.getUsername());
        product.setUsers(users);
        product.setDtFrom(new LocalDate()); //дата зміни (поки тут)
        productRepository.saveAndFlush(product);
    }

    @Override
    public void deleteProduct(Long cId) {
        productRepository.delete(cId);
    }
}
