package com.adacode.impalawriter;


import java.sql.DriverManager;
import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adacode.impalawriter.model.*;
import java.sql.*;

@RestController
public class WriterController {
    static final String ClassName = "com.cloudera.impala.jdbc41.Driver";
    static final String Host = "jdbc:impala://13.214.19.24:21050/bsi";
    
    WriterController() {
    }

    @GetMapping("/v1/health")
    String health() {
        return "OK";
    }

    String sendStringToSQL(String dt) {
        if (dt==null) return "null";
        if (dt.startsWith("NULL")) return "null";
        return String.format("'%s'", dt);
    }

    @PostMapping("/v1/write")
    String write(@RequestBody BsiRecord record) {
        try {   
            Class.forName(ClassName);
            Connection conn = DriverManager.getConnection(Host);
            Statement stmt = conn.createStatement();
            String sqlCmd = String.format("insert into comforte(as_of_date, cif_no, cabangbsi, number_of_phr, "+
            "bpo_1, bpo_2, bpo_3, bpo_4, bpo_5, bpo_6, bpo_7, bpo_8, bpo_9, bpo_10, ureg, usak, ziswaf) "+
            "values('%s','%s','%s',%d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %d, %d, %d)",
                    record.as_of_date, record.cif_no, record.cabangbsi, record.number_of_phr, 
                    sendStringToSQL(record.bpo_1), 
                    sendStringToSQL(record.bpo_2), 
                    sendStringToSQL(record.bpo_3), 
                    sendStringToSQL(record.bpo_4), 
                    sendStringToSQL(record.bpo_5), 
                    sendStringToSQL(record.bpo_6), 
                    sendStringToSQL(record.bpo_7), 
                    sendStringToSQL(record.bpo_8), 
                    sendStringToSQL(record.bpo_9), 
                    sendStringToSQL(record.bpo_10), 
                    record.ureg, record.usag, record.ziswaf);
            System.out.printf("execute command: %s\n",sqlCmd);
            stmt.execute(sqlCmd);
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "NOK";
        } catch (SQLException e) {
            e.printStackTrace();
            return "NOK";
        }

        return "OK";
    }

    @GetMapping("/v1/read")
    ReadRespond read() {
        ReadRespond res = new ReadRespond();

        try {   
            Class.forName(ClassName);
            Connection conn = DriverManager.getConnection(Host);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from comforte");

            List<BsiRecord> lst = new ArrayList<BsiRecord>();
            Integer count = 0;
            while(rs.next()) {
                BsiRecord r = new BsiRecord();
                r.as_of_date = rs.getDate("as_of_date");
                r.cif_no = rs.getString("cif_no");
                r.cabangbsi = rs.getNString("cabangbsi");
                r.number_of_phr = rs.getInt("number_of_phr");
                r.bpo_1 = rs.getString("bpo_1");
                r.bpo_2 = rs.getString("bpo_2");
                r.bpo_3 = rs.getString("bpo_3");
                r.bpo_4 = rs.getString("bpo_4");
                r.bpo_5 = rs.getString("bpo_5");
                r.bpo_6 = rs.getString("bpo_6");
                r.bpo_7 = rs.getString("bpo_7");
                r.bpo_8 = rs.getString("bpo_8");
                r.bpo_9 = rs.getString("bpo_9");
                r.bpo_10 = rs.getString("bpo_10");
                r.usag = rs.getByte("usag");
                r.ureg = rs.getByte("ureg");
                r.ziswaf = rs.getByte("ziswaf");
                lst.add(r);
                count++;
            }
            res.count = count;
            res.records = lst.toArray(new BsiRecord[0]);

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
