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
public class chiTietHoaDon implements Serializable {

    private String maCthd;
    private sanPham maSanPham;
    private int soLuong;
    private String kichThuoc;
    private float tongTien;
    private Date ngayLapHoaDon;
    private hoaDon maHoaDon;

    public chiTietHoaDon() {
    }

    public chiTietHoaDon(String maCthd, sanPham maSanPham, int soLuong, String kichThuoc, float tongTien, Date ngayLapHoaDon, hoaDon maHoaDon) {
        this.maCthd = maCthd;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.kichThuoc = kichThuoc;
        this.tongTien = tongTien;
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.maHoaDon = maHoaDon;
    }

    public String getMaCthd() {
        return maCthd;
    }

    public void setMaCthd(String maCthd) {
        this.maCthd = maCthd;
    }

    public sanPham getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(sanPham maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayLapHoaDon() {
        return ngayLapHoaDon;
    }

    public void setNgayLapHoaDon(Date ngayLapHoaDon) {
        this.ngayLapHoaDon = ngayLapHoaDon;
    }

    public hoaDon getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(hoaDon maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    @Override
    public String toString() {
        return "chiTietHoaDon{" + "maCthd=" + maCthd + ", maSanPham=" + maSanPham + ", soLuong=" + soLuong + ", kichThuoc=" + kichThuoc + ", tongTien=" + tongTien + ", ngayLapHoaDon=" + ngayLapHoaDon + ", maHoaDon=" + maHoaDon + '}';
    }

}
