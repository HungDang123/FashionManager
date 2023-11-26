/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.dao;

import Model.khachHang;
import com.pro1041.util.jdbcHelper;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class DAO_khachHang implements DAO<khachHang> {

    @Override
    public List<khachHang> getSelectAll() {
        List<khachHang> listkh = new ArrayList<>();
        try {
            String sql = "select*from khachHang";
            ResultSet rs = jdbcHelper.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                khachHang kh = new khachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setHoVaTen(rs.getString("hoVaTen"));
                kh.setNgaySinh(rs.getDate("ngaySinh"));
                kh.setGioiTinh(rs.getBoolean("gioiTinh"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                listkh.add(kh);
            }
        } catch (Exception ex) {
            System.out.println("lỗi" + ex.getMessage());
        }
        return listkh;
    }

    @Override
    public void insert(khachHang entity) {
        try {
            String sql = "exec addkh ?,?,?,?,?,?";
            jdbcHelper.executeUpdate(sql, entity.getMaKhachHang(), entity.getHoVaTen(), entity.getNgaySinh(),
                    entity.isGioiTinh(), entity.getSoDienThoai(), entity.getEmail());
        } catch (Exception e) {
            System.out.println("lỗi" + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            String sql = "exec deleteKH ?";
            jdbcHelper.executeUpdate(sql, id);
        } catch (Exception e) {
            System.out.println("lỗi" + e.getMessage());
        }
    }

    @Override
    public void update(khachHang entity) {
        try {
            String sql = "exec updateKhachHang ?,?,?,?,?,?";
            jdbcHelper.executeUpdate(sql, entity.getMaKhachHang(), entity.getHoVaTen(), entity.getNgaySinh(),
                    entity.isGioiTinh(), entity.getSoDienThoai(), entity.getEmail());

        } catch (Exception e) {
            System.out.println("loii" + e.getMessage());
        }
    }

    public khachHang findKH(String id) {
        try {
            String sql = "select*from khachHang where maKhachHang =?";
            ResultSet rs = jdbcHelper.executeQuery(sql, id);
            if (rs.next()) {
                String maKhachHang = rs.getString(1);
                String hoVaTen = rs.getString(2);
                Date ngaySinh = rs.getDate(3);
                int gioiTinh = rs.getInt(4);
                boolean gt = (gioiTinh == 1);
                String sdt = rs.getString(5);
                String email = rs.getString(6);

                khachHang kh = new khachHang(maKhachHang, hoVaTen, ngaySinh, gt, sdt, email);
                return kh;
            }

        } catch (Exception e) {

        }
        return null;
    }

    public List<khachHang> timkiemTen(String name) {
        List<khachHang> list = new ArrayList<>();
        try {
            String sql = "exec timKiemTenKH ?";
            ResultSet rs = jdbcHelper.executeQuery(sql, name);
            while (rs.next()) {
                String maKhachHang = rs.getString(1);
                String hoVaTen = rs.getString(2);
                Date ngaySinh = rs.getDate(3);
                boolean gt = false;
                if (rs.getInt(4) == 1) {
                    gt = true;

                }
                String soDienThoai = rs.getString(5);
                String email = rs.getString(6);
                khachHang kh = new khachHang(maKhachHang, hoVaTen, ngaySinh, gt, soDienThoai, email);
                list.add(kh);

            }

        } catch (Exception e) {
            System.out.println("loi" + e.getMessage());
        }
        return list;

    }

    public List<khachHang> timKiemMa(String ma) {
        List<khachHang> list = new ArrayList<>();
        try {
            String sql = "exec timKiemMaKh ?";
            ResultSet rs = jdbcHelper.executeQuery(sql, ma);
            while (rs.next()) {
                String maKhachHang = rs.getString(1);
                String hoVaTen = rs.getString(2);
                Date ngaySinh = rs.getDate(3);
                int gioiTinh = rs.getInt(4);
                boolean gt = (gioiTinh == 1);
                String sdt = rs.getString(5);
                String email = rs.getString(6);
                khachHang kh = new khachHang(maKhachHang, hoVaTen, ngaySinh, gt, sdt, email);
                list.add(kh);
            }

        } catch (Exception e) {
            System.out.println("loi" + e.getMessage());

        }
        return list;
    }

    public List<khachHang> locGT(String gttinh) {
        List<khachHang> list = new ArrayList<>();
        try {
            String sql = "exec locGTinhhh ?";
            ResultSet rs = jdbcHelper.executeQuery(sql, gttinh);
            while (rs.next()) {
                String maKhachHang = rs.getString(1);
                String hoVaTen = rs.getString(2);
                Date ngaySinh = rs.getDate(3);
                int gioiTinh = rs.getInt(4);
                boolean gt = (gioiTinh == 1);
                String sdt = rs.getString(5);
                String email = rs.getString(6);
                khachHang kh = new khachHang(maKhachHang, hoVaTen, ngaySinh, gt, sdt, email);
                list.add(kh);
            }
        } catch (Exception e) {
            System.out.println(";oi" + e.getMessage());
        }
        return list;
    }

    public List<Object[]> thongKe(Date ngay) {
        List<Object[]> list = new ArrayList<>();
        try {
            String sql = "exec thongKeKH ?";
            ResultSet rs = jdbcHelper.executeQuery(sql, ngay);
            while (rs.next()) {
                String maKhachHang = rs.getString(1);
                String hoVaTen = rs.getString(2);
                int tongHoaDon = rs.getInt(3);
                float tongTien = rs.getFloat(4);
                Object[] obj = {maKhachHang, hoVaTen, tongHoaDon, tongTien};
                list.add(obj);
            }
        } catch (Exception e) {
            System.out.println("loi" + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
