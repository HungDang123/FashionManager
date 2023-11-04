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
public class kichThuoc implements Serializable{
    private String maKichThuoc;
    private sanPham sanPham;
    private int kichThuoc,VAT;

    public kichThuoc() {
    }

    public kichThuoc(String maKichThuoc, sanPham sanPham, int kichThuoc, int VAT) {
        this.maKichThuoc = maKichThuoc;
        this.sanPham = sanPham;
        this.kichThuoc = kichThuoc;
        this.VAT = VAT;
    }

    

    public sanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(sanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(int kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    @Override
    public String toString() {
        return "chiTietSanPham{" + "sanPham=" + sanPham + ", kichThuoc=" + kichThuoc + ", VAT=" + VAT + '}';
    }

    public String getMaKichThuoc() {
        return maKichThuoc;
    }

    public void setMaKichThuoc(String maKichThuoc) {
        this.maKichThuoc = maKichThuoc;
    }
    
}
