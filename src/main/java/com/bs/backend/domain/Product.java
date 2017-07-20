package com.bs.backend.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="BS_PRODUCT")
public class Product extends BaseEntity implements Serializable {

    private String name;
    private String unit;
    //Група ТМЦ
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Product parentProduct;
    //Пользователь
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    //дата
    @Column(name = "dt_from")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dtFrom;

    public Product() {
    }

    public Product(String name, String unit, Product parentProduct, Users users) {
        this.name = name;
        this.unit = unit;
        this.parentProduct = parentProduct;
        this.users = users;
        this.dtFrom = new LocalDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Product getParentProduct() {
        return parentProduct;
    }

    public void setParentProduct(Product parentProduct) {
        this.parentProduct = parentProduct;
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
