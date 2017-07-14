package com.bs.backend.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="BS_FIRMS")
public class Firms  extends BaseEntity implements Serializable {

    private String name;
    //Пользователь
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    //дата
    @Column(name = "dt_from")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dtFrom;

    public Firms() {
    }

    public Firms(String name, Users users) {
        this.name = name;
        this.users = users;
        this.dtFrom = new LocalDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
