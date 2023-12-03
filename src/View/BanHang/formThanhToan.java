/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View.BanHang;

import Model.hoaDon;
import Model.khachHang;
import Model.kichThuoc;
import Model.sanPham;
import static View.BanHang.QRCodeGenerator.generateQR;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pro1041.dao.DAO_banHang;
import com.pro1041.util.DateHelper;
import com.pro1041.util.DialogHelper;
import com.pro1041.util.ShareHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import com.pro1041.dao.DAO_banHang;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DecimalFormat;
import static View.BanHang.thanhToan.tongTien;
import static View.BanHang.thanhToan.list;
import static View.BanHang.thanhToan.modelHd;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HUNG
 */
public class formThanhToan extends javax.swing.JDialog {

    public static hoaDon hoaDon;
    public static List<Object[]> list;
    public static List<Object> objects = new ArrayList<>();
    public static String path;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0 VNĐ");

    /**
     * Creates new form formThanhToan_1
     */
    public formThanhToan(java.awt.Frame parent, boolean modal, hoaDon hoaDon, List<Object[]> list) {
        super(parent, modal);
        this.hoaDon = hoaDon;
        this.list = list;
        initComponents();
        setLocationRelativeTo(null);
        hienThiThanhToan();
    }

    public void hienThiThanhToan() {
        lbl_banHang_maHoaDon.setText(hoaDon.getMaHoaDon());
        lbl_banHang_maKhachHang.setText(hoaDon.getMaKhachHang().getMaKhachHang());
        lbl_banHang_tenKh.setText(hoaDon.getMaKhachHang().getHoVaTen());
        lbl_banHang_tongThanhToan.setText(decimalFormat.format(tongTien));
    }

