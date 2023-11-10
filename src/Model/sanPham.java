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
public class sanPham implements Serializable {

    private String maSanPham, tenSanPham, loaiSanPham, xuatXu;
    private Float donGia, VAT;
    private String nhaCungCap, moTa, mauSac, hinhAnh;

    public sanPham() {
    }

    public sanPham(String maSanPham, String tenSanPham, String loaiSanPham, String xuatXu, Float donGia, Float VAT, String nhaCungCap, String moTa, String mauSac, String hinhAnh) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        this.xuatXu = xuatXu;
        this.donGia = donGia;
        this.VAT = VAT;
        this.nhaCungCap = nhaCungCap;
        this.moTa = moTa;
        this.mauSac = mauSac;
        this.hinhAnh = hinhAnh;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public Float getDonGia() {
        return donGia;
    }

    public void setDonGia(Float donGia) {
        this.donGia = donGia;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public Float getVAT() {
        return VAT;
    }

    public void setVAT(Float VAT) {
        this.VAT = VAT;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    @Override
    public String toString() {
        return "sanPham,{" + "maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + ", loaiSanPham=" + loaiSanPham + ", xuatXu=" + xuatXu + ", donGia=" + donGia + ", VAT=" + VAT + ", nhaCungCap=" + nhaCungCap + ", moTa=" + moTa + ", mauSac=" + mauSac + ", hinhAnh=" + hinhAnh + '}';
    }

}
