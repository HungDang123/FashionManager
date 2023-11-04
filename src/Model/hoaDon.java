/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author HUNG
 */
public class hoaDon implements Serializable{

    private String maHoaDon;
    private nhanVien maNhanVien;
    private khachHang maKhachHang;

    public hoaDon() {
    }

    public hoaDon(String maHoaDon, nhanVien maNhanVien, khachHang maKhachHang) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public nhanVien getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(nhanVien maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public khachHang getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(khachHang maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    @Override
    public String toString() {
        return "hoaDon{" + "maHoaDon=" + maHoaDon + ", maNhanVien=" + maNhanVien + ", maKhachHang=" + maKhachHang + '}';
    }
    
}
