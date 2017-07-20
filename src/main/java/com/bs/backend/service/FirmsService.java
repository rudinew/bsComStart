package com.bs.backend.service;

import com.bs.backend.domain.Firms;
import com.bs.backend.domain.Users;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface FirmsService {
    //spring jpa data query
    //https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-introduction-to-query-methods/

    Firms getFirmByOne(Long cId);
    List<Firms> getFirmsAll();
    List<Firms> getFirmsByUsersOrderByNameAsc(Users users);
    List<Firms> getFirmsAllOrderByNameAsc();
    void saveFirm(Firms firms, User user);
    void saveAndFlushFirms(Firms firms, User user);
    void deleteFirm(Long cId);




}

