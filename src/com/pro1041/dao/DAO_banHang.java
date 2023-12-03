
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.dao;

import Model.chiTietHoaDon;
import Model.hoaDon;
import Model.khachHang;
import Model.kichThuoc;
import Model.nhanVien;
import Model.sanPham;
import com.pro1041.util.jdbcHelper;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HUNG
 */
public class DAO_banHang {

    sanPham sp;
    khachHang kh;
    private final String selectSanPham = "Select * From sanPham";
    private final String findByNumber = "Select * from khachHang where soDienThoai like ?";
    private final String insertKhachHang = "insert into khachHang(maKhachHang,hoVaTen,ngaySinh,gioiTinh,soDienThoai,email)"
            + " values (?,?,?,?,?,?)";
    private final String SearchByName = "select sp.* from sanPham sp where sp.tenSanPham"
            + " COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%' ";
    private final String SearchById = "select sp.* from sanPham sp where sp.maSanPham "
            + " LIKE N'%' + ? + N'%' ";
    private final String SearchBoth = "select sp.* from sanPham sp where sp.tenSanPham"
            + " COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%' "
            + "and sp.maSanPham LIKE N'%' + ? + N'%'";
    private final String insertHoaDon = "insert into hoaDon(maHoaDon,maNhanVien,maKhachHang) values (?,?,?)";
    private final String deleteHd = "Delete from hoaDon where maHoaDon = ?";
    private final String findByMaHD = "Select * from hoaDon where maHoaDon = ?";
    private final String findByMaNV = "Select * from nhanVien where maNhanVien = ?";
    private final String findByMaKH = "Select * from khachHang where maKhachHang = ?";
    private final String findByIdKH = "SELECT * FROM khachHang WHERE maKhachHang = ?";
    private final String findByIdSP = "SELECT * FROM sanPham where maSanPham = ?";
    private final String insertCTHD = "Insert into chiTietHoaDon(maCthd,maSanPham,maHoaDon,soLuong,tongTien,ngayLapHoaDon,kichThuoc) values(?,?,?,?,?,?,?)";
    private final String insertTemp = "Insert into chiTietHoaDonTam(maCthd,maSanPham,maHoaDon,soLuong,tongTien,ngayLapHoaDon,kichThuoc) values(?,?,?,?,?,?,?)";

    private final String getAllCTHD = "SELECT c.maCthd,c.maSanPham, c.soLuong,c.tongTien,c.ngayLapHoaDon,c.maHoaDon,c.kichThuoc,\n"
            + "    SUM(c.tongTien) OVER (PARTITION BY c.maHoaDon) AS 'tongTiens'\n"
            + "FROM chiTietHoaDon c\n"
            + "GROUP BY c.maCthd, c.maSanPham,c.soLuong,c.tongTien,c.ngayLapHoaDon,c.maHoaDon,c.kichThuoc;";
    private final String getAllCTHD1 = "SELECT c.maCthd,c.maSanPham, c.soLuong,c.tongTien,c.ngayLapHoaDon,c.maHoaDon,c.kichThuoc, SUM(c.tongTien)\n"
            + "OVER (PARTITION BY c.maHoaDon) AS 'tongTiens'\n"
            + "            FROM chiTietHoaDon c inner join hoaDon d on d.maHoaDon = c.maHoaDon \n"
            + "            where d.maNhanVien = ? \n"
            + "            GROUP BY c.maCthd, c.maSanPham,c.soLuong,c.tongTien,c.ngayLapHoaDon,c.maHoaDon,c.kichThuoc";
//    private final String getAllCTHD1 = "select *,  from chiTietHoaDon  where";
    private final String getHD = "SELECT distinct s.tenSanPham, s.mauSac,s.donGia,c.kichThuoc,c.soLuong,\n"
            + "  (s.donGia * c.soLuong * s.VAT / 100) AS 'tienVAT',\n"
            + "  (s.donGia * c.soLuong + (s.donGia * c.soLuong * s.VAT / 100)) AS 'tongTien'\n"
            + "FROM\n"
            + "  hoaDon h INNER JOIN chiTietHoaDon c ON h.maHoaDon = c.maHoaDon\n"
            + "  INNER JOIN sanPham s ON c.maSanPham = s.maSanPham\n"
            + "  INNER JOIN kichThuoc k ON k.maSanPham = s.maSanPham\n"
            + "WHERE\n"
            + "  c.maHoaDon = ?;";
    private final String findByIdCTHD = "select * from chiTietHoaDon where maCthd = ?";
    private final String deleteCTHD = "delete from chiTietHoaDon where maCthd = ?";
    private final String deleteHD = "delete from hoaDon where maHoaDon = ?";
    private final String getYear = "select distinct year(c.ngayLapHoaDon) as 'year' from chiTietHoaDon c";
    private final String thongKeBanHangTheoNam = "SELECT\n"
            + "    COUNT(c.maCthd) AS SoHoaDon,\n"
            + "    SUM(c.tongTien) AS TongTien,\n"
            + "    SUM(s.vat * c.tongTien / 100) AS TienVAT \n"
            + "	FROM\n"
            + "    chiTietHoaDon c inner join sanPham s\n"
            + "	on c.maSanPham = s.maSanPham where year(c.ngayLapHoaDon) = ? ";

