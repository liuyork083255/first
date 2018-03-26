package com.sumscope.cdh.webbond.test;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

@Path("/test")
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);
    private static String url = "jdbc:mysql://172.16.73.102:3306/ss_product_copy?user=root&password=sumscope&useUnicode=true&characterEncoding=utf8";
    // private static String url = "jdbc:mysql://172.16.66.61:3306/ss_product_copy?user=root&password=&useUnicode=true&characterEncoding=utf8";
    private static String query = "select Bond_Key,Bond_Type,Bond_Subtype,Bond_ID,Interest_Start_Date,Maturity_Date,Coupon_Frequency,Currency,Interest_Basis,First_Coupon_Date,Coupon_Type,Redemption_Str,Coupon_Rate_Spread,FRN_Index_ID,Fixing_MA_Days,Fixing_Preceds,Fixing_Digit,Cap,Flr,Option_Style,Option_Type,Compensate_From,Compensate_Rate,Call_Str,Put_Str,Assign_Trans_Str,Issue_Price,Issue_Rate from bond where delflag = 0  limit 0,10000";
    private static Connection conn = null;
    private JsonFactory factory = new JsonFactory();

    public Test() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(null, e);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getString() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int columns = rs.getMetaData().getColumnCount();
            StringWriter writer = new StringWriter();
            JsonGenerator generator = factory.createGenerator(writer);
            generator.writeStartArray();
            while (rs.next()) {
                generator.writeStartObject();
                for (int i = 1; i <= columns; ++i) {
                    generator.writeFieldName(rs.getMetaData().getColumnName(i));
                    generator.writeObject(rs.getObject(i));
                }
                generator.writeEndObject();
            }
            generator.writeEndArray();
            generator.flush();
            return writer.toString();
        } catch (SQLException | IOException e) {
        	logger.error(null, e);
        }
        return "";
    }

}
