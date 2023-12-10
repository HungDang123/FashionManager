/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.pro1041.dao;

import Model.kichThuoc;
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

    private final String thongKe = "SELECT S.maSanPham,S.tenSanPham, SUM(c.soLuong) as 'soLuong',SUM(c.tongTien) as 'tongTien' \n"
            + "FROM sanPham S INNER JOIN chiTietHoaDon c on S.maSanPham = c.maSanPham\n"
            + "group by S.maSanPham,S.tenSanPham";
    private final String loaiSanPham = "SELECT loaiSanPham, COUNT(*) as soLuong\n"
            + "FROM sanPham\n"
            + "GROUP BY loaiSanPham";

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
                String nhaCC = rs.getString("nhaCungCap");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                String hinhAnh = rs.getString("hinhAnh");
                String vat = rs.getString("VAT");
                sanPham sp = new sanPham(maSP, tenSP, loaiSP, xuatXu, Float.parseFloat(donGia), nhaCC, moTa, mauSac, hinhAnh, Float.parseFloat(vat));
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
            jdbcHelper.executeUpdate(sql, entity.getMaSanPham(), entity.getTenSanPham(),
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
            ResultSet rs = jdbcHelper.prepareStatement(sql, id).executeQuery();
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
                sanPham sp = new sanPham(maSP, tenSP, loaiSP, xuatXu, Float.parseFloat(donGia), nhaCC, moTa, mauSac, hinhAnh, Float.parseFloat(vat));
                list.add(sp);
            }
            return list.get(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Object[]> thongKe() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.prepareStatement(thongKe).executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSanPham");
                String tenSP = rs.getString("tenSanPham");
                int soLuong = rs.getInt("soLuong");
                float donGia = rs.getFloat("tongTien");
                Object[] rowData = {maSP, tenSP, soLuong, donGia};
                list.add(rowData);
            }
            return list;
        } catch (Exception e) {
            System.out.println("Thống kê: " + e.getMessage());
        }
        return null;
    }

    public void insertKichThuoc(kichThuoc k) {
        try {
            String sql = "insert into kichThuoc values(?,?,?)";
            jdbcHelper.executeUpdate(sql, k.getSanPham().getMaSanPham(), k.getKichThuoc(), k.getsoLuong());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
     public List<Object[]> loaiSanPham() {
        List<Object[]> i = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(loaiSanPham);
            while (rs.next()) {
                String loaiSanPham = rs.getString("loaiSanPham");
                int soLuong = rs.getInt("soLuong");
                Object[] o ={loaiSanPham,soLuong};
                i.add(o);
            }
        } catch (Exception e) {
            System.out.println("loaiSanPham" + e.getMessage());
            e.printStackTrace();
        }
        return i;
    }
}
