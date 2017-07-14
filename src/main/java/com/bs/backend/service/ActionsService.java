package com.bs.backend.service;

import com.bs.backend.domain.Actions;
import com.bs.backend.domain.Firms;
import com.bs.backend.domain.Users;

import java.util.List;

public interface ActionsService {
    //spring jpa data query
    //https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-introduction-to-query-methods/

    Actions getActionByOne(Long cId);
    List<Actions> getActionsAll();
    List<Actions> getActionsByUsersOrderByDtFromAsc(Users users);
    List<Actions> getActionsAllOrderByDtFromAsc();
    void saveAction(Actions action);
    void saveAndFlushActions(Actions action);
  //  void deleteAction(Long cId);




}

