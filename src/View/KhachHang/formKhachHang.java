package View.KhachHang;

import Model.khachHang;
import Model.nhanVien;
import com.pro1041.dao.DAO_banHang;
import com.pro1041.dao.DAO_khachHang;
import com.pro1041.util.DateHelper;
import com.pro1041.util.DialogHelper;
import com.pro1041.util.ShareHelper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.sql.Date;

import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
/**
 *
 * @author admin
 */
public class formKhachHang extends javax.swing.JInternalFrame {

    DefaultTableModel md = new DefaultTableModel();
    List<khachHang> list = new ArrayList<>();
    DAO_khachHang dao = new DAO_khachHang();
    int index = 1;
    int current = -1;
    DefaultTableModel thongKe = new DefaultTableModel();
    private TableRowSorter<DefaultTableModel> rowSorter;

    /**
     * Creates new form myy
     */
    public formKhachHang() {
        initComponents();
        btnSua.setEnabled(false);
        btnXacnhan.setEnabled(false);
        btnXoa.setEnabled(false);
        if(!ShareHelper.USER.getChucVu()){
            jTabbedPane1.removeTabAt(1);
        }
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        load();
    }

    public void filltoform() {
        md = (DefaultTableModel) tblModel.getModel();
        md.setRowCount(0);

        for (khachHang kh : list) {
            String gt = "Nữ";
            if (kh.isGioiTinh() == true) {
                gt = "Nam";
            }
            md.addRow(new Object[]{
                kh.getMaKhachHang(), kh.getHoVaTen(), kh.getNgaySinh(), gt, kh.getSoDienThoai(),
                kh.getEmail()
            });
        }
    }

    public void load() {
        try {
            list.clear();
            list = dao.getSelectAll();
            filltoform();
        } catch (Exception e) {
            System.out.println("lôii" + e.getMessage());
        }
    }

    public void locGT(String gtinh) {
        try {
            list.clear();
            list = dao.locGT(gtinh);
            filltoform();

        } catch (Exception e) {
            System.out.println("loi" + e.getMessage());
        }
    }

    public void filltotable(int index) {
        lblKH.setText(list.get(index).getMaKhachHang());
        txtHoten.setText(list.get(index).getHoVaTen());
        date.setDate(list.get(index).getNgaySinh());
        txtSDt.setText(list.get(index).getSoDienThoai());
        if (list.get(index).isGioiTinh() == true) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtEmail.setText(list.get(index).getEmail());
        btnThem.setEnabled(false);
        btnSua.setEnabled(true);
        btnXoa.setEnabled(true);

    }

    public void reset() {
        lblKH.setText("");
        txtEmail.setText("");
        txtHoten.setText("");
        date.setDate(DateHelper.now());
        txtSDt.setText("");

    }

    public void deletekh(String id) {
        try {
            if (!ShareHelper.USER.getChucVu()) {
                System.out.println(ShareHelper.USER.getChucVu().toString());
                DialogHelper.alert("bạn không có quyền xóa học viên");
            } else if (dao.findKH(id) != null && ShareHelper.USER.getChucVu()) {
                System.out.println(ShareHelper.USER.getChucVu().toString());

                dao.delete(id);
                load();
                DialogHelper.alert("xóa thành công");
            } else {
                DialogHelper.alert("mã khách hàng không tồn tại");
                return;
            }
        } catch (Exception e) {
            System.out.println("lỗii" + e.getMessage());
        }

    }

