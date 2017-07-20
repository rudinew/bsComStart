package com.bs.backend.repositories;

import com.bs.backend.domain.Product;
import com.bs.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //перевірка чи можна по користувачу
    List<Product> findAllByUsersOrderByNameAsc(Users user);

    List<Product> findAllByOrderByNameAsc();

    //Только паренты
    List<Product> findAllByUsersAndParentProductIsNullOrderByNameAsc(Users user);
}
