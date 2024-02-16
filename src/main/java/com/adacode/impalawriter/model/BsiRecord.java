package com.adacode.impalawriter.model;

import javax.persistence.Entity;
import java.sql.*;

@Entity
public class BsiRecord {   
    public BsiRecord() {}

    public Date as_of_date;
    public String cif_no;
    public String cabangbsi;
    public Integer number_of_phr;
    public String bpo_1;
    public String bpo_2;
    public String bpo_3;
    public String bpo_4;
    public String bpo_5;
    public String bpo_6;
    public String bpo_7;
    public String bpo_8;
    public String bpo_9;
    public String bpo_10;
    public Byte ureg;
    public Byte usag;
    public Byte ziswaf;
}