    public void addkh() {
        try {
            String makh = "KH" + System.currentTimeMillis();
            lblKH.setText(makh);
            if (txtHoten.getText().isEmpty()) {
                DialogHelper.alert("Không được để trống họ và tên");
                return;
            }
            String hoVaTen = txtHoten.getText();
            java.util.Date ngaySinh = date.getDate();
            java.sql.Date ngaySinhSQL = new java.sql.Date(ngaySinh.getTime());
            boolean gt = false;
            if (rdoNam.isSelected()) {
                gt = true;
            }
            String sd = "0\\d{9}";
            String sdt = txtSDt.getText();
            if (sdt.matches(sd)) {
                txtSDt.getText();
            } else {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ");
                return;
            }
            String MauEmail = "\\w+@\\w+(\\.\\w+)";//\\w cụm chữ tùy ý + là 1 tối đa 1 lần
            String s = txtEmail.getText();
            if (s.matches(MauEmail)) {
                txtEmail.getText();

            } else {

                JOptionPane.showMessageDialog(null, "email sai!!");
                return;
            }
            khachHang kh = new khachHang(makh, hoVaTen, ngaySinhSQL, gt, sdt, s);
            dao.insert(kh);
            System.out.println();
            load();
            DialogHelper.alert("thêm thành công");

        } catch (Exception e) {
            System.out.println("loii" + e.getMessage());
        }
    }

    public void updatekh() {
        try {
            String makh = lblKH.getText();
            if (txtHoten.getText().isEmpty()) {
                DialogHelper.alert("Không được để trống họ và tên");
                return;
            }
            String hoVaTen = txtHoten.getText();
            java.util.Date ngaySinh = date.getDate();
            java.sql.Date ngaySinhSQL = new java.sql.Date(ngaySinh.getTime());
            boolean gt = false;
            if (rdoNam.isSelected()) {
                gt = true;
            }
            String sd = "0\\d{9}";
            String sdt = txtSDt.getText();
            if (sdt.matches(sd)) {
                txtSDt.getText();
            } else {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ");
                return;
            }
            String MauEmail = "\\w+@\\w+(\\.\\w+)";//\\w cụm chữ tùy ý + là 1 tối đa 1 lần
            String s = txtEmail.getText();
            if (s.matches(MauEmail)) {
                txtEmail.getText();

            } else {

                JOptionPane.showMessageDialog(null, "email sai!!");
                return;
            }
            khachHang kh = new khachHang(makh, hoVaTen, ngaySinhSQL, gt, sdt, s);
            dao.update(kh);
            load();
            DialogHelper.alert("update thành công");

        } catch (Exception e) {
            System.out.println("loi" + e.getMessage());
        }

    }

    public void timKiemTen(String name) {
        try {
            list.clear();
            list = dao.timkiemTen(name);
            filltoform();
        } catch (Exception e) {
            System.out.println("");
        }
    }

    public void timKiemMa(String ma) {
        try {
            list.clear();
            list = dao.timKiemMa(ma);
            filltoform();

        } catch (Exception e) {

        }
    }

