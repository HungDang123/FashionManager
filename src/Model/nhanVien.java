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
public class nhanVien implements Serializable {

    private String maNhanVien, hoVaTen, matKhau;
    private Boolean chucVu, gioiTinh;
    private Date ngaySinh;
    private String email;
    private String canCuocCongDan, soDienThoai, hinhAnh;

    public nhanVien() {
    }

    public nhanVien(String maNhanVien, String hoVaTen, String matKhau, Boolean chucVu, Boolean gioiTinh, Date ngaySinh, String gmail, String canCuocCongDan, String soDienThoai, String hinhAnh) {
        this.maNhanVien = maNhanVien;
        this.hoVaTen = hoVaTen;
        this.matKhau = matKhau;
        this.chucVu = chucVu;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.email = gmail;
        this.canCuocCongDan = canCuocCongDan;
        this.soDienThoai = soDienThoai;
        this.hinhAnh = hinhAnh;
    }

    public String getGmail() {
        return email;
    }

    public void setGmail(String gmail) {
        this.email = gmail;
    }
    
    

   

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public Boolean getChucVu() {
        return chucVu;
    }

    public void setChucVu(Boolean chucVu) {
        this.chucVu = chucVu;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getCanCuocCongDan() {
        return canCuocCongDan;
    }

    public void setCanCuocCongDan(String canCuocCongDan) {
        this.canCuocCongDan = canCuocCongDan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "nhanVien{" + "maNhanVien=" + maNhanVien + ", hoVaTen=" + hoVaTen + ", matKhau=" + matKhau + ", chucVu=" + chucVu + ", gioiTinh=" + gioiTinh + ", ngaySinh=" + ngaySinh + ", canCuocCongDan=" + canCuocCongDan + ", soDienThoai=" + soDienThoai + ", hinhAnh=" + hinhAnh + '}';
    }

}
