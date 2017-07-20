package com.bs.backend.service;


import com.bs.backend.domain.Firms;
import com.bs.backend.domain.Users;
import com.bs.backend.repositories.FirmsRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class FirmsServiceImpl implements FirmsService {
    @Autowired
    private FirmsRepository firmsRepository;

    @Autowired
    private UserService userService;


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
    public void saveFirm(Firms firms, User user) {
        Users users = userService.getUserByLogin(user.getUsername());
        firms.setUsers(users);
        firms.setDtFrom(new LocalDate()); //дата зміни (поки тут)
        firmsRepository.save(firms);
    }

    @Override
    public void saveAndFlushFirms(Firms firms, User user) {
        Users users = userService.getUserByLogin(user.getUsername());
        firms.setUsers(users);
        firms.setDtFrom(new LocalDate()); //дата зміни (поки тут)
        firmsRepository.saveAndFlush(firms);
    }

    @Override
    public void deleteFirm(Long cId) {
       firmsRepository.delete(cId);
    }
}
