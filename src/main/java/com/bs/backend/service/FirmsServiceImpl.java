package com.bs.backend.service;


import com.bs.backend.domain.Firms;
import com.bs.backend.domain.Users;
import com.bs.backend.repositories.FirmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class FirmsServiceImpl implements FirmsService {
    @Autowired
    private FirmsRepository firmsRepository;


    @Override
    public Firms getFirmByOne(Long cId) {
        return firmsRepository.findOne(cId);
    }

    @Override
    public List<Firms> getFirmsAll() {
        return firmsRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<Firms> getFirmsByUsersOrderByNameAsc(Users users) {
        return firmsRepository.findAllByUsersOrderByNameAsc(users);
    }

    @Override
    public List<Firms> getFirmsAllOrderByNameAsc() {
        return firmsRepository.findAllByOrderByNameAsc();
    }

    @Override
    public void saveFirm(Firms firms) {
       firmsRepository.save(firms);
    }

    @Override
    public void saveAndFlushFirms(Firms firms) {
       firmsRepository.saveAndFlush(firms);
    }

    @Override
    public void deleteFirm(Long cId) {
       firmsRepository.delete(cId);
    }
}
