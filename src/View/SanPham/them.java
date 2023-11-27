package View.SanPham;

import Model.sanPham;
import View.Main;
import com.pro1041.dao.DAO_sanPham;
import com.pro1041.util.DialogHelper;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

/**
 *
 * @author HOANG
 */
public class them extends javax.swing.JFrame {

    private static String setDis;
    DAO_sanPham dao = new DAO_sanPham();
    String hinhAnh = null;

    /**
     * Creates new form them
     */
    public them(String setDis) {
        initComponents();
        setLocationRelativeTo(null);
        this.setDis = setDis;
        clear();
        setDisable();
    }

    public void luu() {
        try {
            String maSP = lblMaSP.getText();
            String tenSP = txtTenSP.getText();
            String moTa = txtMoTa.getText();
            String mauSac = txtMauSac.getText();
            String xuatXu = txtXuatXu.getText();
            String loaiSP = cboLoaiSP.getSelectedItem().toString();
            String nhaCC = cboNhaCC.getSelectedItem().toString();
            float donGia = Float.parseFloat(txtGiaNhap.getText());
            float vat = Float.parseFloat(txtVAT.getText());
            String imageName = lblHinh.getToolTipText();
            sanPham sp = new sanPham(maSP, tenSP, loaiSP, xuatXu, donGia, nhaCC, moTa, mauSac, imageName,vat);
            if (dao.selectByID(maSP) == null) {
                dao.insert(sp);
                DialogHelper.alert("Thêm thành công");
            } else {
                dao.update(sp);
                DialogHelper.alert("Cập nhật thành công");
            }
            Main.sanPham.loadToData();
            System.out.println(dao.selectByID(maSP));
            clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setForm(sanPham sp) {
        lblMaSP.setText(sp.getMaSanPham());
        txtTenSP.setText(sp.getTenSanPham());
        txtMoTa.setText(sp.getMoTa());
        txtDonVi.setText(sp.getLoaiSanPham());
        txtGiaNhap.setText(String.valueOf(sp.getDonGia()));
        txtMauSac.setText(sp.getMauSac());
        txtXuatXu.setText(sp.getXuatXu());
        txtVAT.setText(String.valueOf(sp.getVAT()));
        txtS.setText("");
        txtL.setText("");
        txtM.setText("");
        txtXL.setText("");
        setIcon(new ImageIcon(this.getClass().getResource("/image/" + sp.getHinhAnh())), lblHinh);
    }

    public void setIcon(ImageIcon ic, JLabel jl) {
        jl.setIcon(new ImageIcon(ic.getImage().getScaledInstance(jl.getPreferredSize().width, jl.getPreferredSize().height, 0)));
    }

    public void clear() {
        txtTenSP.setText("");
        txtMoTa.setText("");
        txtDonVi.setText("");
        txtGiaNhap.setText("");
        txtMauSac.setText("");
        txtXuatXu.setText("");
        txtVAT.setText("");
        txtS.setText("");
        txtL.setText("");
        txtM.setText("");
        txtXL.setText("");
        String identity = dao.getSelectAll().get(dao.getSelectAll().size() - 1).getMaSanPham().split("SP")[1];
        String maSP
                = Integer.parseInt(identity) < 100 ? "SP0" + (Integer.parseInt(identity) + 1) : "SP" + (Integer.parseInt(identity) + 1);
        lblMaSP.setText(maSP);
    }

    public void setDisable() {
        if (setDis != null) {
//            btnLuu1.setEnabled(false);
            btnLuu1.setVisible(false);
            btnClearForm.setVisible(false);
            btnUpload.setEnabled(false);
            txtDonVi.setEnabled(false);
            txtGiaNhap.setEnabled(false);
            txtMauSac.setEnabled(false);
            txtMoTa.setEnabled(false);
            txtTenSP.setEnabled(false);
            txtXuatXu.setEnabled(false);
            txtVAT.setEnabled(false);
            txtL.setEnabled(false);
            txtM.setEnabled(false);
            txtS.setEnabled(false);
            txtXL.setEnabled(false);
            cboLoaiSP.setEnabled(false);
            cboNhaCC.setEnabled(false);

//            btnClearForm.setEnabled(false);
        } else {
//            btnLuu1.setEnabled(true);
            btnThoat.setVisible(false);
            btnLuu1.setVisible(true);
            btnClearForm.setVisible(true);
            btnUpload.setEnabled(true);
            txtDonVi.setEnabled(true);
            txtGiaNhap.setEnabled(true);
            txtMauSac.setEnabled(true);
            txtMoTa.setEnabled(true);
            txtTenSP.setEnabled(true);
            txtXuatXu.setEnabled(true);
            txtVAT.setEnabled(true);
            txtL.setEnabled(true);
            txtM.setEnabled(true);
            txtS.setEnabled(true);
            txtXL.setEnabled(true);
            cboLoaiSP.setEnabled(true);
            cboNhaCC.setEnabled(true);
//            btnClearForm.setEnabled(true);
        }
    }
    
    public void validateForm() {
        try {
            
        } catch (Exception e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnLuu1 = new javax.swing.JButton();
        btnClearForm = new javax.swing.JButton();
        txtTenSP = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        txtMauSac = new javax.swing.JTextField();
        txtXuatXu = new javax.swing.JTextField();
        txtGiaNhap = new javax.swing.JTextField();
        txtDonVi = new javax.swing.JTextField();
        txtVAT = new javax.swing.JTextField();
        cboLoaiSP = new javax.swing.JComboBox<>();
        lblMaSP = new javax.swing.JLabel();
        cboNhaCC = new javax.swing.JComboBox<>();
        lblHinh = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblSoLuong1 = new javax.swing.JLabel();
        txtS = new javax.swing.JTextField();
        txtL = new javax.swing.JTextField();
        txtXL = new javax.swing.JTextField();
        txtM = new javax.swing.JTextField();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel6.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel6.setText("Màu sắc");

        jLabel7.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel7.setText("Loại sản phẩm");

        jLabel8.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel8.setText("Giá nhập");

        jLabel9.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel9.setText("Đơn vị");

        jLabel10.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel10.setText("VAT");

        jLabel11.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel11.setText("Nhà cung cấp");

        jLabel12.setFont(new java.awt.Font("Lexend Deca", 1, 14)); // NOI18N
        jLabel12.setText("Số lượng");

        btnLuu1.setBackground(new java.awt.Color(123, 213, 0));
        btnLuu1.setFont(new java.awt.Font("Lexend Deca", 0, 12)); // NOI18N
        btnLuu1.setText("Lưu");
        btnLuu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuu1ActionPerformed(evt);
            }
        });

        btnClearForm.setBackground(new java.awt.Color(204, 204, 204));
        btnClearForm.setFont(new java.awt.Font("Lexend Deca", 0, 12)); // NOI18N
        btnClearForm.setText("Xóa trắng");
        btnClearForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFormActionPerformed(evt);
            }
        });

        txtTenSP.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtMoTa.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtMauSac.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtXuatXu.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtGiaNhap.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtDonVi.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtVAT.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        cboLoaiSP.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N
        cboLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quần", "Áo" }));

        lblMaSP.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblMaSP.setText("SP001");

        cboNhaCC.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N
        cboNhaCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ngo3K", "HoangBanh", "HungBanh" }));

        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblHinh.setPreferredSize(new java.awt.Dimension(126, 149));

        jLabel3.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel3.setText("Tên sản phẩm");

        lblSoLuong.setFont(new java.awt.Font("Lexend Deca", 0, 12)); // NOI18N
        lblSoLuong.setText("Size L:");

        btnUpload.setBackground(new java.awt.Color(123, 213, 0));
        btnUpload.setFont(new java.awt.Font("Lexend Deca", 0, 12)); // NOI18N
        btnUpload.setText("Tải lên");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel4.setText("Mô tả");

        jLabel5.setFont(new java.awt.Font("Lexend Deca", 1, 12)); // NOI18N
        jLabel5.setText("Xuất xứ");

        jLabel2.setFont(new java.awt.Font("Lexend Deca", 0, 12)); // NOI18N
        jLabel2.setText("Size S:");

        jLabel13.setFont(new java.awt.Font("Lexend Deca", 0, 12)); // NOI18N
        jLabel13.setText("Size M:");

        lblSoLuong1.setFont(new java.awt.Font("Lexend Deca", 0, 12)); // NOI18N
        lblSoLuong1.setText("Size XL:");

        txtS.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtL.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtXL.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        txtM.setFont(new java.awt.Font("Lexend Deca", 0, 14)); // NOI18N

        btnThoat.setBackground(new java.awt.Color(194, 0, 0));
        btnThoat.setFont(new java.awt.Font("Lexend Deca", 0, 12)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setText("Đóng");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btnUpload)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMaSP)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(241, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(lblSoLuong))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnLuu1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearForm))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtS, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtM)
                                .addComponent(txtXL)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txtL, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(lblSoLuong1)
                                .addGap(74, 74, 74))
                            .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMauSac, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtXuatXu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDonVi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVAT, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboNhaCC, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboLoaiSP, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(41, 41, 41))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnThoat)
                .addGap(106, 106, 106))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblMaSP)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(txtMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cboLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtVAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(cboNhaCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addComponent(jLabel12))
                    .addComponent(txtM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtXL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSoLuong)
                            .addComponent(lblSoLuong1)))
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLuu1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearForm, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLuu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuu1ActionPerformed
        luu();
    }//GEN-LAST:event_btnLuu1ActionPerformed

    private void btnClearFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFormActionPerformed
        clear();
    }//GEN-LAST:event_btnClearFormActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        try {
            JFileChooser jf = new JFileChooser("D:\\DuAn1\\DA1\\src\\image");
            jf.showOpenDialog(null);
            File file = jf.getSelectedFile();
            Image img = ImageIO.read(file);
            lblHinh.setToolTipText(file.getName());
            int width = lblHinh.getWidth();
            int height = lblHinh.getHeight();
            lblHinh.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi như chưa bao giờ được lỗi");
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

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
            java.util.logging.Logger.getLogger(them.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(them.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(them.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(them.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new them(setDis).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearForm;
    private javax.swing.JButton btnLuu1;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnUpload;
    private javax.swing.JComboBox<String> cboLoaiSP;
    private javax.swing.JComboBox<String> cboNhaCC;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblSoLuong1;
    private javax.swing.JTextField txtDonVi;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtL;
    private javax.swing.JTextField txtM;
    private javax.swing.JTextField txtMauSac;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtS;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtVAT;
    private javax.swing.JTextField txtXL;
    private javax.swing.JTextField txtXuatXu;
    // End of variables declaration//GEN-END:variables
}
