package com.bs.backend.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
//журнал дій
@Entity
@Table(name="BS_ACTIONS")
public class Actions extends BaseEntity implements Serializable {

    private String name;
    private String action;
    //Пользователь
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    //дата
    @Column(name = "dt_from")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dtFrom;

    public Actions() {
    }

    public Actions(Users users, String action, String name) {
        this.users = users;
        this.action = action;
        this.name = name;
        this.dtFrom = new LocalDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public LocalDate getDtFrom() {
        return dtFrom;
    }

    public void setDtFrom(LocalDate dtFrom) {
        this.dtFrom = dtFrom;
    }
}
