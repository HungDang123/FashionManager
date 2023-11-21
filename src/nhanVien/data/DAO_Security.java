/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nhanVien.data;

import com.pro1041.util.jdbcHelper;

/**
 *
 * @author hnhut
 */
public class DAO_Security {

    final static String changePw_query = "UPDATE nhanVien SET matKhau = ? WHERE maNhanvien = ?";
    final static String changeSdt_query = "UPDATE nhanVien SET soDienThoai = ? WHERE maNhanvien = ?";
    final static String changeCccd_query = "UPDATE nhanVien SET canCuocCongDan = ? WHERE maNhanvien = ?";
    final static String changeEmail_query = "UPDATE nhanVien SET email = ? WHERE maNhanvien = ?";

    final static String removeMatKhau_query = "UPDATE nhanVien SET matKhau = NULL WHERE maNhanVien = ?";
    final static String removeSoDienThoai_query = "UPDATE nhanVien SET soDienThoai = NULL WHERE maNhanVien = ?";
    final static String removeCanCuocCongDan_query = "UPDATE nhanVien SET canCuocCongDan = NULL WHERE maNhanVien = ?";
    final static String removeEmail_query = "UPDATE nhanVien SET email = NULL WHERE maNhanVien = ?";

    public static void changePw(String matKhau, String maNhanVien) {
        jdbcHelper.executeUpdate(changePw_query, matKhau, maNhanVien);
    }

    public static void changeSdt(String soDienThoai, String maNhanVien) {
        jdbcHelper.executeUpdate(changeSdt_query, soDienThoai, maNhanVien);
    }

    public static void changeCccd(String canCuocCongDan, String maNhanVien) {
        jdbcHelper.executeUpdate(changeCccd_query, canCuocCongDan, maNhanVien);
    }

    public static void changeEmail(String email, String maNhanVien) {
        jdbcHelper.executeUpdate(changeEmail_query, email, maNhanVien);
    }

    public static void removeMatKhau(String maNhanVien) {
        jdbcHelper.executeUpdate(removeMatKhau_query, maNhanVien);
    }

    public static void removeSoDienThoai(String maNhanVien) {
        jdbcHelper.executeUpdate(removeSoDienThoai_query, maNhanVien);
    }

    public static void removeCanCuocCongDan(String maNhanVien) {
        jdbcHelper.executeUpdate(removeCanCuocCongDan_query, maNhanVien);
    }

    public static void removeEmail(String maNhanVien) {
        jdbcHelper.executeUpdate(removeEmail_query, maNhanVien);
    }
   }