    public void thongKe(Date ngay) {
        thongKe = (DefaultTableModel) tblThongke.getModel();
        thongKe.setRowCount(0);
        try {
            List<Object[]> listt = dao.thongKe(ngay);
            for (Object[] ob : listt) {
                System.out.println(ob[0] + " " + ob[1] + " " + ob[2] + " " + ob[3]);
                String ma = (String) ob[0];
                String ten = (String) ob[1];
                int soLan = (Integer) ob[2];
                float tongTien = (float) ob[3];
                thongKe.addRow(new Object[]{ma, ten, soLan, tongTien});
                tblThongke.setModel(thongKe);
            }
        } catch (Exception e) {
            System.out.println("loi" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void search_hd(String name, String id) {
        rowSorter = new TableRowSorter<>(md);
        tblModel.setRowSorter(rowSorter);

        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Kiểm tra và thêm RowFilter cho điều kiện 'name' nếu 'name' không rỗng
        if (!name.isEmpty()) {
            RowFilter<Object, Object> nameFilter = RowFilter.regexFilter(name, 1);
            filters.add(nameFilter);
        }

        // Kiểm tra và thêm RowFilter cho điều kiện 'sdt' nếu 'sdt' không rỗng
        if (!id.isEmpty()) {
            RowFilter<Object, Object> sdtFilter = RowFilter.regexFilter(id, 0);
            filters.add(sdtFilter);
        }

        // Tạo AndFilter để kết hợp các RowFilter thành một
        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        rowSorter.setRowFilter(combinedFilter);
    }

    public void chartKhachHang() {
        try {
            String dateString = DateHelper.toString(date1.getDate(), "yyyy-MM-dd");
            if (dateString != null) {
                java.sql.Date dateS = new java.sql.Date(DateHelper.toDate(dateString, "yyyy-MM-dd").getTime());
                List<Object[]> listChart = dao.thongKe(dateS);
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (Object[] o : listChart) {
                    System.out.println(o[3] + " " + o[1]);
                    dataset.setValue((Number) o[3], "Tiền", (Comparable) o[1]);
                }
                JFreeChart chart = ChartFactory.createBarChart("Tiền mua theo tháng", "Thống kê khách hàng", "Doanh thu", dataset, PlotOrientation.VERTICAL, false, true, false);
                chart.setTitle("Tiền mua theo tháng");
                chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18)); // Đặt kiểu chữ cho tiêu đề
                CategoryPlot z = chart.getCategoryPlot();
                z.getRenderer().setSeriesPaint(0, new Color(59, 89, 152));
                z.setRangeGridlinePaint(Color.black);
                ChartPanel chartPanel = new ChartPanel(chart);

                // Đặt kích thước cho ChartPanel
                Dimension panelSize = panelThongKe.getSize();
                chartPanel.setPreferredSize(panelSize);

                panelThongKe.removeAll();
                panelThongKe.add(chartPanel);
                panelThongKe.revalidate();
                panelThongKe.repaint();
            }
        } catch (Exception e) {
            System.out.println("ChartKhachHang: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblModel = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtHoten = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JCheckBox();
        rdoNu = new javax.swing.JCheckBox();
        btnXacnhan = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtSDt = new javax.swing.JTextField();
        lblKH = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        date = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboLoc = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtTimten = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThongKe = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongke = new javax.swing.JTable();
        date1 = new com.toedter.calendar.JDateChooser();
        panelThongKe = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1000, 700));

        jTabbedPane1.setBackground(new java.awt.Color(248, 247, 241));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 800));

        tblModel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Họ tên", "Ngày sinh", "Giới tính", "Số điện thoại", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblModel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblModelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblModel);

        jPanel3.setBackground(new java.awt.Color(248, 247, 241));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setEnabled(false);
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel4.setText(" Họ Tên");

        txtHoten.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel5.setText(" Ngày Sinh");

        jLabel6.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel6.setText(" Giới Tính");

