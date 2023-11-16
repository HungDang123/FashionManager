/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.pro1041.dao;

import Model.sanPham;
import com.pro1041.util.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HUNG
 */
public class DAO_sanPham implements DAO<sanPham> {

    @Override
    public List<sanPham> getSelectAll() {
        List<sanPham> list = new ArrayList<>();
        try {
            String sql = "exec getAllSanPham";
            ResultSet rs = jdbcHelper.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSanPham");
                String tenSP = rs.getString("tenSanPham");
                String loaiSP = rs.getString("loaiSanPham");
                String xuatXu = rs.getString("xuatSu");
                String donGia = rs.getString("donGia");
                String vat = rs.getString("VAT");
                String nhaCC = rs.getString("nhaCungCap");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                String hinhAnh = rs.getString("hinhAnh");
                sanPham sp = new sanPham(maSP, tenSP, loaiSP, xuatXu, Float.parseFloat(donGia), Float.parseFloat(vat), nhaCC, moTa, mauSac, hinhAnh);
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void insert(sanPham entity) {
        try {
            String sql = "exec addSanPham ?,?,?,?,?,?,?,?,?,?";
            jdbcHelper.executeUpdate(sql, entity.getMaSanPham(), entity.getTenSanPham(),
                    entity.getLoaiSanPham(), entity.getXuatXu(), entity.getDonGia(), entity.getVAT(), entity.getNhaCungCap(),
                    entity.getMoTa(), entity.getMauSac(), entity.getHinhAnh());
        } catch (Exception e) {
            System.out.println("Add sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            String sql = "exec deleteSanPham ?";
            jdbcHelper.executeUpdate(sql, id);
        } catch (Exception e) {
            System.out.println("Delete sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public void update(sanPham entity) {
        try {
            String sql = "exec updateSanPham ?,?,?,?,?,?,?,?,?,?";
            jdbcHelper.executeUpdate(sql, entity.getMaSanPham() , entity.getTenSanPham(),
                    entity.getLoaiSanPham(), entity.getXuatXu(), entity.getDonGia(), entity.getVAT(), entity.getNhaCungCap(),
                    entity.getMoTa(), entity.getMauSac(), entity.getHinhAnh());
        } catch (Exception e) {
            System.out.println("Update người học" + e.getMessage());
        }
    }

    public sanPham selectByID(String id) {
        List<sanPham> list = new ArrayList<>();
        try {
            String sql = "select * from SanPham where maSanPham like ? ";
            ResultSet rs = jdbcHelper.prepareStatement(sql,id).executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSanPham");
                String tenSP = rs.getString("tenSanPham");
                String loaiSP = rs.getString("loaiSanPham");
                String xuatXu = rs.getString("xuatSu");
                String donGia = rs.getString("donGia");
                String vat = rs.getString("VAT");
                String nhaCC = rs.getString("nhaCungCap");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                String hinhAnh = rs.getString("hinhAnh");
                sanPham sp = new sanPham(maSP, tenSP, loaiSP, xuatXu, Float.parseFloat(donGia), Float.parseFloat(vat), nhaCC, moTa, mauSac, hinhAnh);
                list.add(sp);
            }
            return list.get(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

