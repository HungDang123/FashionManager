package View.BanHang;

import Model.chiTietHoaDon;
import Model.hoaDon;
import Model.hoaDonContainer;
import Model.khachHang;
import Model.nhanVien;
import Model.sanPham;
import static View.BanHang.formThanhToan.hoaDon;
import static View.BanHang.formThanhToan.list;
import static View.BanHang.formThanhToan.objects;
import com.pro1041.dao.DAO_banHang;
import com.pro1041.dao.DAO_nhanVien;
import com.pro1041.util.DateHelper;
import com.pro1041.util.DialogHelper;
import com.pro1041.util.ShareHelper;
import com.pro1041.util.listData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static com.pro1041.util.ShareHelper.USER;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 *
 * @author HOANG
 */
public class thanhToan extends javax.swing.JInternalFrame implements Runnable {

    public static nhanVien nv = ShareHelper.USER;
    hoaDon hoaDon;
    DefaultTableModel modelHd = new DefaultTableModel();
    private JDesktopPane desktopPane;
    DAO_banHang dao = new DAO_banHang();
    List<sanPham> listSp = new ArrayList<>();
    List<Object[]> list = new ArrayList<>();
    List<Object[]> listHD = new ArrayList<>();
    List<hoaDonContainer> listKh = new ArrayList<>();
    List<chiTietHoaDon> listCTHD = new ArrayList<>();
    private static khachHang kh;
    DefaultTableModel modelSp = new DefaultTableModel();
    DefaultTableModel modelCthd;
    DefaultTableModel modelCthd2 = new DefaultTableModel();
    private int index = 0;
    listData ld = new listData();
    private static Float tongTien = 0.0f;
    private static String maHoaDon;
    int index1 = 0;
    String date;
    private TableRowSorter<DefaultTableModel> rowSorter;
    int indexRow;
    List<String> year;
    List<Object[]> thongke;

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    /**
     * Creates new form thanhToan
     */
    public thanhToan() {
        initComponents();
        initTableChiTietHD();
        loadDataCTHD();
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        initCboYear();
    }

    public void initTableChiTietHD() {
        modelCthd = new DefaultTableModel();
        String[] cols = new String[]{"Mã hóa đơn", "Mã khách hàng", "Tên khách hàng", "Ngày sinh", "SDT", "Nhân viên", "Ngày lập HD", "Thành tiền"};
        modelCthd.setColumnIdentifiers(cols);
        tblChiTietHD.setModel(modelCthd);
    }

    public void themKhachHang() {
        try {
            ShareHelper.SDT = txtSdt.getText();
            MySwingWorker worker = new MySwingWorker(this);
            worker.execute();
            kh = ShareHelper.khachHang;
        } catch (Exception e) {
        }
    }

    public void findByNumberPhone() {
        if (!(txtSdt.getText().length() == 0)) {
            String sdt = txtSdt.getText();
            kh = dao.findByNumber(sdt);
            if (kh != null) {
                ShareHelper.khachHang = kh;
                txtMaKh.setText(kh.getMaKhachHang());
                txtHoVaTen.setText(kh.getHoVaTen());
                if (kh.isGioiTinh() == true) {
                    txtGioiTinh.setText("Nam");
                } else {
                    txtGioiTinh.setText("Nữ");
                }
                txtNgaySinh.setText(DateHelper.toString(kh.getNgaySinh(), "yyyy-MM-dd"));
            } else {
                txtGioiTinh.setText("");
                txtHoVaTen.setText("");
                txtMaKh.setText("");
                txtNgaySinh.setText("");
            }
        } else {
            txtGioiTinh.setText("");
            txtHoVaTen.setText("");
            txtMaKh.setText("");
            txtNgaySinh.setText("");
        }
    }

    public void thongTinNhanVien() {
        DAO_nhanVien dao = new DAO_nhanVien();
        txt_banHang_MaNhanVien.setText(nv.getMaNhanVien());
        txt_banHang_tenNhanVien.setText(nv.getHoVaTen());
        date = DateHelper.toString(DateHelper.now(), "dd/MM/yyyy");
        txt_banHang_ngayLapHoaDon.setText(date);
        lbl_banHang_hoaDon.setText("KH" + System.currentTimeMillis());
    }

    public void fillToTableSp() {
        modelSp = (DefaultTableModel) tblSanPham.getModel();
        modelSp.setRowCount(0);
        for (sanPham sp : listSp) {
            modelSp.addRow(new Object[]{sp.getMaSanPham(), sp.getTenSanPham(), sp.getMoTa(), sp.getMauSac(), sp.getXuatXu(), sp.getDonGia(), sp.getNhaCungCap()});
            tblSanPham.setModel(modelSp);
        }

    }

    public void fillToTableHd() {
        modelCthd.setRowCount(0);
        for (chiTietHoaDon cthd : listCTHD) {
            hoaDon maHoaDon = cthd.getMaHoaDon();
            if (maHoaDon != null) {
                modelCthd.addRow(new Object[]{maHoaDon.getMaHoaDon(), maHoaDon.getMaKhachHang().getMaKhachHang(),
                    maHoaDon.getMaKhachHang().getHoVaTen(), maHoaDon.getMaKhachHang().getNgaySinh(),
                    maHoaDon.getMaKhachHang().getSoDienThoai(), maHoaDon.getMaNhanVien().getHoVaTen(), cthd.getNgayLapHoaDon(), cthd.getTongTien()});
                tblChiTietHD.setModel(modelCthd);
            } else {
                System.out.println("Null");
            }
        }
    }
    //    public void loadDataCTHD() {
    //        try {
    //            listCTHD = dao.getAllCTHD();
    //            fillToTableHd();
    //        } catch (Exception e) {
    //        }
    //    }