    public void thanhToan() {
        try {
            try {
                if (Integer.parseInt(txt_banHang_tienKhachDua.getText()) <= 0) {
                    DialogHelper.alert("Vui lòng nhập số tiền");
                    return;
                }
                if (txt_banHang_tienKhachDua.getText().length() == 0) {
                    DialogHelper.alert("Không được để trống");
                    return;
                } else if (Integer.parseInt(txt_banHang_tienKhachDua.getText()) - tongTien < 0) {
                    DialogHelper.alert("Số tiền bạn đưa không đủ");
                    return;
                }
            } catch (Exception e) {
                DialogHelper.alert("Tiền đưa phải là số!");
                e.printStackTrace();
                return;
            }

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
                Date date = new Date(DateHelper.toDate(dateStr, "yyyy-MM-dd").getTime());
                String kichThuoc = (String) obj[4];
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

                objects.clear();
                objects.add(new Object[]{maCthd, maSp, maHd, soLuong, tongTien, date, kichThuoc});
                dao.insertCTHD(objects);
                System.out.println("Đã thêm thành công cthd");
                System.out.println();
                tongTien = 0.0f;
                list.clear();
                modelHd.setRowCount(0);
                System.out.println(tongTien);
                dispose();
                this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }
            DialogHelper.alert("Đã thanh toán thành công!");
        } catch (Exception e) {
            System.out.println("ThanhToan");
        }
    }

    public void formBill() {
        try {
            MySwingWorkerBill worker = new MySwingWorkerBill(this, hoaDon, list);
            worker.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateQR(String url, int width, int height, String filePath) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);

        try {
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scanQR() throws IOException, WriterException {
        String url = "00020101021138540010A00000072701240006970422011003393530730208QRIBFTTA5303704" + String.valueOf(2000000) + "5802VN63044451"; // Địa chỉ URL của trang web
        path = "image/QR.png";
        QRCodeGenerator qr = new QRCodeGenerator();
        qr.qr();
        try {
            generateQR(url, 1250, 1250, path);
            System.out.println("QR code generated successfully.");
        } catch (WriterException e) {
            System.out.println("QR code generation failed. Error: " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_banHang_ngayLap = new javax.swing.JLabel();
        lbl_banHang_maHoaDon = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_banHang_maKhachHang = new javax.swing.JLabel();
        lbl_banHang_tenKh = new javax.swing.JLabel();
        lbl_banHang_tongThanhToan = new javax.swing.JLabel();
        txt_banHang_tienKhachDua = new javax.swing.JTextField();
        lbl_banHang_tienTraKhach = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnDong = new javax.swing.JButton();
        btn_thanhToan = new javax.swing.JButton();
        btnQR = new javax.swing.JButton();
        btn_inHoaDon = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setFont(new java.awt.Font("Lexend Deca", 1, 18)); // NOI18N
        jLabel1.setText("THANH TOÁN HÓA ĐƠN");

        lbl_banHang_ngayLap.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        lbl_banHang_ngayLap.setText("Ngày lập HD: 02-11-2023");
        lbl_banHang_ngayLap.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lbl_banHang_ngayLapAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_banHang_ngayLap)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lbl_banHang_ngayLap)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        lbl_banHang_maHoaDon.setFont(new java.awt.Font("Lexend Deca", 1, 18)); // NOI18N
        lbl_banHang_maHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel4.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel4.setText("Mã khách hàng:");

        jLabel5.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel5.setText("Tên khác hàng:");

        jLabel6.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel6.setText("Tiền khách đưa:");

        jLabel7.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel7.setText("Tổng thanh toán:");

        jLabel8.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel8.setText("Tiền trả khách:");

        lbl_banHang_maKhachHang.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N

        lbl_banHang_tenKh.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N

        lbl_banHang_tongThanhToan.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N

        txt_banHang_tienKhachDua.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_banHang_tienKhachDuaCaretUpdate(evt);
            }
        });
        txt_banHang_tienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_banHang_tienKhachDuaActionPerformed(evt);
            }
        });

        lbl_banHang_tienTraKhach.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        btnDong.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        btnDong.setText("Đóng");
        btnDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongActionPerformed(evt);
            }
        });
        jPanel2.add(btnDong);

        btn_thanhToan.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        btn_thanhToan.setText("Thanh toán");
        btn_thanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thanhToanActionPerformed(evt);
            }
        });
        jPanel2.add(btn_thanhToan);

        btnQR.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        btnQR.setText("Quét mã QR");
        btnQR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQRMouseClicked(evt);
            }
        });
        jPanel2.add(btnQR);

        btn_inHoaDon.setFont(new java.awt.Font("Lexend Deca", 0, 13)); // NOI18N
        btn_inHoaDon.setText("In hóa đơn");
        btn_inHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inHoaDonActionPerformed(evt);
            }
        });
        jPanel2.add(btn_inHoaDon);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(lbl_banHang_maHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_banHang_tienTraKhach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_banHang_tongThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_banHang_tenKh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_banHang_maKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_banHang_tienKhachDua, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_banHang_maHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(lbl_banHang_maKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(lbl_banHang_tenKh, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(lbl_banHang_tongThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_banHang_tienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_banHang_tienTraKhach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_banHang_tienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_banHang_tienKhachDuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_banHang_tienKhachDuaActionPerformed

    private void lbl_banHang_ngayLapAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbl_banHang_ngayLapAncestorAdded
        String date = DateHelper.toString(DateHelper.now(), "dd-MM-yyyy");
        lbl_banHang_ngayLap.setText("Ngày lập hóa đơn: " + date);
    }//GEN-LAST:event_lbl_banHang_ngayLapAncestorAdded

    private void txt_banHang_tienKhachDuaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_banHang_tienKhachDuaCaretUpdate
        try {
            Float tienKhachTra = Float.parseFloat(txt_banHang_tienKhachDua.getText());
            if ((tienKhachTra) < tongTien) {
                System.out.println("Trả không đủ ");
                lbl_banHang_tienTraKhach.setText("");
            } else {
                float tienTraKhach = tienKhachTra - tongTien;
                String formattedTienTraKhach = decimalFormat.format(tienTraKhach);
                lbl_banHang_tienTraKhach.setText(formattedTienTraKhach);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "Tiền đưa");
        }
    }//GEN-LAST:event_txt_banHang_tienKhachDuaCaretUpdate

    private void btnDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongActionPerformed
        dispose();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_btnDongActionPerformed

    private void btn_thanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thanhToanActionPerformed
        thanhToan();
    }//GEN-LAST:event_btn_thanhToanActionPerformed

    private void btn_inHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inHoaDonActionPerformed
        formBill();
    }//GEN-LAST:event_btn_inHoaDonActionPerformed

    private void btnQRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQRMouseClicked
        try {
            scanQR();
        } catch (IOException ex) {
            Logger.getLogger(formThanhToan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriterException ex) {
            Logger.getLogger(formThanhToan.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            MySwingWorkerQR worker = new MySwingWorkerQR(this, path);
            worker.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnQRMouseClicked

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
            java.util.logging.Logger.getLogger(formThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                formThanhToan dialog = new formThanhToan(new javax.swing.JFrame(), true, hoaDon, list);
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
    private javax.swing.JButton btnDong;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btn_inHoaDon;
    private javax.swing.JButton btn_thanhToan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_banHang_maHoaDon;
    private javax.swing.JLabel lbl_banHang_maKhachHang;
    private javax.swing.JLabel lbl_banHang_ngayLap;
    private javax.swing.JLabel lbl_banHang_tenKh;
    private javax.swing.JLabel lbl_banHang_tienTraKhach;
    private javax.swing.JLabel lbl_banHang_tongThanhToan;
    private javax.swing.JTextField txt_banHang_tienKhachDua;
    // End of variables declaration//GEN-END:variables
}
