/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.dao;

import Model.chiTietHoaDon;
import Model.khachHang;
import Model.sanPham;
import com.pro1041.util.jdbcHelper;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HUNG
 */
public class DAO_banHang {

    List<sanPham> list = new ArrayList<>();
    sanPham sp;
    khachHang kh;
    private final String selectSanPham = "Select * From sanPham";
    private final String findByNumber = "Select * from khachHang where soDienThoai = ?";
    private final String insertKhachHang = " insert into khachHang(maKhachHang,hoVaTen,ngaySinh,gioiTinh,soDienThoai,email)"
            + " values ?,?,?,?,?,?";
    private final String Search = "select sp.* from sanPham sp where sp.tenSanPham"
            + " COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%' "
            + "or sp.maSanPham COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%'";
    private final String SearchBoth = "select sp.* from sanPham sp where sp.tenSanPham"
            + " COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%' "
            + "and sp.maSanPham COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%'";

    public List<sanPham> SelectSanPham() {
        try {
            ResultSet rs = (ResultSet) jdbcHelper.prepareStatement(selectSanPham);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, nhaCungCap, moTa, mauSac);
                list.add(sp);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Select sanPham: " + e.getMessage());
        }
        return null;
    }

    public khachHang findByNumber(String sdt) {
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByNumber, sdt);
            while (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                String hoVaTen = rs.getString("hoVaTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String soDienThoai = rs.getString("soDienThoai");
                String email = rs.getString("email");
                kh = new khachHang(maKhachHang, hoVaTen, ngaySinh, gioiTinh, soDienThoai, email);
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FindByNumber: " + e.getMessage());
        }
        return null;
    }

    public void insertKhachHang(khachHang kh) {
        try {
            jdbcHelper.executeUpdate(insertKhachHang, kh.getMaKhachHang(), kh.getHoVaTen(), kh.getNgaySinh(), kh.isGioiTinh(), kh.getSoDienThoai(), kh.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<sanPham> search(String str) {
        try {
            ResultSet rs = jdbcHelper.executeQuery(Search, str);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, nhaCungCap, moTa, mauSac);
                list.add(sp);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("search: " + e.getMessage());
        }
        return null;
    }

    public List<sanPham> searchBoth(String str, String str1) {
        try {
            ResultSet rs = jdbcHelper.executeQuery(SearchBoth, str, str1);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, nhaCungCap, moTa, mauSac);
                list.add(sp);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("search both: " + e.getMessage());
        }
        return null;
    }

}
