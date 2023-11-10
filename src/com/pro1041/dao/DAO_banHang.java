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

    sanPham sp;
    khachHang kh;
    private final String selectSanPham = "Select * From sanPham";
    private final String findByNumber = "Select * from khachHang where soDienThoai like ?";
    private final String insertKhachHang = "insert into khachHang(maKhachHang,hoVaTen,ngaySinh,gioiTinh,soDienThoai,email)"
            + " values (?,?,?,?,?,?)";
    private final String SearchByName = "select sp.* from sanPham sp where sp.tenSanPham"
            + " COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%' ";
    private final String SearchById = "select sp.* from sanPham sp where sp.maSanPham "
            + " LIKE N'%' + ? + N'%' ";
    private final String SearchBoth = "select sp.* from sanPham sp where sp.tenSanPham"
            + " COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%' "
            + "and sp.maSanPham LIKE N'%' + ? + N'%'";
    private final String findByIdKH = "SELECT * FROM khachHang WHERE maKhachHang = ?";

    public List<sanPham> SelectSanPham() {
        List<sanPham> list = new ArrayList<>();
        list.clear();
        try {
            ResultSet rs = jdbcHelper.prepareStatement(selectSanPham).executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, vat, nhaCungCap, moTa, mauSac, hinhAnh);
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Select sanPham:. " + e.getMessage());
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

    public List<sanPham> searchByName(String str) {
        List<sanPham> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(SearchByName, str);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, vat, nhaCungCap, moTa, mauSac, hinhAnh);
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("searchByName: " + e.getMessage());
        }
        return list;
    }
    public List<sanPham> searchById(String str) {
        List<sanPham> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(SearchById, str);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, vat, nhaCungCap, moTa, mauSac, hinhAnh);
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("searchByName: " + e.getMessage());
        }
        return list;
    }

    public List<sanPham> searchBoth(String name, String id) {
        List<sanPham> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(SearchBoth, name, id);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, vat, nhaCungCap, moTa, mauSac, hinhAnh);
                list.add(sp);
            }

        } catch (Exception e) {
            System.out.println("search both: " + e.getMessage());
        }
        return list;
    }

    public khachHang findByIdKh(String k) {
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByIdKH, k);
            if (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                String tenKhachHang = rs.getString("hoVaTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String sdt = rs.getString("soDienThoai");
                String email = rs.getString("email");
                khachHang kh = new khachHang(maKhachHang, tenKhachHang, ngaySinh, gioiTinh, sdt, email);
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FindByIdKh :" + e.getMessage());
        }
        return null;
    }
}
