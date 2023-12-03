/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View.BanHang;

import Model.hoaDon;
import Model.hoaDonContainer;
import Model.kichThuoc;
import Model.sanPham;
import com.pro1041.dao.DAO_banHang;
import com.pro1041.util.DateHelper;
import com.pro1041.util.DialogHelper;
import java.util.ArrayList;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Date;
import java.text.DecimalFormat;

/**
 *
 * @author HUNG
 */
public class formDanhSachCho extends javax.swing.JDialog {

    private static List<hoaDonContainer> listKh;
    DefaultTableModel model = new DefaultTableModel();
    int index = 0;
    DAO_banHang dao = new DAO_banHang();
    private TableRowSorter<DefaultTableModel> rowSorter;
    private List<Object> list = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat("#,##0 VNĐ");

    /**
     * Creates new form formDanhSachCho
     */
    public formDanhSachCho(java.awt.Frame parent, boolean modal, List<hoaDonContainer> listKh) {
        super(parent, modal);
        this.listKh = listKh;
        initComponents();
        setLocationRelativeTo(null);
        initTable();
        fillToTable();
    }

    public void initTable() {
        String[] cols = new String[]{"Mã hóa đơn", "Tên khách hàng", "Số điện thoại", "Tổng tiền"};
        model.setColumnIdentifiers(cols);
        tbl_banHang_danhSachCho.setModel(model);
    }

    public void fillToTable() {
        model.setRowCount(0);
        for (hoaDonContainer h : listKh) {
            float tongTien = 0.0f;
            for (int i = 0; i < h.getList().size(); i++) {
                Object[] objArray = (Object[]) h.getList().get(i);
                tongTien += (Float) objArray[7] + (Float) objArray[6];
            }
            model.addRow(new Object[]{h.getHd().getMaHoaDon(), h.getHd().getMaKhachHang().getHoVaTen(),
                h.getHd().getMaKhachHang().getSoDienThoai(), decimalFormat.format(tongTien)});
        }
    }

    public void delete() {
        index = tbl_banHang_danhSachCho.getSelectedRow();
        dao.deleteHD(listKh.get(index).getHd().getMaHoaDon());
        listKh.remove(index);
        fillToTable();
    }

    public void deleteAll() {
        for (int i = 0; i < listKh.size(); i++) {
            dao.deleteHD(listKh.get(i).getHd().getMaHoaDon());
        }
        listKh.removeAll(listKh);
        fillToTable();
    }

    public void search() {
        String find = txt_banHang_find.getText();
        rowSorter = new TableRowSorter<>(model);
        tbl_banHang_danhSachCho.setRowSorter(rowSorter);
        if (find.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> rowFiler = RowFilter.regexFilter(find, 0);
            rowSorter.setRowFilter(rowFiler);
        }
    }

    public void thanhToan() {
        try {
            index = tbl_banHang_danhSachCho.getSelectedRow();
            for (int i = 0; i < listKh.get(index).getList().size(); i++) {
                Object[] objArray = (Object[]) listKh.get(index).getList().get(i);
                if (objArray.length >= 7) {
                    String maCthd = "CTHD" + System.currentTimeMillis();
                    String maSp = ((String) objArray[0]);
                    System.out.println(objArray[5]);
                    int soLuong = Integer.parseInt(String.valueOf(objArray[5]));
                    float tongTien = (Float) objArray[6] + (Float) objArray[7];
                    java.sql.Date ngayLapHD = new java.sql.Date(DateHelper.now().getTime());
                    String maHd = listKh.get(index).getHd().getMaHoaDon();
                    String kichThuoc = (String) objArray[4];
                    sanPham sp = dao.findByMaSP(maSp);
                    try {
                        kichThuoc k = dao.findSizes(maSp, kichThuoc);
                        if (k != null) {
                            if (k.getsoLuong() - soLuong >= 0) {
                                dao.updateKho(soLuong, maSp, kichThuoc);
                                System.out.println("Đã cập nhật thành công kho");
                                System.out.println("Check có tồn kho");
                            } else {
                                DialogHelper.alert("Sản phẩm " + sp.getTenSanPham() + " có Size " + kichThuoc + " không còn hàng trong kho!");
                                return;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Check không tồn kho: " + e.getMessage());
                        e.printStackTrace();
                        return;
                    }
                    Object obj = new Object[]{maCthd, maSp, maHd, soLuong, tongTien, ngayLapHD, kichThuoc};
                    list.clear();
                    list.add(obj);
                    dao.insertCTHD(list);
                } else {
                    System.out.println("Mảng không đủ chiều dài");
                    return;
                }
            }
            listKh.remove(index);
            DialogHelper.alert("Thanh toán thành công");
            fillToTable();
        } catch (Exception e) {
            System.out.println("Thanh toán: " + e.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_banHang_danhSachCho = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txt_banHang_find = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btn_banHang_thanhToan = new javax.swing.JButton();
        btn_banHang_xoa = new javax.swing.JButton();
        btn_banHang_xoaTatCa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lexend Deca", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chờ thanh toán");

        tbl_banHang_danhSachCho.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        tbl_banHang_danhSachCho.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_banHang_danhSachCho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_banHang_danhSachChoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_banHang_danhSachCho);

        jLabel2.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        jLabel2.setText("Tìm kiếm");

        txt_banHang_find.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_banHang_findCaretUpdate(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        btn_banHang_thanhToan.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N
        btn_banHang_thanhToan.setText("Thanh toán");
        btn_banHang_thanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_thanhToanActionPerformed(evt);
            }
        });
        jPanel1.add(btn_banHang_thanhToan);

        btn_banHang_xoa.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N
        btn_banHang_xoa.setText("Xóa");
        btn_banHang_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_xoaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_banHang_xoa);

        btn_banHang_xoaTatCa.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N
        btn_banHang_xoaTatCa.setText("Xóa tất cả");
        btn_banHang_xoaTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_banHang_xoaTatCaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_banHang_xoaTatCa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_banHang_find, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_banHang_find, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_banHang_danhSachChoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_banHang_danhSachChoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_banHang_danhSachChoMouseClicked

    private void btn_banHang_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_xoaActionPerformed
        delete();
    }//GEN-LAST:event_btn_banHang_xoaActionPerformed

    private void btn_banHang_xoaTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_xoaTatCaActionPerformed
        deleteAll();
    }//GEN-LAST:event_btn_banHang_xoaTatCaActionPerformed

    private void txt_banHang_findCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_banHang_findCaretUpdate
        search();
    }//GEN-LAST:event_txt_banHang_findCaretUpdate

    private void btn_banHang_thanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_banHang_thanhToanActionPerformed
        thanhToan();
    }//GEN-LAST:event_btn_banHang_thanhToanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formDanhSachCho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formDanhSachCho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formDanhSachCho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formDanhSachCho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                formDanhSachCho dialog = new formDanhSachCho(new javax.swing.JFrame(), true, listKh);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_banHang_thanhToan;
    private javax.swing.JButton btn_banHang_xoa;
    private javax.swing.JButton btn_banHang_xoaTatCa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_banHang_danhSachCho;
    private javax.swing.JTextField txt_banHang_find;
    // End of variables declaration//GEN-END:variables
}
