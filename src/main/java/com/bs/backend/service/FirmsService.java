package com.bs.backend.service;

import com.bs.backend.domain.Firms;
import com.bs.backend.domain.Users;

import java.util.List;

public interface FirmsService {
    //spring jpa data query
    //https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-introduction-to-query-methods/

    Firms getFirmByOne(Long cId);
    List<Firms> getFirmsAll();
    List<Firms> getFirmsByUsersOrderByNameAsc(Users users);
    List<Firms> getFirmsAllOrderByNameAsc();
    void saveFirm(Firms firms);
    void saveAndFlushFirms(Firms firms);
    void deleteFirm(Long cId);




}

