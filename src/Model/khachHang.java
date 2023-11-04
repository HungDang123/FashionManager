/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author HUNG
 */
public class khachHang implements Serializable{
    private String maKhachHang,hoVaTen;
    private Date ngaySinh;
    private boolean gioiTinh;
    private String soDienThoai;

    public khachHang() {
    }

    public khachHang(String maKhachHang, String hoVaTen, Date ngaySinh, boolean gioiTinh, String soDienThoai) {
        this.maKhachHang = maKhachHang;
        this.hoVaTen = hoVaTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @Override
    public String toString() {
        return "khachHang{" + "maKhachHang=" + maKhachHang + ", hoVaTen=" + hoVaTen + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", soDienThoai=" + soDienThoai + '}';
    }
    
}
