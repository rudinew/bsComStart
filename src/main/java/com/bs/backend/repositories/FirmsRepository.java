package com.bs.backend.repositories;

import com.bs.backend.domain.Firms;
import com.bs.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirmsRepository  extends JpaRepository<Firms, Long> {

    //перевірка чи можна по користувачу
    List<Firms> findAllByUsersOrderByNameAsc(Users user);

    List<Firms> findAllByOrderByNameAsc();
}
