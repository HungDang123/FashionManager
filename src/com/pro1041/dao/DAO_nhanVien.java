/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.dao;

import Model.nhanVien;
import com.pro1041.util.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hnhut
 */
public class DAO_nhanVien {

    String sql_getAll = "Select * from nhanVien";

    public List<nhanVien> getALL_nv() {
        try {
        List<nhanVien> listNV = new ArrayList<>();
//       Gọi connection
        Object[] params = null;
        ResultSet rs = (ResultSet) jdbcHelper.prepareStatement(sql_getAll);
        
            while (rs.next()) {
                nhanVien nhanVien = new nhanVien();
                nhanVien.setMaNhanVien(rs.getString("manhanvien"));
                nhanVien.setHoVaTen(rs.getString("hovaten"));
                nhanVien.setMatKhau(rs.getString("matkhau"));
                nhanVien.setChucVu(rs.getBoolean("chucvu"));
                nhanVien.setGioiTinh(rs.getBoolean("gioitinh"));
                nhanVien.setNgaySinh(rs.getDate("ngaysinh"));
                nhanVien.setCanCuocCongDan(rs.getString("cancuoccongdan"));
                nhanVien.setSoDienThoai(rs.getString("sodienthoai"));
                nhanVien.setHinhAnh(rs.getString("hinhanh"));
                
                listNV.add(nhanVien);
            }
            
            return listNV;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_nhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
