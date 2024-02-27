package com.adacode.impalawriter.model;

import java.sql.Date;

import javax.persistence.Entity;

@Entity
public class ComforteProtection {   
    public ComforteProtection() {}

    public String name;
    public String address;
    public String email;
    public String cif;
    public String phone;
    public Date join_date;
}
