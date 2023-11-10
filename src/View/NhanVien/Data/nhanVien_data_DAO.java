/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.NhanVien.Data;

import Model.nhanVien_Model;
import com.pro1041.util.jdbcHelper;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hnhut
 */
public class nhanVien_data_DAO {

    final String changePassword = "update nhanVien \n"
            + "SET matKhau = ? where maNhanVien = ?";
    final String selectAll = "select * from nhanVien";
    final String insertQuery = "INSERT INTO nhanVien"
            + "(maNhanVien, hoVaTen, matKhau, chucVu, gioiTinh, ngaySinh, canCuocCongDan, soDienThoai, hinhAnh) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    final String deleteQuery = "Delete nhanVien where maNhanVien = ?";
    final String updateQuery = "UPDATE nhanVien \n"
        + "SET maNhanVien = ?,hoVaTen = ?, matKhau = ?, chucVu = ?, gioiTinh = ?, ngaySinh = ?, canCuocCongDan = ?, soDienThoai = ?, hinhAnh = ? "
        + "WHERE maNhanVien = ?";

    public List<nhanVien_Model> selectAll() {
        List<nhanVien_Model> listNV = new ArrayList<>();
        Object[] obj = null;
        ResultSet rs = jdbcHelper.executeQuery(selectAll, obj);
        try {
            while (rs.next()) {
                nhanVien_Model nvM = new nhanVien_Model();
                nvM.setMaNhanVien(rs.getString("maNhanVien"));
                nvM.setHoVaTen(rs.getString("hovaTen"));
                nvM.setMatKhau(rs.getString("matKhau"));
                nvM.setChucVu(rs.getBoolean("chucVu"));
                nvM.setNgaySinh(rs.getDate("ngaySinh"));
                nvM.setGioiTinh(rs.getBoolean("gioiTinh"));
                nvM.setCanCuocCongDan(rs.getString("canCuocCongDan"));
                nvM.setSoDienThoai(rs.getString("soDienThoai"));
                nvM.setHinhAnh(rs.getString("hinhAnh"));

                listNV.add(nvM);
            }
            return listNV;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(nhanVien_data_DAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void insert(nhanVien_Model nvM) {
        jdbcHelper.executeUpdate(insertQuery, nvM.getMaNhanVien(),nvM.getHoVaTen(),nvM.getMatKhau(),nvM.getChucVu(),
                nvM.getGioiTinh(),nvM.getNgaySinh(),nvM.getCanCuocCongDan(),nvM.getSoDienThoai(),nvM.getHinhAnh());
    }
    
    public void delete(String maNhanVien) {
        jdbcHelper.executeUpdate(deleteQuery, maNhanVien);
    }
    
    public void update(nhanVien_Model nv_New, String maNhanVien_old) {
        jdbcHelper.executeUpdate(updateQuery, nv_New.getMaNhanVien(),nv_New.getHoVaTen(),nv_New.getMatKhau(),nv_New.getChucVu(),
                nv_New.getGioiTinh(),nv_New.getNgaySinh(),nv_New.getCanCuocCongDan(),nv_New.getSoDienThoai(),nv_New.getHinhAnh(),
                maNhanVien_old)
               ;
    }
    
    public void changePassword(String newPassword,String maNhanVien) {
        jdbcHelper.executeUpdate(changePassword, newPassword,maNhanVien);
    }
    
    
    
    public static void main(String[] args) {
//        new nhanVien_data_DAO().nhanVien_Insert(new nhanVien_Model("nv011", "Hồ Minh Nhựt", "123456", false, 
//                Boolean.TRUE, new Date(new java.util.Date().getTime()), "0002223", "012345", "404"));
//        new nhanVien_data_DAO().nhanVien_Delete("123456");
        new nhanVien_data_DAO().update(new nhanVien_Model("nv002", "Hồ Minh Nhựt", "123456", false, 
                Boolean.TRUE, new Date(new java.util.Date().getTime()),null, "0002223", "012345", "404"),"nv002");
    }
}
