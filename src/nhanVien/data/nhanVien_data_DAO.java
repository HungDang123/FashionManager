/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nhanVien.data;

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

    final String selectAll = "SELECT * FROM nhanVien\n"
            + "ORDER BY hovaTen";

    final String selectID = "Select * from nhanVien where maNhanVien = ?";

    final String insertQuery = "INSERT INTO nhanVien"
            + "(maNhanVien, hoVaTen, matKhau, chucVu, gioiTinh, ngaySinh, canCuocCongDan, soDienThoai, hinhAnh) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    final String select_Almost = "Select * from nhanVien where maNhanVien like ? and hoVaTen like ?";
    final String deleteQuery = "Delete nhanVien where maNhanVien = ?";
    final String updateQuery = "UPDATE nhanVien \n"
            + "SET maNhanVien = ?,hoVaTen = ?, matKhau = ?, chucVu = ?, gioiTinh = ?, ngaySinh = ?, canCuocCongDan = ?, soDienThoai = ?, hinhAnh = ? "
            + "WHERE maNhanVien = ?";

    final String getSecurity_query = "Select matKhau, canCuocCongDan, soDienThoai, hinhAnh, email "
            + "from nhanVien "
            + "where maNhanVien = ?";
    final String selectCount_maNhanVien = "Select Count(*) from nhanVien where maNhanVien = ?";

    final String selectStatistiCal = "SELECT\n"
            + "  nv.maNhanVien as 'Mã Nhân Viên',\n"
            + "  nv.hoVaten as 'Tên Nhân Viên',\n"
            + "  COUNT(hd.maNhanVien) as 'Số lượng hóa đơn',\n"
            + "  Sum(ctHD.soLuong) as 'Số lượng đơn hàng',\n"
            + "  SUM(ctHD.tongTien) as 'Tổng Tiền'\n"
            + "FROM\n"
            + "  nhanVien nv\n"
            + "  JOIN hoaDon hd ON nv.maNhanVien = hd.maNhanVien\n"
            + "  JOIN khachHang kh ON kh.maKhachHang = hd.maKhachHang\n"
            + "  JOIN chiTietHoaDon ctHD ON ctHD.maHoaDon = hd.maHoaDon\n"
            + "WHERE\n"
            + "  nv.maNhanVien like ? and ctHD.tongTien > ?\n"
            + "GROUP BY\n"
            + "  nv.maNhanVien,nv.hoVaten";

    public int getCount_maNv(String id) {
        Object[] args = null;
        ResultSet rs = jdbcHelper.executeQuery(selectCount_maNhanVien, id);
        try {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(nhanVien_data_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public List<nhanVien> selectAll() {
        List<nhanVien> listNV = new ArrayList<>();
        ResultSet rs = jdbcHelper.executeQuery(selectAll);
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

    public List<nhanVien> selectNhanVien_byIDandHovaTen_almost(String ID, String hovaten) {
        ResultSet rs = jdbcHelper.executeQuery(select_Almost, "%" + ID + "%", hovaten + "%");
        System.out.println("sQL: " + select_Almost);
        System.out.println("ID: " + ID);
        System.out.println("HovaTEn: " + hovaten);
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

    public ResultSet getStatical(String maNhanVien, double tongTien) {
        return jdbcHelper.executeQuery(selectStatistiCal, maNhanVien, tongTien);
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
        try {
            //        new nhanVien_data_DAO().nhanVien_Insert(new nhanVien("nv011", "Hồ Minh Nhựt", "123456", false,
//                Boolean.TRUE, new Date(new java.util.Date().getTime()), "0002223", "012345", "404"));
//        new nhanVien_data_DAO().nhanVien_Delete("123456");

            ResultSet rs = new nhanVien_data_DAO().getStatical("%%", 1);

// Check if there are rows in the result set
// Close the ResultSet when done
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(nhanVien_data_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
