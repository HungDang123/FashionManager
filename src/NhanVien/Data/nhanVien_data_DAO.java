/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NhanVien.Data;

import Model.nhanVien;
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

    final String selectID = "Select * from nhanVien where maNhanVien = ?";

    final String insertQuery = "INSERT INTO nhanVien"
            + "(maNhanVien, hoVaTen, matKhau, chucVu, gioiTinh, ngaySinh, canCuocCongDan, soDienThoai, hinhAnh) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    final String updateSecurity = "Update nhanVien \n"
            + "Set matKhau = ?, soDienThoai = ?, canCuocCongDan = ?, email = ? \n"
            + "WHERE maNhanVien = ? ";
    
    final String selectID_Almost = "Select * from nhanVien where maNhanVien like ?";
    final String deleteQuery = "Delete nhanVien where maNhanVien = ?";
    final String updateQuery = "UPDATE nhanVien \n"
            + "SET maNhanVien = ?,hoVaTen = ?, matKhau = ?, chucVu = ?, gioiTinh = ?, ngaySinh = ?, canCuocCongDan = ?, soDienThoai = ?, hinhAnh = ? "
            + "WHERE maNhanVien = ?";

    public List<nhanVien> selectAll() {
        List<nhanVien> listNV = new ArrayList<>();
        Object[] obj = null;
        ResultSet rs = jdbcHelper.executeQuery(selectAll, obj);
        try {
            while (rs.next()) {
                nhanVien nvM = new nhanVien();
                nvM.setMaNhanVien(rs.getString("maNhanVien"));
                nvM.setHoVaTen(rs.getString("hovaTen"));
                nvM.setMatKhau(rs.getString("matKhau"));
                nvM.setChucVu(rs.getBoolean("chucVu"));
                nvM.setNgaySinh(rs.getDate("ngaySinh"));
                nvM.setGioiTinh(rs.getBoolean("gioiTinh"));
                nvM.setCanCuocCongDan(rs.getString("canCuocCongDan"));
                nvM.setSoDienThoai(rs.getString("soDienThoai"));
                nvM.setHinhAnh(rs.getString("hinhAnh"));
                nvM.setEmail(rs.getString("email"));

                listNV.add(nvM);
            }
            return listNV;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(nhanVien_data_DAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public nhanVien selectNhanVien_byID(String ID) {
        ResultSet rs = jdbcHelper.executeQuery(selectID, ID);

        try {
            while (rs.next()) {
                nhanVien nvM = new nhanVien();
                nvM.setMaNhanVien(rs.getString("maNhanVien"));
                nvM.setHoVaTen(rs.getString("hovaTen"));
                nvM.setMatKhau(rs.getString("matKhau"));
                nvM.setChucVu(rs.getBoolean("chucVu"));
                nvM.setNgaySinh(rs.getDate("ngaySinh"));
                nvM.setGioiTinh(rs.getBoolean("gioiTinh"));
                nvM.setCanCuocCongDan(rs.getString("canCuocCongDan"));
                nvM.setSoDienThoai(rs.getString("soDienThoai"));
                nvM.setHinhAnh(rs.getString("hinhAnh"));
                nvM.setEmail(rs.getString("email"));

                System.out.println(nvM.getMaNhanVien());

                return nvM;
            }
        } catch (SQLException ex) {
            Logger.getLogger(nhanVien_data_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<nhanVien> selectNhanVien_byID_almost(String ID) {
        ResultSet rs = jdbcHelper.executeQuery(selectID_Almost, "%" + ID + "%");
        List<nhanVien> listNV = new ArrayList<>();
        try {
            while (rs.next()) {
                nhanVien nvM = new nhanVien();
                nvM.setMaNhanVien(rs.getString("maNhanVien"));
                nvM.setHoVaTen(rs.getString("hovaTen"));
                nvM.setMatKhau(rs.getString("matKhau"));
                nvM.setChucVu(rs.getBoolean("chucVu"));
                nvM.setNgaySinh(rs.getDate("ngaySinh"));
                nvM.setGioiTinh(rs.getBoolean("gioiTinh"));
                nvM.setCanCuocCongDan(rs.getString("canCuocCongDan"));
                nvM.setSoDienThoai(rs.getString("soDienThoai"));
                nvM.setHinhAnh(rs.getString("hinhAnh"));
                nvM.setEmail(rs.getString("email"));

                System.out.println(nvM.getMaNhanVien());

                listNV.add(nvM);
            }
            return listNV;
        } catch (SQLException ex) {
            Logger.getLogger(nhanVien_data_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void updateSecurity(nhanVien nv) {
        jdbcHelper.executeUpdate(updateSecurity, nv.getMatKhau(), nv.getSoDienThoai(), nv.getCanCuocCongDan(), nv.getEmail(),
                nv.getMaNhanVien()
                );
    }

    public void insert(nhanVien nvM) {
        jdbcHelper.executeUpdate(insertQuery, nvM.getMaNhanVien(), nvM.getHoVaTen(), nvM.getMatKhau(), nvM.getChucVu(),
                nvM.getGioiTinh(), nvM.getNgaySinh(), nvM.getCanCuocCongDan(), nvM.getSoDienThoai(), nvM.getHinhAnh());
    }

    public void delete(String maNhanVien) {
        jdbcHelper.executeUpdate(deleteQuery, maNhanVien);
    }

    public void update(nhanVien nv_New, String maNhanVien_old) {
        jdbcHelper.executeUpdate(updateQuery, nv_New.getMaNhanVien(), nv_New.getHoVaTen(), nv_New.getMatKhau(), nv_New.getChucVu(),
                nv_New.getGioiTinh(), nv_New.getNgaySinh(), nv_New.getCanCuocCongDan(), nv_New.getSoDienThoai(), nv_New.getHinhAnh(),
                maNhanVien_old);
    }

    public void changePassword(String newPassword, String maNhanVien) {
        jdbcHelper.executeUpdate(changePassword, newPassword, maNhanVien);
    }

    
    public static void main(String[] args) {
//        new nhanVien_data_DAO().nhanVien_Insert(new nhanVien("nv011", "Hồ Minh Nhựt", "123456", false, 
//                Boolean.TRUE, new Date(new java.util.Date().getTime()), "0002223", "012345", "404"));
//        new nhanVien_data_DAO().nhanVien_Delete("123456");

//        new nhanVien_data_DAO().update(new nhanVien("nv002", "Hồ Minh Nhựt", "123456", false, 
//                Boolean.TRUE, new Date(new java.util.Date().getTime()),null, "0002223", "012345", "404"),"nv002");
    }
}