        buttonGroup1.add(rdoNam);
        rdoNam.setText(" Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText(" Nữ");

        btnXacnhan.setBackground(new java.awt.Color(145, 169, 120));
        btnXacnhan.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        btnXacnhan.setText(" Xác nhận");
        btnXacnhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacnhanActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        btnThem.setText(" Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(225, 185, 87));
        btnSua.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        btnSua.setText(" Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(194, 0, 0));
        btnXoa.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText(" Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(102, 153, 255));
        btnPrev.setText(" <<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnNext.setForeground(new java.awt.Color(102, 153, 255));
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel7.setText(" Số Điện Thoại");

        txtSDt.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        txtSDt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDtActionPerformed(evt);
            }
        });

        lblKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel8.setText(" Email");

        txtEmail.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(122, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lblKH, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnXacnhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addComponent(btnXoa)
                        .addGap(15, 15, 15))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(btnThem)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEmail)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                        .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtHoten, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSDt)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(35, 35, 35))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNext)
                .addGap(43, 43, 43))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblKH, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacnhan)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrev)
                    .addComponent(btnNext))
                .addGap(45, 45, 45))
        );

        jPanel4.setBackground(new java.awt.Color(248, 247, 241));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel1.setText(" Lọc");

        cboLoc.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        cboLoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lọc theo giới tính", "Nam", "Nữ ", "Tất cả" }));
        cboLoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel2.setText(" Tìm theo tên");

        txtTimten.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        txtTimten.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimtenCaretUpdate(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel3.setText(" Tìm theo mã");

        txtMa.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        txtMa.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtMaCaretUpdate(evt);
            }
        });
        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(txtTimten, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtTimten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(" Khách hàng", jPanel1);

        jPanel2.setBackground(new java.awt.Color(248, 247, 241));

        btnThongKe.setText(" Thống kê");
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        tblThongke.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Số lần thanh toán", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblThongke);

        panelThongKe.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThongKe)
                        .addGap(0, 374, Short.MAX_VALUE))
                    .addComponent(panelThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThongKe))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(panelThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thống kê", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updatekh();
        reset();
        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnXacnhan.setEnabled(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        try {
            if (index <= 0) {
                index = list.size() - 1; // Set index bằng list.size() - 1 khi index về 0
            } else {
                index--;
            }
            filltotable(index);
        } catch (Exception e) {
            index = current - 1;
            if (index < 0) {
                index = 0;
            }
            filltotable(index);
            System.out.println("Error in prev(): " + e.getMessage());
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void txtSDtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDtActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void tblModelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblModelMouseClicked
        index = tblModel.getSelectedRow();
        filltotable(index);
    }//GEN-LAST:event_tblModelMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deletekh(lblKH.getText());
        reset();
        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnXacnhan.setEnabled(true);
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        btnXacnhan.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        String makh = "KH" + System.currentTimeMillis();
        lblKH.setText(makh);


    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXacnhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacnhanActionPerformed
        addkh();
        reset();
    }//GEN-LAST:event_btnXacnhanActionPerformed

    private void txtMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaActionPerformed

    private void txtTimtenCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimtenCaretUpdate
        search_hd(txtTimten.getText(), txtMa.getText());
    }//GEN-LAST:event_txtTimtenCaretUpdate

    private void txtMaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtMaCaretUpdate
        search_hd(txtTimten.getText(), txtMa.getText());
    }//GEN-LAST:event_txtMaCaretUpdate

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        try {
            if (index >= list.size() - 1) { // Nếu index >= list.size() - 1, gán index = 0
                index = 0;
            } else {
                index++;
            }
            filltotable(index);
        } catch (Exception e) {
            System.out.println("Error in next(): " + e.getMessage());
            index = current - 1;
            if (index < 0) {
                index = 0;
            }
            filltotable(index);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void cboLocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocItemStateChanged
        try {
            String gioiTinh = (String) cboLoc.getSelectedItem();

            locGT(gioiTinh);
            cboLoc.removeItem("Lọc theo giới tính");

        } catch (Exception e) {
            System.out.println("cboChuyenDeItem:" + e.getMessage());
        }
    }//GEN-LAST:event_cboLocItemStateChanged

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        try {
            String dateString = DateHelper.toString(date1.getDate(), "yyyy-MM-dd");
            if (dateString != null) {
                java.sql.Date dateS = new java.sql.Date(DateHelper.toDate(dateString, "yyyy-MM-dd").getTime());
                System.out.println(dateS);
                thongKe((Date) dateS);
                chartKhachHang();
            } else {
                DialogHelper.alert("Vui lòng chọn ngày cần thống kê!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThongKeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnXacnhan;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboLoc;
    private com.toedter.calendar.JDateChooser date;
    private com.toedter.calendar.JDateChooser date1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblKH;
    private javax.swing.JPanel panelThongKe;
    private javax.swing.JCheckBox rdoNam;
    private javax.swing.JCheckBox rdoNu;
    private javax.swing.JTable tblModel;
    private javax.swing.JTable tblThongke;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoten;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtSDt;
    private javax.swing.JTextField txtTimten;
    // End of variables declaration//GEN-END:variables
}
