/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.nhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hnhut
 */
public class JdbcConnect {

    static final String sql_connection = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=quanLyCuaHangThoiTrang;user=sa;password=1;encrypt=false";
    static Connection c;
    static PreparedStatement pst;

    public void openConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(sql_connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql, Object... objects) {
        if (c == null) {
            openConnection();
        }
        try {
            pst = c.prepareStatement(sql);

            // Thiết lập giá trị cho các tham số trong PreparedStatement
            if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                pst.setObject(i + 1, objects[i]); 
            }
            }

            // Thực hiện truy vấn SQL và trả về ResultSet
            return pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(JdbcConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          
        }
        return null;
    }

    public void closeConnection() {
        if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (c != null) {
                closeConnection();
            }
    }
}
