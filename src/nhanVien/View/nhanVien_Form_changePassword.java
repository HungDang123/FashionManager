/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package nhanVien.View;

import nhanVien.Data.checkNhanVien;
import nhanVien.Data.nhanVien_data_DAO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author hnhut
 */
public class nhanVien_Form_changePassword extends javax.swing.JFrame {

    /**
     * Creates new form nhanVien_Form_changePasswrod
     */
    nhanVien_data_DAO dao = new nhanVien_data_DAO();
    private NhanVienCard nvC;
    private String maNhanVien;

    public nhanVien_Form_changePassword(String maNhanVien, NhanVienCard nvC) {
        this.nvC = nvC;
        initComponents();
        this.maNhanVien = maNhanVien;
        changePw_ID.setText("Mã nhân viên: " + maNhanVien);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
        changePw_ID = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        changePw_txt_newPW = new javax.swing.JTextField();
        changePw_btn_change = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thay Đổi Mật Khẩu");

        changePw_ID.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        changePw_ID.setText("Mã nhân viên");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel2.setText("Nhập mật khẩu mới");

        changePw_txt_newPW.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        changePw_txt_newPW.setMargin(new java.awt.Insets(5, 9, 5, 9));

        changePw_btn_change.setBackground(new java.awt.Color(153, 0, 0));
        changePw_btn_change.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        changePw_btn_change.setForeground(new java.awt.Color(153, 255, 255));
        changePw_btn_change.setText("CHANGE PASSWORD");
        changePw_btn_change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePw_btn_changeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(changePw_txt_newPW)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(changePw_ID))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(changePw_btn_change, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(changePw_ID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(changePw_txt_newPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(changePw_btn_change)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changePw_btn_changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePw_btn_changeActionPerformed
        // TODO add your handling code here:
        if (!checkNhanVien.validateInput(changePw_txt_newPW.getText(), "Password")) {
            JOptionPane.showMessageDialog(this, " // Kiểm tra mật khẩu: ít nhất 8 ký tự, ít nhất một chữ cái và một chữ số", "NO", JOptionPane.ERROR_MESSAGE);
        } else {
            int op = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thay đổi MẬT KHẨU của thành viên " + maNhanVien, "", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                dao.changePassword(changePw_txt_newPW.getText(), maNhanVien);
                JOptionPane.showMessageDialog(this, "Thay đổi password thành công", "Ok", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("update" + changePw_txt_newPW.getText());
                nvC.updatePassworđ(changePw_txt_newPW.getText());
                this.dispose();

            } else {
                System.out.println("no");
            }
        }
    }//GEN-LAST:event_changePw_btn_changeActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel changePw_ID;
    private javax.swing.JButton changePw_btn_change;
    private javax.swing.JTextField changePw_txt_newPW;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}