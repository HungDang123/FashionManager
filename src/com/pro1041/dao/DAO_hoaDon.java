/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.pro1041.dao;

import Model.hoaDon;
import com.pro1041.util.jdbcHelper;

/**
 *
 * @author HUNG
 */
public class DAO_hoaDon {

    private final String insertHoaDon= "insert into hoaDon(maHoaDon,maNhanVien,maKhachHang) values (?,?,?)";
    
    public void insertHoaDon(hoaDon hd){
        try {
            jdbcHelper.executeUpdate(insertHoaDon, hd.getMaHoaDon(),hd.getMaNhanVien().getMaNhanVien(),hd.getMaKhachHang().getMaKhachHang());
        } catch (Exception e) {
            System.out.println("Insert hóađơn: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
