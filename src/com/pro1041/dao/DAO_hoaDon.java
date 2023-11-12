/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.pro1041.dao;

import Model.hoaDon;
import Model.khachHang;
import Model.nhanVien;
import com.pro1041.util.jdbcHelper;
import java.sql.*;
/**
 *
 * @author HUNG
 */
public class DAO_hoaDon {

    private final String insertHoaDon = "insert into hoaDon(maHoaDon,maNhanVien,maKhachHang) values (?,?,?)";
    private final String deleteHd = "Delete from hoaDon where maHoaDon = ?";
    private final String findByMaHD = "Select * from hoaDon where maHoaDon = ?";
    private final String findByMaNV = "Select * from nhanVien where maNhanVien = ?";
    private final String findByMaKH = "Select * from khachHang where maKhachHang = ?";
    
    
    public void insertHoaDon(hoaDon hd) {
        try {
            jdbcHelper.executeUpdate(insertHoaDon, hd.getMaHoaDon(), hd.getMaNhanVien().getMaNhanVien(), hd.getMaKhachHang().getMaKhachHang());
        } catch (Exception e) {
            System.out.println("Insert hóa đơn:. " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteHoaDon(String id) {
        try {
            jdbcHelper.executeUpdate(deleteHd, id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Delete hoa don: " + e.getMessage());
        }
    }
    public nhanVien findByMaNV(String id){
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByMaNV, id);
            if(rs.next()){
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
                
                return nhanVien;
            }
        } catch (Exception e) {
            System.out.println("Find by ID: "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public khachHang findByMaKH(String id){
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByMaKH, id);
            while(rs.next()){
                String maKhachHang = rs.getString("maKhachHang");
                String hoVaTen = rs.getString("hoVaTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String soDienThoai = rs.getString("soDienThoai");
                String email = rs.getString("email");
                khachHang kh = new khachHang(maKhachHang, hoVaTen, ngaySinh, gioiTinh, soDienThoai, email);
                return kh;
            }
        } catch (Exception e) {
            System.out.println("FindByMaKH: "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public hoaDon findByMaHD(String id){
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByMaHD, id);
            while(rs.next()){
                String MaHoaDon = rs.getString("maHoaDon");
                String MaNhanVien = rs.getString("maNhanVien");
                String MaKhachHang = rs.getString("maKhachHang");
                hoaDon hd = new hoaDon(MaHoaDon, findByMaNV(MaNhanVien), findByMaKH(MaKhachHang));
                return hd;
            }
        } catch (Exception e) {
            System.out.println("Find by ma hd:" +e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
