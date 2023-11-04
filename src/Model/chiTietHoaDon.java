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
public class chiTietHoaDon implements Serializable{
    private int maCthd;
    private khachHang maKhachHang;
    private sanPham maSanPham;
    private int soLuong;
    private float tongTien;
    private Date ngayLapHoaDon;
    private nhanVien maNhanVien; 
}
