package com.sumscope.qpwb.ncd.service;

import com.mysql.jdbc.StatementImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class JdbcUtil {
    @Autowired
    private static Connection connection = null;
    private static StatementImpl statement = null;

    /**
     *
     * @param list 拼接好的 sql 语句
     */
    public static void save(List<String> list){
        if(CollectionUtils.isEmpty(list)) return;

        try {
            start();
            int count = 0;
            while (count < list.size()){
                statement.addBatch(list.get(count));
                count++;
            }
            save0();
        } catch (Exception e) {
        }finally {
            close();
        }
    }

    private static void save0() throws SQLException {
        if(CollectionUtils.isEmpty(statement.getBatchedArgs())) return;
        statement.executeBatch();
        connection.commit();
        statement.clearBatch();
    }

    private static void start() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://172.16.66.120:3306/wf_web_ncd_test?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true",
                "root",
                "123456");
        connection.setAutoCommit(false);
        statement = (StatementImpl)connection.createStatement();
    }

    private static void close(){
        if(statement != null) statement = null;
        if(connection != null) connection = null;
    }
}