    private final String thongKeBanHangTheoQuy = "SELECT\n"
            + "    COUNT(c.maCthd) AS SoHoaDon,\n"
            + "    SUM(c.tongTien) AS TongTien,\n"
            + "    SUM(s.vat * c.tongTien / 100) AS TienVAT\n"
            + "	FROM\n"
            + "    chiTietHoaDon c inner join sanPham s\n"
            + "	on c.maSanPham = s.maSanPham where "
            + " year(c.ngayLapHoaDon) = ? and DATEPART(QUARTER, c.ngayLapHoaDon) = ?";

    private final String thongKeBanHangTheoThang = "SELECT\n"
            + "    COUNT(c.maCthd) AS SoHoaDon,\n"
            + "    SUM(c.tongTien) AS TongTien,\n"
            + "    SUM(s.vat * c.tongTien / 100) AS TienVAT\n"
            + "	FROM\n"
            + "    chiTietHoaDon c inner join sanPham s\n"
            + "	on c.maSanPham = s.maSanPham  where "
            + " year(c.ngayLapHoaDon) = ?\n"
            + " and MONTH(c.ngayLapHoaDon) = ?";
    private final String updateCTHD = "update chiTietHoaDon set kichThuoc = ? , soLuong = ?,tongTien = ? where maCthd = ?";
    private final String chartByMonth = "SELECT MONTH(ngayLapHoaDon) AS Thang, SUM(tongTien) AS TongTien\n"
            + "FROM chiTietHoaDon\n"
            + "WHERE YEAR(ngayLapHoaDon) = ?\n"
            + "GROUP BY MONTH(ngayLapHoaDon)\n"
            + "ORDER BY MONTH(ngayLapHoaDon)";
    private final String chartByYear = "SELECT year(ngayLapHoaDon) AS Nam, SUM(tongTien) AS TongTien\n"
            + "FROM chiTietHoaDon\n"
            + "GROUP BY year(ngayLapHoaDon)\n"
            + "ORDER BY year(ngayLapHoaDon)";
    private final String chartByDatePart = "SELECT DATEPART(QUARTER, ngayLapHoaDon) AS Quy, SUM(tongTien) AS TongTien\n"
            + "FROM chiTietHoaDon c\n"
            + "where YEAR(c.ngayLapHoaDon) = ?\n"
            + "GROUP BY DATEPART(QUARTER, ngayLapHoaDon)\n"
            + "ORDER BY DATEPART(QUARTER, ngayLapHoaDon)";
    private final String findSizes = "select * from kichThuoc "
            + "where maSanPham = ? and kichThuoc = ?";
    private final String khoSP = "UPDATE kichThuoc\n"
            + "SET soLuong = CASE\n"
            + "    WHEN soLuong - ? >= 0 THEN soLuong - ?\n"
            + "    ELSE soLuong\n"
            + "    END\n"
            + "WHERE maSanPham = ? AND kichThuoc = ?";

