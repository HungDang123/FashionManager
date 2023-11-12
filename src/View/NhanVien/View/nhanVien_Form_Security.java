/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.NhanVien.View;

import Model.nhanVien;
import View.NhanVien.Controller.UI_nhanVien;
import View.NhanVien.Data.nhanVien_data_DAO;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author hnhut
 */
public class nhanVien_Form_Security extends javax.swing.JFrame {

    /**
     * Creates new form nhanVien_From_fillMore
     */
    private NhanVienCard nvCard;

    public nhanVien_Form_Security(NhanVienCard nvCard) {
        initComponents();
        this.nvCard = nvCard;

        setSecurity();

        // Sự Kiện
        Security_txt_pw.addMouseListener(UI_nhanVien.getAction(Color.RED, Color.BLACK, Security_txt_pw, lb_pw));
        Security_txt_cccd.addMouseListener(UI_nhanVien.getAction(Color.RED, Color.BLACK, Security_txt_cccd, lb_cccd));
        Security_txt_email.addMouseListener(UI_nhanVien.getAction(Color.RED, Color.BLACK, Security_txt_email, lb_email));
        Security_txt_sdt.addMouseListener(UI_nhanVien.getAction(Color.RED, Color.BLACK, Security_txt_sdt, lb_sdt));

    }

    public void setSecurity() {
        nhanVien nv = new nhanVien_data_DAO().selectNhanVien_byID(nvCard.getNhanVienInstance().getMaNhanVien());

        hasInfor(Security_txt_cccd, nv.getCanCuocCongDan());
        hasInfor(Security_txt_email, nv.getEmail());
        hasInfor(Security_txt_pw, nv.getMatKhau());
        hasInfor(Security_txt_sdt, nv.getSoDienThoai());

        setLocationRelativeTo(null);
    }
    
    public void hasInfor(JTextField text,String data) {
        if (data.isEmpty()) {
            text.setText("Chưa có thông tin");
        }
        else{
            text.setText(data);
        }
    }

    public void getForm() {
        nvCard.getNhanVienInstance().setMaNhanVien(nvCard.getNhanVienInstance().getMaNhanVien());
        nvCard.getNhanVienInstance().setMatKhau(Security_txt_pw.getText());
        nvCard.getNhanVienInstance().setSoDienThoai(Security_txt_sdt.getText());
        System.out.println("sdT: "+Security_txt_sdt.getText());
        nvCard.getNhanVienInstance().setEmail(Security_txt_email.getText());
        nvCard.getNhanVienInstance().setCanCuocCongDan(Security_txt_cccd.getText());
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
        jLabel2 = new javax.swing.JLabel();
        lb_pw = new javax.swing.JLabel();
        lb_sdt = new javax.swing.JLabel();
        lb_email = new javax.swing.JLabel();
        lb_cccd = new javax.swing.JLabel();
        Security_txt_pw = new javax.swing.JTextField();
        Security_txt_sdt = new javax.swing.JTextField();
        Security_txt_email = new javax.swing.JTextField();
        Security_txt_cccd = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        panel_Security = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        nhanVien_sld_percentSecurity = new javax.swing.JSlider();
        nhanVien_container_error = new javax.swing.JPanel();
        nhanVien_btn_updateSecurity = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Tăng Cường Bảo Mật");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Bảo mật");

        lb_pw.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lb_pw.setText("Mật khẩu");

        lb_sdt.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lb_sdt.setText("Số điện thoại");

        lb_email.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lb_email.setText("Email");

        lb_cccd.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lb_cccd.setText("Số căn cước công dân");

        Security_txt_pw.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Security_txt_sdt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Security_txt_email.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Security_txt_cccd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Security_txt_cccd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Security_txt_cccdActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(255, 102, 102));
        jSeparator1.setForeground(new java.awt.Color(255, 102, 102));

        panel_Security.setBackground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Mức độ bảo mật");

        nhanVien_container_error.setLayout(new java.awt.GridLayout(0, 1));

        javax.swing.GroupLayout panel_SecurityLayout = new javax.swing.GroupLayout(panel_Security);
        panel_Security.setLayout(panel_SecurityLayout);
        panel_SecurityLayout.setHorizontalGroup(
            panel_SecurityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_SecurityLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_SecurityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nhanVien_sld_percentSecurity, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_SecurityLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(nhanVien_container_error, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_SecurityLayout.setVerticalGroup(
            panel_SecurityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_SecurityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(nhanVien_sld_percentSecurity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nhanVien_container_error, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );

        nhanVien_btn_updateSecurity.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        nhanVien_btn_updateSecurity.setText("Cập Nhật Thông Tin");
        nhanVien_btn_updateSecurity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nhanVien_btn_updateSecurity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhanVien_btn_updateSecurityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nhanVien_btn_updateSecurity))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Security_txt_cccd))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_email, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Security_txt_email))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Security_txt_sdt))
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_pw, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Security_txt_pw, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 40, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(panel_Security, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nhanVien_btn_updateSecurity))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_pw)
                    .addComponent(Security_txt_pw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_sdt)
                    .addComponent(Security_txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_email)
                    .addComponent(Security_txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_cccd)
                    .addComponent(Security_txt_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_Security, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Security_txt_cccdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Security_txt_cccdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Security_txt_cccdActionPerformed

    private void nhanVien_btn_updateSecurityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhanVien_btn_updateSecurityActionPerformed
        // TODO add your handling code here:
//        Kiểm Form
    getForm();
        new nhanVien_data_DAO().updateSecurity(nvCard.getNhanVienInstance());
        nvCard.updateCard(nvCard.getNhanVienInstance());
        JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành Công","Hồ Minh Nhựt", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_nhanVien_btn_updateSecurityActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Security_txt_cccd;
    private javax.swing.JTextField Security_txt_email;
    private javax.swing.JTextField Security_txt_pw;
    private javax.swing.JTextField Security_txt_sdt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lb_cccd;
    private javax.swing.JLabel lb_email;
    private javax.swing.JLabel lb_pw;
    private javax.swing.JLabel lb_sdt;
    private javax.swing.JButton nhanVien_btn_updateSecurity;
    private javax.swing.JPanel nhanVien_container_error;
    private javax.swing.JSlider nhanVien_sld_percentSecurity;
    private javax.swing.JPanel panel_Security;
    // End of variables declaration//GEN-END:variables
}
