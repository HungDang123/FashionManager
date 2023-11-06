/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.util;

import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.*;

/**
 *
 * @author HUNG
 */
public class jdbcHelper {

    static String url = "jdbc:sqlserver://localhost:1433;databaseName=Polypro;encrypt=false";
    static String user = "sa";
    static String pass = "hung";

    static {
        try {
        } catch (Exception e) {
        }
    }

    public static PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        Connection con = DriverManager.getConnection(url, user, pass);
        PreparedStatement pr = null;
        if (sql.trim().startsWith("{")) {
            pr = con.prepareCall(sql);
        } else {
            pr = con.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pr.setObject(i + 1, args[i]);
        }
        return pr;
    }
    public static void executeUpdate(String sql,Object...args){
        try {
            PreparedStatement pr = prepareStatement(sql, args);
            try {
                pr.executeUpdate();
            } finally {
                pr.getConnection().close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static ResultSet executeQuery(String sql,Object...args){
        try {
            PreparedStatement pr = prepareStatement(sql, args);
            return  pr.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    } 
}
