
package Model;

import java.io.Serializable;

/**
 *
 * @author HOANG
 */
public class kichThuoc implements Serializable{
    private String maKichThuoc,kichThuoc;
    private sanPham sanPham;
    private int soLuong;

    public kichThuoc() {
    }

    public kichThuoc(sanPham sanPham, String kichThuoc, int soLuong) {
        this.sanPham = sanPham;
        this.kichThuoc = kichThuoc;
        this.soLuong = soLuong;
    }

    public sanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(sanPham sanPham) {
        this.sanPham = sanPham;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public int getsoLuong() {
        return soLuong;
    }

    public void setsoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "chiTietSanPham,{" + "sanPham=" + sanPham + ", kichThuoc=" + kichThuoc + ", soLuong=" + soLuong + '}';
    }

    public String getMaKichThuoc() {
        return maKichThuoc;
    }

    public void setMaKichThuoc(String maKichThuoc) {
        this.maKichThuoc = maKichThuoc;
    }
    
}
