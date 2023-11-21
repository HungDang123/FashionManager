create table nhanVien(
maNhanVien varchar(50) primary key,
hoVaten nvarchar(50) not null,
chucVu bit not null,
ngaySinh date not null,
gioiTinh bit not null,
canCuocCongDan varchar(12) null,
soDienThoai varchar(12) null,
hinhAnh varchar(100) null,
email varchar(50) not null,
matKhau varchar(50) not null
)

create table khachHang(
maKhachHang varchar(50) primary key,
hoVaTen nvarchar(50) not null,
ngaySinh date not null,
gioiTinh bit not null,
soDienThoai varchar(12) null,
email varchar(50) not null
)

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
)
create table kichThuoc(
maKichThuoc int identity primary key,
maSanPham varchar(50) references sanPham(maSanPham),
kichThuoc varchar(50) null,
soLuong int not null
)
create table chiTietHoaDon(
maCthd varchar(50) primary key,
maSanPham varchar(50) references sanPham(maSanPham),
soLuong int not null,
tongTien money not null,
ngayLapHoaDon date default getdate(),
maHoaDon varchar(50) references hoaDon(maHoaDon),
kichThuoc varchar(10) null
)

create table hoaDon(
maHoaDon varchar(50) primary key,
maNhanVien varchar(50) references nhanVien(maNhanVien),
maKhachHang varchar(50) references khachHang(maKhachHang)
)