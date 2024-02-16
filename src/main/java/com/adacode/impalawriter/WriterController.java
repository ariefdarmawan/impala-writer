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

    @PostMapping("/v1/write")
    String write(@RequestBody BsiRecord record) {
        try {   
            Class.forName(ClassName);
            Connection conn = DriverManager.getConnection(Host);
            Statement stmt = conn.createStatement();
            String sqlCmd = String.format("insert into comforte(as_of_date, cif_no) values('%s','%s')",
                    record.as_of_date, record.cif_no);
            System.out.printf("execute command: %s\n",sqlCmd);
            stmt.execute(sqlCmd);
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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
