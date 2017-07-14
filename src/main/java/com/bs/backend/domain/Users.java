package com.bs.backend.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="bs_users")
public class Users extends BaseEntity {

    @Size(min=1)
    @Column(unique=true)
    private String login;
    @NotNull
    @Size(min=1)
    @Column(name = "l_name")
    private String lastname;
    @NotNull
    @Size(min=1)
    @Column(name = "f_name")
    private String firstname;
    @NotNull
    @Size(min=1)
    @Column(name = "m_name")
    private String middlename;
    @NotNull
    @Column(name = "dt_birth")
    // @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate datebirth;
    private short gender;
    private String phone;
    @Column(name = "e_mail")
    private String email;
    private String password;
    private String confirmPassword;
    private Boolean is_active;
    @Enumerated(EnumType.STRING)
    private UserRole role;


    //дата создания/модификации
    @Column(name = "dt_from")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dtFrom;


    public Users() {
        this.dtFrom = new LocalDate();
    }

    public Users(String login, String lastname, String firstname, String middlename, LocalDate datebirth, short gender, String phone, String email, String password, String confirmPassword, Boolean is_active, UserRole role) {
        this.login = login;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.datebirth = datebirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.is_active = is_active;
        this.role = role;
        this.dtFrom = new LocalDate();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public LocalDate getDatebirth() {
        return datebirth;
    }

    public void setDatebirth(LocalDate datebirth) {
        this.datebirth = datebirth;
    }

    public short getGender() {
        return gender;
    }

    public void setGender(short gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDate getDtFrom() {
        return dtFrom;
    }

    public void setDtFrom(LocalDate dtFrom) {
        this.dtFrom = dtFrom;
    }
}
