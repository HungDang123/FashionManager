create database quanLyCuaHangThoiTrang
use quanLyCuaHangThoiTrang
go
create table nhanVien(
maNhanVien varchar(50) primary key,
hoVaten nvarchar(50) not null,
chucVu bit not null,
ngaySinh date not null,
gioiTinh bit not null,
canCuocCongDan varchar(12) null,
soDienThoai varchar(12) null,
hinhAnh varchar(100) null,
email varchar(50)  null,
matKhau varchar(50) not null
)
go
create table khachHang(
maKhachHang varchar(50) primary key,
hoVaTen nvarchar(50) not null,
ngaySinh date not null,
gioiTinh bit not null,
soDienThoai varchar(12) null,
email varchar(50) not null
)
go
create table sanPham(
maSanPham varchar(50) primary key,
tenSanPham nvarchar(50) not null,
loaiSanPham nvarchar(50) not null,
xuatSu nvarchar(50) not null,
donGia money not null,
nhaCungCap nvarchar(50) not null,
moTa nvarchar(225) null,
mauSac nvarchar(20) null,
hinhAnh varchar(50) null,
vat float null
)go
create table kichThuoc(
maKichThuoc int identity primary key,
maSanPham varchar(50) references sanPham(maSanPham),
kichThuoc varchar(50) null,
soLuong int not null
)
go
create table hoaDon(
maHoaDon varchar(50) primary key,
maNhanVien varchar(50) references nhanVien(maNhanVien),
maKhachHang varchar(50) references khachHang(maKhachHang)
)
go
create table chiTietHoaDon(
maCthd varchar(50) primary key,
maSanPham varchar(50) references sanPham(maSanPham),
soLuong int not null,
tongTien money not null,
ngayLapHoaDon date default getdate(),
maHoaDon varchar(50) references hoaDon(maHoaDon),
kichThuoc varchar(10) null
)
go
create table chiTietHoaDonTam(
maCthd varchar(50) primary key,
maSanPham varchar(50) references sanPham(maSanPham),
soLuong int not null,
tongTien money not null,
ngayLapHoaDon date default getdate(),
maHoaDon varchar(50) references hoaDon(maHoaDon),
kichThuoc varchar(10) null
)
go 
CREATE FUNCTION dbo.GetHoaDonCountByMaNhanVien
(
    @maNhanVien varchar(50)
)
RETURNS INT
AS
BEGIN
    DECLARE @count INT;

    SELECT @count = COUNT(*)
    FROM hoaDon
    WHERE maNhanVien = @maNhanVien;

    RETURN @count;
END;

--SELECT
--    COUNT(c.maCthd) AS SoHoaDon,
--    SUM(c.tongTien) AS TongTien,
--    SUM(s.vat * c.tongTien / 100) AS TienVAT
--	FROM
--    chiTietHoaDon c inner join sanPham s
--	on c.maSanPham = s.maSanPham
--	inner join hoaDon h on c.maHoaDon = h.maHoaDon
--	where h.maNhanVien = 'admin1' and year(c.ngayLapHoaDon) = 2022 or MONTH(c.ngayLapHoaDon) = ''


--SELECT c.maCthd,c.maSanPham, c.soLuong,c.tongTien,c.ngayLapHoaDon,c.maHoaDon,c.kichThuoc,
--    SUM(c.tongTien) OVER (PARTITION BY c.maHoaDon) AS 'tongTiens'
--FROM chiTietHoaDon c
--GROUP BY c.maCthd, c.maSanPham,c.soLuong,c.tongTien,c.ngayLapHoaDon,c.maHoaDon,c.kichThuoc;

--select sp.* from sanPham sp where sp.tenSanPham COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%' 
--select sp.* from sanPham sp where sp.tenSanPham COLLATE Vietnamese_CI_AS LIKE N'%' + ? + N'%' and sp.maSanPham LIKE N'%' + ? + N'%'

--SELECT c.maCthd,c.maSanPham, c.soLuong,c.tongTien,c.ngayLapHoaDon,c.maHoaDon,c.kichThuoc, SUM(c.tongTien)
--OVER (PARTITION BY c.maHoaDon) AS 'tongTiens'
--            FROM chiTietHoaDon c inner join hoaDon d on d.maHoaDon = c.maHoaDon 
--            where d.maNhanVien = 'nv0001'
--            GROUP BY c.maCthd, c.maSanPham,c.soLuong,c.tongTien,c.ngayLapHoaDon,c.maHoaDon,c.kichThuoc;