    public void loadDataCTHD() {
        try {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    if (!nv.getChucVu()) {
                        System.out.println(nv.getChucVu());
                        listCTHD = dao.getAllCTHD1(nv.getMaNhanVien()); 
                    } else {
                        System.out.println(nv.getChucVu());
                        listCTHD = dao.getAllCTHD();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        fillToTableHd();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Fill table: " + e.getMessage());
                    }
                }
            };

            worker.execute();
        } catch (Exception e) {
            System.out.println("Load data: " + e.getMessage());
        }
    }

    public void loadData() {
        try {
            listSp = dao.SelectSanPham();
            fillToTableSp();
        } catch (Exception e) {
            System.out.println("load data: " + e.getMessage());
        }
    }

    public void taoHoaDon() {
        maHoaDon = "HD" + System.currentTimeMillis();
        thongTinNhanVien();
        lbl_banHang_hoaDon.setText(maHoaDon);
        String maKhachHang = txtMaKh.getText();
        txt_banHang_tenNhanVien.setText(nv.getHoVaTen());
        txt_banHang_MaNhanVien.setText(nv.getMaNhanVien());
        hoaDon = new hoaDon(maHoaDon, nv, kh);
        System.out.println(hoaDon.toString());
        if (hoaDon != null) {
            dao.insertHoaDon(hoaDon);
        }
        System.out.println("thêm hóa đơn thành công");
        loadData();
    }

    public void SearchByName(String name) {
        listSp.clear();
        listSp = dao.searchByName(name);
        fillToTableSp();
    }

    public void SearchById(String id) {
        listSp.clear();
        listSp = dao.searchById(id);
        fillToTableSp();
    }

    public void SearchBoth(String name, String id) {
        listSp.clear();
        listSp = dao.searchBoth(name, id);
        fillToTableSp();
    }

    public void addThongTinHoaDon() {
        modelHd = (DefaultTableModel) tblHoaDon.getModel();
        sanPham selectedProduct = listSp.get(index);
        String kichThuoc = (String) cbo_banHang_kichThuoc.getSelectedItem();
        int soLuong = Integer.parseInt(txt_banHang_soLuong.getText());
        Float thanhTien = soLuong * selectedProduct.getDonGia();
        Float tienVat = thanhTien * (selectedProduct.getVAT() / 100);

        Object[] rowData = {selectedProduct.getMaSanPham(), selectedProduct.getTenSanPham(),
            selectedProduct.getMauSac(), selectedProduct.getDonGia(),
            kichThuoc, soLuong, tienVat, thanhTien};

        list.add(rowData);
        modelHd.addRow(new Object[]{selectedProduct.getTenSanPham(), selectedProduct.getMauSac(), selectedProduct.getDonGia(), kichThuoc, soLuong, tienVat, thanhTien});
        tongTien();
    }

    public void removeThongTinHoaDon(int indexRow) {
        list.remove(indexRow);
        modelHd.removeRow(indexRow);
    }

    public void tongTien() {
        tongTien = 0.0f;
        for (Object[] obj : list) {
            tongTien += (Float) obj[6] + (Float) obj[7];
        }
    }

    public void fillToTableHD() {
        modelHd.setRowCount(0);
        for (Object[] obj : list) {
            modelHd.addRow(new Object[]{obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7]});
            tblHoaDon.setModel(modelHd);
        }
    }

    public void updateTblHD() {
        indexRow = tblHoaDon.getSelectedRow();
        Object[] obj = list.get(indexRow);
        if (obj.length >= 8) {
            obj[4] = tblHoaDon.getValueAt(indexRow, 3);
            obj[5] = tblHoaDon.getValueAt(indexRow, 4);
            System.out.println(obj[5]);
            String valueFromColumn4 = (String) tblHoaDon.getValueAt(indexRow, 4);
            Integer intValue = Integer.valueOf(valueFromColumn4);
            Float floatValue = (Float) tblHoaDon.getValueAt(indexRow, 2);
            obj[7] = floatValue * intValue;
        }
        fillToTableHD();
    }

    public void formThanhToan() {
        try {
            MySwingWorkerThanhToan worker = new MySwingWorkerThanhToan(this, tongTien, hoaDon, list);
            worker.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " form thanh toan");
        }
    }

    public void deleteHoaDon(String id) {
        if (dao.findByMaHD(id) != null) {
            dao.deleteHoaDon(id);
            DialogHelper.alert("Xóa thành công!");
        }
    }

    public void search() {
        try {
            String maText = txt_banHang_TimTheoMa.getText();
            String tenText = txt_banHang_timTheoTen.getText();

            if (maText.length() > 0 && tenText.length() > 0) {
                SearchBoth(tenText, maText);
            } else if (maText.length() > 0) {
                SearchById(maText);
            } else if (tenText.length() > 0) {
                SearchByName(tenText);
            } else {
                loadData();
            }

            System.out.println(maText + ", " + tenText);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void waiting() {
        List<Object[]> newList = new ArrayList<>(list);
        listKh.add(new hoaDonContainer(hoaDon, newList));
        int count = 1;
        for (hoaDonContainer h : listKh) {
            System.out.println(h.getHd().getMaHoaDon() + " " + h.getHd().getMaKhachHang() + " " + h.getHd().getMaNhanVien());
            for (int i = 0; i < h.getList().size(); i++) {
                Object[] objArray = (Object[]) h.getList().get(i);
                if (objArray.length >= 6) {
                    System.out.println(objArray[0] + " " + objArray[1] + " " + objArray[2] + " " + objArray[3] + " " + objArray[4] + " " + objArray[5] + " " + objArray[6] + " " + objArray[7]);
                } else {
                    System.out.println("Mảng không đủ chiều dài");
                }
            }
            System.out.println("Nguoi thu " + (count));
            count++;
        }
        tongTien = 0.0f;
        list.clear();
        modelHd.setRowCount(0);
    }

    public void formDanhSachCho() {
        try {
            MySwingWorkerDanhSachCho worker = new MySwingWorkerDanhSachCho(this, listKh);
            worker.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " form thanh toan");
        }
    }

    public void fillToFormHD(int index1) {
        String maHD = listCTHD.get(index1).getMaHoaDon().getMaHoaDon();
        String maKH = listCTHD.get(index1).getMaHoaDon().getMaKhachHang().getMaKhachHang();
        String tenKH = listCTHD.get(index1).getMaHoaDon().getMaKhachHang().getHoVaTen();
        String ngaySinh = String.valueOf(listCTHD.get(index1).getMaHoaDon().getMaKhachHang().getNgaySinh());
        String sdt = String.valueOf(listCTHD.get(index1).getMaHoaDon().getMaKhachHang().getSoDienThoai());
        String tenNV = listCTHD.get(index1).getMaHoaDon().getMaNhanVien().getHoVaTen();
        String ngayLap = String.valueOf(listCTHD.get(index1).getNgayLapHoaDon());
        String tongTien = String.valueOf(listCTHD.get(index1).getTongTien());

        String maSP = listCTHD.get(index1).getMaSanPham().getMaSanPham();

        lbl_chiTietHoaDon_maHoaDon.setText(maHD);
        lbl_chiTietHoaDon_maKhachHang.setText(maKH);
        lbl_chiTietHoaDon_tenKhachHang.setText(tenKH);
        lbl_cthd_ngaySinh.setText(ngaySinh);
        lbl_cthd_sdt.setText(sdt);
        lbl_cthd_tenNhanVien.setText(tenNV);
        lbl_cthd_ngayLapHD.setText(ngayLap);
        lbl_cthd_tongTien.setText(tongTien);

        listHD = dao.getHD(maHD);
        fillToTableHD2();
    }

    public void fillToTableHD2() {
        modelCthd2 = (DefaultTableModel) tblChiTietHD2.getModel();
        modelCthd2.setRowCount(0);
        for (Object[] o : listHD) {
            modelCthd2.addRow(new Object[]{o[0], o[1], o[2], o[3], o[4], o[5], o[6]
            });
        }
    }

    public void deleteCTHD(String id) {
        if (dao.findByIdCTHD(id) != null) {
            dao.deleteCTHD(id);
            DialogHelper.alert("Xóa thành công!");
        } else {
            DialogHelper.alert("Mã chi tiết hóa đơn không tồn tại!");
        }
    }

    public void search_hd_ten(String name) {
        rowSorter = new TableRowSorter<>(modelCthd);
        tblChiTietHD.setRowSorter(rowSorter);
        if (name.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> rowFiler = RowFilter.regexFilter(name, 2);
            rowSorter.setRowFilter(rowFiler);
        }
    }

    public void search_hd_sdt(String sdt) {
        rowSorter = new TableRowSorter<>(modelCthd);
        tblChiTietHD.setRowSorter(rowSorter);
        if (sdt.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> rowFiler = RowFilter.regexFilter(sdt, 4);
            rowSorter.setRowFilter(rowFiler);
        }
    }

    public void tongTienGiam() {
        Comparator<chiTietHoaDon> com = new Comparator<chiTietHoaDon>() {
            @Override
            public int compare(chiTietHoaDon o1, chiTietHoaDon o2) {
                return Double.compare(o2.getTongTien(), o1.getTongTien());
            }
        };
        Collections.sort(listCTHD, com);
        fillToTableHd();
    }

    public void tongTienTang() {
        Comparator<chiTietHoaDon> com = new Comparator<chiTietHoaDon>() {
            @Override
            public int compare(chiTietHoaDon o1, chiTietHoaDon o2) {
                return Double.compare(o1.getTongTien(), o2.getTongTien());
            }
        };
        Collections.sort(listCTHD, com);
        fillToTableHd();
    }

    public void ngayLapGiam() {
        Comparator<chiTietHoaDon> com = new Comparator<chiTietHoaDon>() {
            @Override
            public int compare(chiTietHoaDon o1, chiTietHoaDon o2) {
                return o2.getNgayLapHoaDon().compareTo(o1.getNgayLapHoaDon());
            }
        };
        Collections.sort(listCTHD, com);
        fillToTableHd();
    }

    public void ngayLapTang() {
        Comparator<chiTietHoaDon> com = new Comparator<chiTietHoaDon>() {
            @Override
            public int compare(chiTietHoaDon o1, chiTietHoaDon o2) {
                return o1.getNgayLapHoaDon().compareTo(o2.getNgayLapHoaDon());
            }
        };
        Collections.sort(listCTHD, com);
        fillToTableHd();
    }

    public void formBill1() {
        try {
            chiTietHoaDon chiTiet = (chiTietHoaDon) listCTHD.get(index1);
            MySwingWorkerBill1 bill1 = new MySwingWorkerBill1(this, chiTiet, listHD);
            bill1.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bill 1" + e.getMessage());
        }
    }

    public void initCboYear() {
        year = dao.getYear();
        cboYear.removeAllItems();
        for (String s : year) {
            cboYear.addItem(s);
        }
    }

    public void selectThongKe() {
        boolean[] enableStatus = new boolean[]{false, false, false};
        int selectedIndex = cboThongKe.getSelectedIndex();
        for (int i = 0; i <= selectedIndex; i++) {
            enableStatus[i] = true;
            if (selectedIndex == 2) {
                enableStatus[1] = false;
            }
        }
        cboYear.setEnabled(enableStatus[0]);
        cboQuy.setEnabled(enableStatus[1]);
        cboThang.setEnabled(enableStatus[2]);

    }

    public void thongKeTheoNam() {
        int year = Integer.parseInt((String) cboYear.getSelectedItem());
        thongke = dao.thongKeTheoNam(year);

        if (!thongke.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for (Object[] o : thongke) {
                result.append(o[0]).append(" ").append(o[1]).append(" ").append(o[2]).append("\n");
            }
            System.out.println(result.toString());

            Object[] firstItem = thongke.get(0);
            lblSoHoaDon.setText("Số hóa đơn: " + firstItem[0]);
            lblTongTien.setText("Tổng tiền: " + firstItem[1]);
            lblTongThue.setText("Tổng thuế: " + firstItem[2]);
        }
    }

    public void thongKeTheoQuy() {
        int year = Integer.parseInt((String) cboYear.getSelectedItem());
        int quarter = Integer.parseInt((String) cboQuy.getSelectedItem());
        thongke = dao.thongKeTheoQuy(year, quarter);

        if (!thongke.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for (Object[] o : thongke) {
                result.append(o[0]).append(" ").append(o[1]).append(" ").append(o[2]).append("\n");
            }
            System.out.println(result.toString());

            Object[] firstItem = thongke.get(0);
            lblSoHoaDon.setText("Số hóa đơn: " + firstItem[0]);
            lblTongTien.setText("Tổng tiền: " + firstItem[1]);
            lblTongThue.setText("Tổng thuế: " + firstItem[2]);
        }
    }

    public void thongKeTheoThang() {
        int year = Integer.parseInt((String) cboYear.getSelectedItem());
        int month = Integer.parseInt((String) cboThang.getSelectedItem());
        thongke = dao.thongKeTheoThang(year, month);

        if (!thongke.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for (Object[] o : thongke) {
                result.append(o[0]).append(" ").append(o[1]).append(" ").append(o[2]).append("\n");
            }
            System.out.println(result.toString());

            Object[] firstItem = thongke.get(0);
            lblSoHoaDon.setText("Số hóa đơn: " + firstItem[0]);
            lblTongTien.setText("Tổng tiền: " + firstItem[1]);
            lblTongThue.setText("Tổng thuế: " + firstItem[2]);
        }
    }

    public void updateCTHD() {
        int i = tblChiTietHD2.getSelectedRow();
        Object[] o = listHD.get(i);
        o[3] = tblChiTietHD2.getValueAt(i, 3);
        o[4] = tblChiTietHD2.getValueAt(i, 4);
        String maCTHD = listCTHD.get(index1).getMaCthd();
        o[6] = Float.parseFloat(o[4].toString()) * Float.parseFloat(o[2].toString());
        System.out.println(o[3] + " " + o[4] + " " + o[6] + " " + maCTHD);
        dao.updateCTHD((String) o[3], Integer.parseInt(o[4].toString()), Float.parseFloat(o[6].toString()), maCTHD);
        System.out.println("Cập nhật thành công");
        fillToTableHD2();
    }

    public void xuatThongKe() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Thống kê bán hàng");

            XSSFRow row = null;
            Cell cell = null;
            // Tạo một đối tượng XSSFCellStyle
            XSSFCellStyle headerStyle = workbook.createCellStyle();
            // Đặt kích thước font chữ và in đậm cho ô
            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);
            headerStyle.setFont(font);
            // Đặt màu nền cho ô
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Tạo border cho ô
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            row = spreadsheet.createRow((short) 2);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH BÁN HÀNG");
            cell.setCellStyle(headerStyle);
            spreadsheet.setColumnWidth(0, 5000); // Đặt độ rộng cho cột 0

            row = spreadsheet.createRow((short) 3);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("NGÀY THỐNG KÊ");
            spreadsheet.setColumnWidth(1, 3000); // Đặt độ rộng cho cột 1
            spreadsheet.setColumnWidth(2, 3000); // Đặt độ rộng cho cột 2
            spreadsheet.setColumnWidth(3, 3000); // Đặt độ rộng cho cột 3

            // Tiếp tục tạo và định dạng các ô khác ở đây
            // Tạo border cho các ô trong bảng
            for (int rowIndex = 2; rowIndex <= 3; rowIndex++) {
                row = spreadsheet.getRow(rowIndex);
                for (int columnIndex = 0; columnIndex <= 3; columnIndex++) {
                    cell = row.getCell(columnIndex);
                    XSSFCellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setBorderTop(BorderStyle.THIN);
                    cellStyle.setBorderBottom(BorderStyle.THIN);
                    cellStyle.setBorderLeft(BorderStyle.THIN);
                    cellStyle.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(cellStyle);
                }
            }

            FileOutputStream out = new FileOutputStream(new File("D:/PRO1041/thongKeBanHang.xlsx"));
            workbook.write(out);
            out.flush();
            workbook.close();
            out.close();
            DialogHelper.alert("Xuất thống kê thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTemp() {
        try {
            DAO_banHang dao = new DAO_banHang();
            for (Object[] obj : list) {
                String maCthd = "CTHD" + System.currentTimeMillis();
                String maSp = (String) obj[0];
                System.out.println("Mã sp :" + maSp);
                String maHd = hoaDon.getMaHoaDon();
                int soLuong = (Integer) obj[5];
                System.out.println("Số lượng: " + soLuong);
                Float tongTien = (Float) obj[7] + (Float) obj[6];
                System.out.println("Tiền: " + tongTien);
                String dateStr = DateHelper.toString(DateHelper.now(), "yyyy-MM-dd");
                java.sql.Date date = new java.sql.Date(DateHelper.toDate(dateStr, "yyyy-MM-dd").getTime());
                String kichThuoc = (String) obj[4];
                objects.clear();
                objects.add(new Object[]{maCthd, maSp, maHd, soLuong, tongTien, date, kichThuoc});
                dao.insertTemp(objects);
                System.out.println("Đã thêm thành công vào bảng tạm");
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("temp");
        }
    }

    public void imageProduct(int index) {
//        try {
//            if (listSp.get(index).getHinhAnh().isEmpty()) {
//                pictureBoxImage.setImage(null);
//            } else {
//                pictureBoxImage.setImage(new ImageIcon(this.getClass().getResource("/image/" + listSp.get(index).getHinhAnh())));
//            }
//            pictureBoxImage.repaint(); // Make sure to repaint the PictureBox to reflect the changes.
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btn_banHang_taoHoaDon = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        txtSdt = new javax.swing.JTextField();
        txtMaKh = new javax.swing.JLabel();
        txtHoVaTen = new javax.swing.JLabel();
        txtGioiTinh = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lbl_banHang_hoaDon = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_banHang_MaNhanVien = new javax.swing.JLabel();
        txt_banHang_tenNhanVien = new javax.swing.JLabel();
        txt_banHang_ngayLapHoaDon = new javax.swing.JLabel();
        btn_banHang_huyHoaDon = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_banHang_timTheoTen = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_banHang_TimTheoMa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cbo_banHang_kichThuoc = new javax.swing.JComboBox<>();
        txt_banHang_soLuong = new javax.swing.JTextField();
        btn_banHang_themHoaDon = new javax.swing.JButton();
        pictureBoxImage = new swing.PictureBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        btn_banHang_xoaHoaDon = new javax.swing.JButton();
        btn_banHang_cho = new javax.swing.JButton();
        btn_banHang_danhSachCho = new javax.swing.JButton();
        btn_banHang_thanhToan = new javax.swing.JButton();
        lbl_banHang_tongTien = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lbl_chiTietHoaDon_maHoaDon = new javax.swing.JLabel();
        lbl_chiTietHoaDon_maKhachHang = new javax.swing.JLabel();
        lbl_chiTietHoaDon_tenKhachHang = new javax.swing.JLabel();
        lbl_cthd_ngaySinh = new javax.swing.JLabel();
        lbl_cthd_sdt = new javax.swing.JLabel();
        lbl_cthd_tenNhanVien = new javax.swing.JLabel();
        lbl_cthd_ngayLapHD = new javax.swing.JLabel();
        lbl_cthd_tongTien = new javax.swing.JLabel();
        btn_cthd_xoa = new javax.swing.JButton();
        btn_cthd_inHoaDon = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        cboSapXep = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        txt_cthd_theoSDT = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txt_cthd_theoTen = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietHD = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChiTietHD2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboThongKe = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cboThang = new javax.swing.JComboBox<>();
        cboYear = new javax.swing.JComboBox<>();
        cboQuy = new javax.swing.JComboBox<>();
        btnThongKe = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        lblSoHoaDon = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        lblTongTien = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lblTongThue = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1000, 700));
        setRequestFocusEnabled(false);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel4.setBackground(new java.awt.Color(255, 204, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 350));

        btn_banHang_taoHoaDon.setBackground(new java.awt.Color(32, 152, 185));
        btn_banHang_taoHoaDon.setText("Tạo hóa đơn");
        btn_banHang_taoHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_banHang_taoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_taoHoaDonActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Thông tin khách hàng");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Số điện thoại:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Mã KH:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Ngày sinh:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Giới tính:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Họ tên:");

        jButton5.setBackground(new java.awt.Color(123, 213, 0));
        jButton5.setText("Thêm mới KH");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtSdt.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSdtCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(btn_banHang_taoHoaDon))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSdt, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtGioiTinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtHoVaTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMaKh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(72, 72, 72))))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(44, 44, 44)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel20)
                        .addComponent(jLabel18)
                        .addComponent(jLabel12)
                        .addComponent(jLabel19))
                    .addContainerGap(178, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_banHang_taoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel11)
                .addGap(28, 28, 28)
                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaKh, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addComponent(txtHoVaTen, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(156, 156, 156)
                    .addComponent(jLabel12)
                    .addGap(52, 52, 52)
                    .addComponent(jLabel20)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel19)
                    .addGap(24, 24, 24)
                    .addComponent(jLabel18)
                    .addContainerGap(30, Short.MAX_VALUE)))
        );

        jPanel5.setBackground(new java.awt.Color(255, 204, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setPreferredSize(new java.awt.Dimension(250, 270));

        lbl_banHang_hoaDon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_banHang_hoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Mã nhân viên:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tên nhân viên:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Ngày lập hóa đơn:");

        btn_banHang_huyHoaDon.setBackground(new java.awt.Color(194, 0, 0));
        btn_banHang_huyHoaDon.setForeground(new java.awt.Color(242, 242, 242));
        btn_banHang_huyHoaDon.setText("Hủy HD");
        btn_banHang_huyHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_banHang_huyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_huyHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_banHang_hoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                    .addGap(34, 34, 34)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_banHang_MaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_banHang_tenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_banHang_ngayLapHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(13, 13, 13)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(btn_banHang_huyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lbl_banHang_hoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_banHang_MaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_banHang_tenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_banHang_ngayLapHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btn_banHang_huyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 204, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setPreferredSize(new java.awt.Dimension(690, 300));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("SẢN PHẨM");

        jLabel9.setText("Tìm theo tên");

        txt_banHang_timTheoTen.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_banHang_timTheoTenCaretUpdate(evt);
            }
        });

        jLabel10.setText("Tìm theo mã");

        txt_banHang_TimTheoMa.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_banHang_TimTheoMaCaretUpdate(evt);
            }
        });

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên", "Mô tả", "Màu sắc", "Xuất sứ", "Giá nhập", "Nhà cung cấp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        jLabel21.setText("Chọn chi tiết sản phẩm");

        jLabel22.setText("Kích thước:");

        jLabel23.setText("Số lượng:");

        cbo_banHang_kichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M", "L", "XL" }));
        cbo_banHang_kichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_banHang_kichThuocActionPerformed(evt);
            }
        });

        txt_banHang_soLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_banHang_soLuongActionPerformed(evt);
            }
        });

        btn_banHang_themHoaDon.setBackground(new java.awt.Color(123, 213, 0));
        btn_banHang_themHoaDon.setText("Thêm");
        btn_banHang_themHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_banHang_themHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_themHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_banHang_timTheoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(txt_banHang_TimTheoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btn_banHang_themHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_banHang_soLuong)
                            .addComponent(cbo_banHang_kichThuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pictureBoxImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_banHang_timTheoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txt_banHang_TimTheoMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(pictureBoxImage, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbo_banHang_kichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_banHang_soLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_banHang_themHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );

        jPanel7.setBackground(new java.awt.Color(255, 204, 153));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(800, 274));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setText("THÔNG TIN");

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sản phẩm", "Màu sản phẩm", "Giá bán", "Kích thước", "Số lượng", "Tiền VAT", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                tblHoaDonComponentRemoved(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel25.setText(" HÓA ĐƠN");

        btn_banHang_xoaHoaDon.setBackground(new java.awt.Color(194, 0, 0));
        btn_banHang_xoaHoaDon.setForeground(new java.awt.Color(242, 242, 242));
        btn_banHang_xoaHoaDon.setText("Xóa");
        btn_banHang_xoaHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_banHang_xoaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_xoaHoaDonActionPerformed(evt);
            }
        });

        btn_banHang_cho.setBackground(new java.awt.Color(255, 209, 84));
        btn_banHang_cho.setText("Chờ");
        btn_banHang_cho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_banHang_cho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_choActionPerformed(evt);
            }
        });

        btn_banHang_danhSachCho.setBackground(new java.awt.Color(62, 138, 204));
        btn_banHang_danhSachCho.setText("Danh sách chờ");
        btn_banHang_danhSachCho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_banHang_danhSachCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_danhSachChoActionPerformed(evt);
            }
        });

        btn_banHang_thanhToan.setBackground(new java.awt.Color(123, 213, 0));
        btn_banHang_thanhToan.setText("Thanh toán");
        btn_banHang_thanhToan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_banHang_thanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_thanhToanActionPerformed(evt);
            }
        });

        lbl_banHang_tongTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_banHang_tongTien.setForeground(new java.awt.Color(0, 0, 255));
        lbl_banHang_tongTien.setText("Tổng tiền: ");
        lbl_banHang_tongTien.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lbl_banHang_tongTienAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_banHang_tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btn_banHang_cho)
                        .addGap(30, 30, 30)
                        .addComponent(btn_banHang_danhSachCho)
                        .addGap(29, 29, 29)
                        .addComponent(btn_banHang_thanhToan))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(btn_banHang_xoaHoaDon)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel25)
                        .addGap(60, 60, 60)
                        .addComponent(btn_banHang_xoaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_banHang_thanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_banHang_danhSachCho, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_banHang_cho, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_banHang_tongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hóa đơn", jPanel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel8.setBackground(new java.awt.Color(255, 204, 153));
        jPanel8.setPreferredSize(new java.awt.Dimension(350, 450));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel27.setText("CHI TIẾT HÓA ĐƠN");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Mã hóa đơn");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Mã khách hàng");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Ngày sinh");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Tên khách hàng");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Tổng tiền");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Ngày lập hóa đơn");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("Nhân viên");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("Số điện thoại");

        btn_cthd_xoa.setBackground(new java.awt.Color(194, 0, 0));
        btn_cthd_xoa.setForeground(new java.awt.Color(242, 242, 242));
        btn_cthd_xoa.setText("Xóa");
        btn_cthd_xoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cthd_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cthd_xoaActionPerformed(evt);
            }
        });

        btn_cthd_inHoaDon.setBackground(new java.awt.Color(0, 153, 153));
        btn_cthd_inHoaDon.setText("In hóa đơn");
        btn_cthd_inHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cthd_inHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cthd_inHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cthd_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel28)
                        .addComponent(jLabel29)
                        .addComponent(jLabel31)
                        .addComponent(jLabel30)
                        .addComponent(jLabel35)
                        .addComponent(jLabel34)
                        .addComponent(jLabel33)
                        .addComponent(jLabel32)))
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btn_cthd_inHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl_cthd_ngayLapHD, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                        .addComponent(lbl_cthd_tenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_cthd_sdt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_cthd_ngaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_chiTietHoaDon_tenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_chiTietHoaDon_maKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_chiTietHoaDon_maHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_cthd_tongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(42, 42, 42))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_chiTietHoaDon_maHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_chiTietHoaDon_maKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_chiTietHoaDon_tenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_cthd_ngaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_cthd_sdt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_cthd_tenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_cthd_ngayLapHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_cthd_tongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(80, 80, 80)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cthd_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cthd_inHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(271, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 204, 153));
        jPanel9.setPreferredSize(new java.awt.Dimension(650, 450));

        jLabel47.setText("Sắp xếp theo");

        cboSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tổng tiền giảm", "Tổng tiền tăng", "Ngày lập giảm", "Ngày lập tăng" }));
        cboSapXep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSapXepItemStateChanged(evt);
            }
        });

        jLabel48.setText("Tìm theo tên");

        txt_cthd_theoSDT.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_cthd_theoSDTCaretUpdate(evt);
            }
        });

        jLabel44.setText("Tìm theo SDT");

        txt_cthd_theoTen.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_cthd_theoTenCaretUpdate(evt);
            }
        });

        tblChiTietHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblChiTietHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietHDMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblChiTietHD);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_cthd_theoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44)
                .addGap(12, 12, 12)
                .addComponent(txt_cthd_theoSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(txt_cthd_theoSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(txt_cthd_theoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 204, 153));
        jPanel10.setPreferredSize(new java.awt.Dimension(650, 207));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel45.setText("CHI TIẾT HÓA ĐƠN");

        tblChiTietHD2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Màu sắc", "Giá ", "Kích thước", "Số lượng ", "Tiền VAT", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietHD2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                tblChiTietHD2ComponentRemoved(evt);
            }
        });
        jScrollPane4.setViewportView(tblChiTietHD2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addGap(417, 417, 417))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 708, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Xem hóa đơn", jPanel2);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thống kê theo:");

        cboThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thống kê theo năm", "Thống kê theo quý", "Thống kê theo tháng" }));
        cboThongKe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThongKeItemStateChanged(evt);
            }
        });

        jLabel5.setText("Tháng");

        jLabel6.setText("Quý");

        jLabel7.setText("Năm");

        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cboThang.setEnabled(false);

        cboYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboYear.setEnabled(false);

        cboQuy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        cboQuy.setEnabled(false);

        btnThongKe.setText("Thống kê");
        btnThongKe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(541, 500));

        jPanel15.setBackground(new java.awt.Color(0, 102, 204));
        jPanel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel15.setPreferredSize(new java.awt.Dimension(200, 100));

        lblSoHoaDon.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblSoHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblSoHoaDon.setText("Số hóa đơn:");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblSoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblSoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 51, 51));
        jPanel16.setPreferredSize(new java.awt.Dimension(200, 100));

        lblTongTien.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 255, 255));
        lblTongTien.setText("Tổng tiền:");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(0, 204, 51));
        jPanel13.setPreferredSize(new java.awt.Dimension(200, 100));

        lblTongThue.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblTongThue.setForeground(new java.awt.Color(255, 255, 255));
        lblTongThue.setText("Tổng thuế:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTongThue, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblTongThue, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(374, Short.MAX_VALUE)))
        );

        jButton1.setText("Xuất thống kê");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(cboThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(57, 57, 57)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboQuy, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(56, 56, 56)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(55, 55, 55)
                            .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(278, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(318, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(384, 384, 384))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cboThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboQuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(780, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thống kê doanh thu", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_banHang_soLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_banHang_soLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_banHang_soLuongActionPerformed

    private void btn_banHang_huyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_huyHoaDonActionPerformed
        deleteHoaDon(lbl_banHang_hoaDon.getText());
        listSp.clear();
        fillToTableSp();
        modelHd.setRowCount(0);
        list.removeAll(list);
        pictureBoxImage.setImage(null);
        pictureBoxImage.repaint();
        txt_banHang_soLuong.setText("");
        txt_banHang_MaNhanVien.setText("");
        txt_banHang_tenNhanVien.setText("");
        lbl_banHang_hoaDon.setText("");
    }//GEN-LAST:event_btn_banHang_huyHoaDonActionPerformed

    private void btn_banHang_xoaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_xoaHoaDonActionPerformed
        indexRow = tblHoaDon.getSelectedRow();
        removeThongTinHoaDon(indexRow);
    }//GEN-LAST:event_btn_banHang_xoaHoaDonActionPerformed

    private void btn_banHang_thanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_thanhToanActionPerformed
        insertTemp();
        tongTien();
        formThanhToan();
    }//GEN-LAST:event_btn_banHang_thanhToanActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (txtSdt.getText().isEmpty()) {
            DialogHelper.alert("Vui lòng nhập số điện thoại");
            return;
        }
        try {
            if (ld.isSdt(txtSdt.getText())) {
                if (dao.findByNumber(txtSdt.getText()) != null) {
                    DialogHelper.alert("Số điện thoại này đã tồn tại");
                    return;
                } else {
                    themKhachHang();
                }
            } else {
                DialogHelper.alert("Vui lòng nhập đúng định dạng");
            }
        } catch (Exception e) {
            DialogHelper.alert("Số điện thoại phải bao gồm các chữ số");
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void cbo_banHang_kichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_banHang_kichThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_banHang_kichThuocActionPerformed

    private void txtSdtCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSdtCaretUpdate
        findByNumberPhone();
    }//GEN-LAST:event_txtSdtCaretUpdate

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        index = tblSanPham.getSelectedRow();
        imageProduct(index);
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btn_banHang_taoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_taoHoaDonActionPerformed
        if (kh == null) {
            DialogHelper.alert("Vui lòng nhập đúng số điện thoại");
            return;
        }
        taoHoaDon();
    }//GEN-LAST:event_btn_banHang_taoHoaDonActionPerformed

    private void txt_banHang_timTheoTenCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_banHang_timTheoTenCaretUpdate
        search();
    }//GEN-LAST:event_txt_banHang_timTheoTenCaretUpdate

    private void txt_banHang_TimTheoMaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_banHang_TimTheoMaCaretUpdate
        search();
    }//GEN-LAST:event_txt_banHang_TimTheoMaCaretUpdate

    private void btn_banHang_themHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_themHoaDonActionPerformed
        if (txt_banHang_soLuong.getText().isEmpty()) {
            DialogHelper.alert("Vui lòng nhập số lượng");
            return;
        }
        try {
            if (Integer.parseInt(txt_banHang_soLuong.getText()) < 0) {
                DialogHelper.alert("Số lượng phải lớn hơn 0");
            } else if (Integer.parseInt(txt_banHang_soLuong.getText()) > 0) {
                addThongTinHoaDon();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert("Số lượng phải là chữ số");
        }

    }//GEN-LAST:event_btn_banHang_themHoaDonActionPerformed

    private void lbl_banHang_tongTienAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbl_banHang_tongTienAncestorAdded
        new Thread(this).start();
    }//GEN-LAST:event_lbl_banHang_tongTienAncestorAdded

    private void btn_banHang_choActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_choActionPerformed
        waiting();
    }//GEN-LAST:event_btn_banHang_choActionPerformed

    private void btn_banHang_danhSachChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_danhSachChoActionPerformed
        formDanhSachCho();
    }//GEN-LAST:event_btn_banHang_danhSachChoActionPerformed

    private void tblChiTietHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietHDMouseClicked
        index1 = tblChiTietHD.getSelectedRow();
        fillToFormHD(index1);
    }//GEN-LAST:event_tblChiTietHDMouseClicked

    private void btn_cthd_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cthd_xoaActionPerformed
        if (nv.getChucVu()) {
            deleteCTHD(listCTHD.get(index1).getMaCthd());
            loadDataCTHD();
        } else {
            btn_cthd_xoa.setEnabled(false);
        }
    }//GEN-LAST:event_btn_cthd_xoaActionPerformed

    private void txt_cthd_theoTenCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_cthd_theoTenCaretUpdate
        search_hd_ten(txt_cthd_theoTen.getText());
    }//GEN-LAST:event_txt_cthd_theoTenCaretUpdate

    private void txt_cthd_theoSDTCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_cthd_theoSDTCaretUpdate
        search_hd_sdt(txt_cthd_theoSDT.getText());
    }//GEN-LAST:event_txt_cthd_theoSDTCaretUpdate

    private void cboSapXepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSapXepItemStateChanged
        if (cboSapXep.getSelectedIndex() == 0) {
            tongTienGiam();
        } else if (cboSapXep.getSelectedIndex() == 1) {
            tongTienTang();
        } else if (cboSapXep.getSelectedIndex() == 2) {
            ngayLapGiam();
        } else if (cboSapXep.getSelectedIndex() == 3) {
            ngayLapTang();
        }
    }//GEN-LAST:event_cboSapXepItemStateChanged

    private void btn_cthd_inHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cthd_inHoaDonActionPerformed
        formBill1();
    }//GEN-LAST:event_btn_cthd_inHoaDonActionPerformed

    private void tblHoaDonComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tblHoaDonComponentRemoved
        updateTblHD();
    }//GEN-LAST:event_tblHoaDonComponentRemoved

    private void cboThongKeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThongKeItemStateChanged
        selectThongKe();
    }//GEN-LAST:event_cboThongKeItemStateChanged

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        if (cboThongKe.getSelectedIndex() == 0) {
            thongKeTheoNam();
        } else if (cboThongKe.getSelectedIndex() == 1) {
            thongKeTheoQuy();
        } else if (cboThongKe.getSelectedIndex() == 2) {
            thongKeTheoThang();
        }
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void tblChiTietHD2ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tblChiTietHD2ComponentRemoved
        updateCTHD();
        fillToTableHD2();
    }//GEN-LAST:event_tblChiTietHD2ComponentRemoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        xuatThongKe();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btn_banHang_cho;
    private javax.swing.JButton btn_banHang_danhSachCho;
    private javax.swing.JButton btn_banHang_huyHoaDon;
    private javax.swing.JButton btn_banHang_taoHoaDon;
    private javax.swing.JButton btn_banHang_thanhToan;
    private javax.swing.JButton btn_banHang_themHoaDon;
    private javax.swing.JButton btn_banHang_xoaHoaDon;
    private javax.swing.JButton btn_cthd_inHoaDon;
    private javax.swing.JButton btn_cthd_xoa;
    private javax.swing.JComboBox<String> cboQuy;
    private javax.swing.JComboBox<String> cboSapXep;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JComboBox<String> cboThongKe;
    private javax.swing.JComboBox<String> cboYear;
    private javax.swing.JComboBox<String> cbo_banHang_kichThuoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblSoHoaDon;
    private javax.swing.JLabel lblTongThue;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lbl_banHang_hoaDon;
    private javax.swing.JLabel lbl_banHang_tongTien;
    private javax.swing.JLabel lbl_chiTietHoaDon_maHoaDon;
    private javax.swing.JLabel lbl_chiTietHoaDon_maKhachHang;
    private javax.swing.JLabel lbl_chiTietHoaDon_tenKhachHang;
    private javax.swing.JLabel lbl_cthd_ngayLapHD;
    private javax.swing.JLabel lbl_cthd_ngaySinh;
    private javax.swing.JLabel lbl_cthd_sdt;
    private javax.swing.JLabel lbl_cthd_tenNhanVien;
    private javax.swing.JLabel lbl_cthd_tongTien;
    private swing.PictureBox pictureBoxImage;
    private javax.swing.JTable tblChiTietHD;
    private javax.swing.JTable tblChiTietHD2;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JLabel txtGioiTinh;
    private javax.swing.JLabel txtHoVaTen;
    private javax.swing.JLabel txtMaKh;
    private javax.swing.JLabel txtNgaySinh;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JLabel txt_banHang_MaNhanVien;
    private javax.swing.JTextField txt_banHang_TimTheoMa;
    private javax.swing.JLabel txt_banHang_ngayLapHoaDon;
    private javax.swing.JTextField txt_banHang_soLuong;
    private javax.swing.JLabel txt_banHang_tenNhanVien;
    private javax.swing.JTextField txt_banHang_timTheoTen;
    private javax.swing.JTextField txt_cthd_theoSDT;
    private javax.swing.JTextField txt_cthd_theoTen;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        while (true) {
            try {
                tongTien();
                lbl_banHang_tongTien.setText("Tổng tiền: " + String.valueOf(tongTien) + " VNĐ");
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