    public List<sanPham> SelectSanPham() {
        List<sanPham> list = new ArrayList<>();
        list.clear();
        try {
            ResultSet rs = jdbcHelper.prepareStatement(selectSanPham).executeQuery();
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, nhaCungCap, moTa, mauSac, hinhAnh, vat);
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Select sanPham:. " + e.getMessage());
        }
        return null;
    }

    public khachHang findByNumber(String sdt) {
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByNumber, sdt);
            while (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                String hoVaTen = rs.getString("hoVaTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String soDienThoai = rs.getString("soDienThoai");
                String email = rs.getString("email");
                kh = new khachHang(maKhachHang, hoVaTen, ngaySinh, gioiTinh, soDienThoai, email);
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FindByNumber: " + e.getMessage());
        }
        return null;
    }

    public void insertKhachHang(khachHang kh) {
        try {
            jdbcHelper.executeUpdate(insertKhachHang, kh.getMaKhachHang(), kh.getHoVaTen(), kh.getNgaySinh(), kh.isGioiTinh(), kh.getSoDienThoai(), kh.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<sanPham> searchByName(String str) {
        List<sanPham> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(SearchByName, str);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, nhaCungCap, moTa, mauSac, hinhAnh, vat);
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("searchByName: " + e.getMessage());
        }
        return list;
    }

    public List<sanPham> searchById(String str) {
        List<sanPham> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(SearchById, str);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, nhaCungCap, moTa, mauSac, hinhAnh, vat);
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("searchByName: " + e.getMessage());
        }
        return list;
    }

    public List<sanPham> searchBoth(String name, String id) {
        List<sanPham> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(SearchBoth, name, id);
            while (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, nhaCungCap, moTa, mauSac, hinhAnh, vat);
                list.add(sp);
            }

        } catch (Exception e) {
            System.out.println("search both: " + e.getMessage());
        }
        return list;
    }

    public khachHang findByIdKh(String k) {
        if (k == null) {
            return null;
        }
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByIdKH, k);
            if (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                String tenKhachHang = rs.getString("hoVaTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String sdt = rs.getString("soDienThoai");
                String email = rs.getString("email");
                khachHang kh = new khachHang(maKhachHang, tenKhachHang, ngaySinh, gioiTinh, sdt, email);
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FindByIdKh :" + e.getMessage());
        }
        return null;
    }

    public void insertHoaDon(hoaDon hd) {
        try {
            jdbcHelper.executeUpdate(insertHoaDon, hd.getMaHoaDon(), hd.getMaNhanVien().getMaNhanVien(), hd.getMaKhachHang().getMaKhachHang());
        } catch (Exception e) {
            System.out.println("Insert hóa đơn:. " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteHoaDon(String id) {
        try {
            jdbcHelper.executeUpdate(deleteHd, id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Delete hoa don: " + e.getMessage());
        }
    }

    public nhanVien findByMaNV(String id) {
        if (id == null) {
            return null;
        }
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByMaNV, id);
            if (rs.next()) {
                nhanVien nhanVien = new nhanVien();
                nhanVien.setMaNhanVien(rs.getString("manhanvien"));
                nhanVien.setHoVaTen(rs.getString("hovaten"));
                nhanVien.setMatKhau(rs.getString("matkhau"));
                nhanVien.setChucVu(rs.getBoolean("chucvu"));
                nhanVien.setGioiTinh(rs.getBoolean("gioitinh"));
                nhanVien.setNgaySinh(rs.getDate("ngaysinh"));
                nhanVien.setCanCuocCongDan(rs.getString("cancuoccongdan"));
                nhanVien.setSoDienThoai(rs.getString("sodienthoai"));
                nhanVien.setHinhAnh(rs.getString("hinhanh"));

                return nhanVien;
            }
        } catch (Exception e) {
            System.out.println("Find by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public khachHang findByMaKH(String id) {
        if (id == null) {
            return null;
        }
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByMaKH, id);
            if (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                String hoVaTen = rs.getString("hoVaTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String soDienThoai = rs.getString("soDienThoai");
                String email = rs.getString("email");
                khachHang kh = new khachHang(maKhachHang, hoVaTen, ngaySinh, gioiTinh, soDienThoai, email);
                return kh;
            }
        } catch (Exception e) {
            System.out.println("FindByMaKH: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public hoaDon findByMaHD(String id) {
        if (id == null) {
            return null;
        }
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByMaHD, id);
            if (rs.next()) {
                String MaHoaDon = rs.getString("maHoaDon");
                String MaNhanVien = rs.getString("maNhanVien");
                String MaKhachHang = rs.getString("maKhachHang");
                hoaDon hd = new hoaDon(MaHoaDon, findByMaNV(MaNhanVien), findByMaKH(MaKhachHang));
                return hd;
            }
        } catch (Exception e) {
            System.out.println("Find by ma hd:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public sanPham findByMaSP(String id) {
        if (id == null) {
            return null;
        }
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByIdSP, id);
            if (rs.next()) {
                String maSanPham = rs.getString("maSanPham");
                String tenSanPham = rs.getString("tenSanPham");
                String loaiSanPham = rs.getString("loaiSanPham");
                String moTa = rs.getString("moTa");
                String mauSac = rs.getString("mauSac");
                Float giaNhap = rs.getFloat("donGia");
                String xuatSu = rs.getString("xuatSu");
                String hinhAnh = rs.getString("hinhAnh");
                Float vat = rs.getFloat("VAT");
                String nhaCungCap = rs.getString("nhaCungCap");
                sanPham sp = new sanPham(maSanPham, tenSanPham, loaiSanPham, xuatSu, giaNhap, nhaCungCap, moTa, mauSac, hinhAnh, vat);
                return sp;
            }
        } catch (Exception e) {
            System.out.println("Find by ma sp" + e.getMessage());
        }
        return null;

    }

    public void insertCTHD(List<Object> obj) {
        try {
            Object[] objArray = (Object[]) obj.get(0);
            System.out.println(objArray[0] + " " + objArray[1] + " " + objArray[2] + " " + objArray[3] + " " + objArray[4] + " " + objArray[5] + " " + objArray[6]);

            jdbcHelper.executeUpdate(insertCTHD, objArray[0], objArray[1], objArray[2], objArray[3], objArray[4], objArray[5], objArray[6]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Insert cthd: " + e.getMessage());
        }
    }

    public List<chiTietHoaDon> getAllCTHD() {
        List<chiTietHoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.prepareStatement(getAllCTHD).executeQuery();
            while (rs.next()) {
                String maCTHD = rs.getString("maCthd");
                String maSp = rs.getString("maSanPham");
                sanPham sanPham = findByMaSP(maSp);
                int soLuong = rs.getInt("soLuong");
                String kichThuoc = rs.getString("kichThuoc");
                Float tongTien = rs.getFloat("tongTiens");
                Date ngayLapHoaDon = rs.getDate("ngayLapHoaDon");
                String maHoaDon = rs.getString("maHoaDon");
                hoaDon hd = findByMaHD(maHoaDon);
                if (hd == null) {
                    System.out.println("hd null");
                    return null;
                }
                chiTietHoaDon cthd = new chiTietHoaDon(maCTHD, sanPham, soLuong, kichThuoc, tongTien, ngayLapHoaDon, hd);
                list.add(cthd);
            }
            return list;
        } catch (Exception e) {
            System.out.println("GetAllCTHD: " + e.getMessage());
        }
        return null;
    }

    public List<chiTietHoaDon> getAllCTHD1(String id) {
        List<chiTietHoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(getAllCTHD1, id);
            while (rs.next()) {
                String maCTHD = rs.getString("maCthd");
                String maSp = rs.getString("maSanPham");
                sanPham sanPham = findByMaSP(maSp);
                int soLuong = rs.getInt("soLuong");
                String kichThuoc = rs.getString("kichThuoc");
                Float tongTien = rs.getFloat("tongTiens");
                Date ngayLapHoaDon = rs.getDate("ngayLapHoaDon");
                String maHoaDon = rs.getString("maHoaDon");
                hoaDon hd = findByMaHD(maHoaDon);
                if (hd == null) {
                    System.out.println("hd null");
                    return null;
                }
                chiTietHoaDon cthd = new chiTietHoaDon(maCTHD, sanPham, soLuong, kichThuoc, tongTien, ngayLapHoaDon, hd);
                list.add(cthd);
            }
            return list;
        } catch (Exception e) {
            System.out.println("GetAllCTHD1: " + e.getMessage());
        }
        return null;
    }

    public List<Object[]> getHD(String hd) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(getHD, hd);
            while (rs.next()) {
                String tenSp = rs.getString("tenSanPham");
                String mauSac = rs.getString("mauSac");
                Float donGia = rs.getFloat("donGia");
                String kichThuoc = rs.getString("kichThuoc");
                int soLuong = rs.getInt("soLuong");
                Float tienVat = rs.getFloat("tienVAT");
                Float tongTien = rs.getFloat("tongTien");
                Object[] obj = new Object[]{tenSp, mauSac, donGia, kichThuoc, soLuong, tienVat, tongTien};
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            System.out.println("getHd : " + e.getMessage());
        }
        return null;
    }

    public chiTietHoaDon findByIdCTHD(String id) {
        try {
            ResultSet rs = jdbcHelper.executeQuery(findByIdCTHD, id);
            if (rs.next()) {
                String maCTHD = rs.getString("maCthd");
                String maSp = rs.getString("maSanPham");
                sanPham sanPham = findByMaSP(maSp);
                int soLuong = rs.getInt("soLuong");
                String kichThuoc = rs.getString("kichThuoc");
                Float tongTien = rs.getFloat("tongTien");
                Date ngayLapHoaDon = rs.getDate("ngayLapHoaDon");
                String maHoaDon = rs.getString("maHoaDon");
                hoaDon hd = findByMaHD(maHoaDon);
                chiTietHoaDon cthd = new chiTietHoaDon(maCTHD, sanPham, soLuong, kichThuoc, tongTien, ngayLapHoaDon, hd);
                return cthd;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("findByIdCTHD: " + e.getMessage());
        }
        return null;
    }

    public void deleteCTHD(String id) {
        try {
            jdbcHelper.executeUpdate(deleteCTHD, id);
        } catch (Exception e) {
            System.out.println("DeleteCTHD :" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteHD(String id) {
        try {
            jdbcHelper.executeUpdate(deleteHD, id);
        } catch (Exception e) {
            System.out.println("DeleteHD :" + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String> getYear() {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.prepareStatement(getYear).executeQuery();
            while (rs.next()) {
                String year = rs.getString("year");
                list.add(year);
            }
            return list;
        } catch (Exception e) {
            System.out.println("GetYear :" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Object[]> thongKeTheoNam(int year) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(thongKeBanHangTheoNam, year);
            while (rs.next()) {
                Integer soHoaDon = rs.getInt("SoHoaDon");
                Float tongTien = rs.getFloat("TongTien");
                Float tienVAT = rs.getFloat("TienVAT");
                Object[] obj = new Object[]{soHoaDon, tongTien, tienVAT};
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Thống kê theo năm: " + e.getMessage());
        }
        return list;
    }

    public List<Object[]> thongKeTheoQuy(int year, int datePart) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(thongKeBanHangTheoQuy, year, datePart);
            while (rs.next()) {
                Integer soHoaDon = rs.getInt("SoHoaDon");
                Float tongTien = rs.getFloat("TongTien");
                Float tienVAT = rs.getFloat("TienVAT");
                Object[] obj = new Object[]{soHoaDon, tongTien, tienVAT};
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Thống kê theo quý: " + e.getMessage());
        }
        return list;
    }

    public List<Object[]> thongKeTheoThang(int year, int month) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(thongKeBanHangTheoThang, year, month);
            while (rs.next()) {
                Integer soHoaDon = rs.getInt("SoHoaDon");
                Float tongTien = rs.getFloat("TongTien");
                Float tienVAT = rs.getFloat("TienVAT");
                Object[] obj = new Object[]{soHoaDon, tongTien, tienVAT};
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Thống kê theo tháng: " + e.getMessage());
        }
        return list;
    }

    public void updateCTHD(String kichThuoc, int soLuong, Float tongTien, String maChiTietHoaDon) {
        try {
            jdbcHelper.executeUpdate(updateCTHD, kichThuoc, soLuong, tongTien, maChiTietHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Update CTHD : " + e.getMessage());
        }
    }

    public void insertTemp(List<Object> obj) {
        try {
            Object[] objArray = (Object[]) obj.get(0);
            System.out.println(objArray[0] + " " + objArray[1] + " " + objArray[2] + " " + objArray[3] + " " + objArray[4] + " " + objArray[5] + " " + objArray[6]);

            jdbcHelper.executeUpdate(insertTemp, objArray[0], objArray[1], objArray[2], objArray[3], objArray[4], objArray[5], objArray[6]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Insert cthd1: " + e.getMessage());
        }
    }

    public List<Object[]> chartByMonth(int year) {
        List<Object[]> f = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(chartByMonth, year);
            while (rs.next()) {
                int thang = rs.getInt("Thang");
                Float tongTien = rs.getFloat("TongTien");
                Object[] o = new Object[]{thang, tongTien};
                f.add(o);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "ChartByMonth");
        }
        return f;
    }

    public List<Object[]> chartByDatepart(int year) {
        List<Object[]> f = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(chartByDatePart, year);
            while (rs.next()) {
                int quy = rs.getInt("Quy");
                Float tongTien = rs.getFloat("TongTien");
                Object[] o = new Object[]{quy, tongTien};
                f.add(o);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "ChartByDatepart");
        }
        return f;
    }

    public List<Object[]> chartByYear() {
        List<Object[]> f = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.executeQuery(chartByYear);
            while (rs.next()) {
                int nam = rs.getInt("Nam");
                Float tongTien = rs.getFloat("TongTien");
                Object[] o = new Object[]{nam, tongTien};
                f.add(o);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "ChartByYear");
        }
        return f;
    }

    public kichThuoc findSizes(String maSp, String size) {
        kichThuoc k = null;
        try {
            ResultSet rs = jdbcHelper.executeQuery(findSizes, maSp,size);
            if(rs.next()){
                String maSanPham = rs.getString("maSanPham");
                String kichThuocs = rs.getString("kichThuoc");
                int soLuong = rs.getInt("soLuong");
                k = new kichThuoc(findByMaSP(maSanPham), kichThuocs, soLuong);
            }
        } catch (Exception e) {
            System.out.println("FindSize: "+e.getMessage());
            e.printStackTrace();
        }
        return k;
    }

    public void updateKho(int soLuong, String maSp, String kichThuoc) {
        try {
            jdbcHelper.executeUpdate(khoSP, soLuong, soLuong, maSp, kichThuoc);
        } catch (Exception e) {
            System.out.println("updateKho: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
