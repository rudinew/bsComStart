package com.bs.backend.service;



import com.bs.backend.domain.Actions;
import com.bs.backend.domain.Users;
import com.bs.backend.repositories.ActionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActionsServiceImpl implements ActionsService {
    @Autowired
    private ActionsRepository actionsRepository;


    @Override
    public Actions getActionByOne(Long cId) {
        return actionsRepository.findOne(cId);
    }

    @Override
    public List<Actions> getActionsAll() {
        return actionsRepository.findAll();
    }

    @Override
    public List<Actions> getActionsByUsersOrderByDtFromAsc(Users users) {
        return actionsRepository.findAllByUsersOrderByDtFromAsc(users);
    }

    @Override
    public List<Actions> getActionsAllOrderByDtFromAsc() {
        return actionsRepository.findAllByOrderByDtFromAsc();
    }

    @Override
    public void saveAction(Actions action) {
        actionsRepository.save(action);
    }

    @Override
    public void saveAndFlushActions(Actions action) {
        actionsRepository.saveAndFlush(action);
    }
}
