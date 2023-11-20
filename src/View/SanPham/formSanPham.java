/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View.SanPham;

import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import Model.sanPham;
import com.pro1041.dao.DAO_sanPham;
import java.awt.Dimension;
import View.SanPham.card;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author HUNG
 */
public class formSanPham extends javax.swing.JInternalFrame {
    
    String setDis = null;
    String hinhAnh = null;
    DAO_sanPham dao = new DAO_sanPham();
    List<sanPham> list = dao.getSelectAll();

    /**
     * Creates new form formSanPham
     */
    public formSanPham() {
        initComponents();
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        loadToData();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);

        // tìm sản phẩm theo tên
        txtFindByName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                findByName();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                findByName();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println(e);
            }
        });

        // tìm sản phẩm theo mã
        txtFindByID.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                findByID();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                findByID();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("");
            }
        });

        // sắp xếp
        cboSapXep.addItem("Tất cả");
        cboSapXep.addItem("Giá tăng dần");
        cboSapXep.addItem("Giá giảm dần");
        cboSapXep.addItem("Tên A -> Z");
        cboSapXep.addItem("Tên Z -> A");
        cboSapXep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sapXepDuLieu();
            }
        });

        // lọc
        cboLoc.addItem("Tất cả");
        cboLoc.addItem("Quần");
        cboLoc.addItem("Áo");
        cboLoc.addItem("Mũ");
        cboLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                locDuLieu();
            }
        });
    }
    
    public void loadToData() {
        try {
            list = dao.getSelectAll();
            fillCard();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void fillCard() {
        jPanel1.removeAll();
        for (sanPham sp : list) {
            card card = new card(sp);
            jPanel1.add(card);
            if (list.indexOf(sp) % 6 == 0) {
                if (list.indexOf(sp) != 0) {
                    jPanel1.setPreferredSize(new Dimension(700, jPanel1.getPreferredSize().height + 350));
                }
            }
        }
        repaint();
//        validate();
    }

    // tìm kiếm sản phẩm theo tên và mã sản phẩm
    public void findByName() {
        String searchTerm = txtFindByName.getText().trim().toLowerCase(); // getText FindByName
        // Lọc list theo từ tìm kiếm
        List<sanPham> filteredListName = list.stream()
                .filter(sp -> sp.getTenSanPham().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
        // Cập nhật lại danh sách
        displayFilteredResults(filteredListName);
    }
    
    private void displayFilteredResults(List<sanPham> filteredList) {
        jPanel1.removeAll();
        
        for (sanPham sp : filteredList) {
            card card = new card(sp);
            jPanel1.add(card);
            
            if (filteredList.indexOf(sp) % 6 == 0) {
                if (filteredList.indexOf(sp) != 0) {
                    jPanel1.setPreferredSize(new Dimension(700, jPanel1.getPreferredSize().height + 350));
                }
            }
        }
        
        repaint();
        validate();
    }
    
    public void findByID() {
        String searchTerm = txtFindByID.getText().trim().toLowerCase(); // getText FindByID
        // Lọc list theo từ tìm kiếm
        List<sanPham> filteredListID = list.stream()
                .filter(sp -> sp.getMaSanPham().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
        // Cập nhật lại danh sách
        displayFilteredResults(filteredListID);
    }

    // sắp xếp sản phẩm
    public void sapXepDuLieu() {
        String selectedOption = cboSapXep.getSelectedItem().toString();
        
        switch (selectedOption) {
            case "Tất cả":
                loadToData();
                break;
            case "Giá tăng dần":
                // Sắp xếp theo giá tăng dần
                Collections.sort(list, (sp1, sp2) -> Double.compare(sp1.getDonGia(), sp2.getDonGia()));
                break;
            case "Giá giảm dần":
                // Sắp xếp theo giá giảm dần
                Collections.sort(list, (sp1, sp2) -> Double.compare(sp2.getDonGia(), sp1.getDonGia()));
                break;
            case "Tên A -> Z":
                // Sắp xếp theo tên A -> Z
                Collections.sort(list, (sp1, sp2) -> sp2.getTenSanPham().compareToIgnoreCase(sp1.getTenSanPham()));
                break;
            case "Tên Z -> A":
                // Sắp xếp theo tên Z -> A
                Collections.sort(list, (sp1, sp2) -> sp1.getTenSanPham().compareToIgnoreCase(sp2.getTenSanPham()));
                break;
            default:
                break;
        }
//        fillCard();
        displayFilteredResults(list);
    }

    // lọc sản phẩm
    public void locDuLieu() {
        String selectedOption2 = cboLoc.getSelectedItem().toString();
        
        List<sanPham> filteredList = new ArrayList<>();
        
        switch (selectedOption2) {
            case "Tất cả":
                loadToData();
                return;
            case "Quần":
                // Lọc và chỉ hiển thị sản phẩm có chứa chữ "quần" trong tên
                List<sanPham> filteredListQuan = list.stream()
                        .filter(sp -> sp.getTenSanPham().toLowerCase().contains("quần"))
                        .collect(Collectors.toList());
                displayFilteredResults(filteredListQuan);
                return;
            case "Áo":
                // Lọc và chỉ hiển thị sản phẩm có chứa chữ "quần" trong tên
                List<sanPham> filteredListAo = list.stream()
                        .filter(sp -> sp.getTenSanPham().toLowerCase().contains("áo"))
                        .collect(Collectors.toList());
                displayFilteredResults(filteredListAo);
                return;
            default:
                break;
        }
//        fillCard();
        displayFilteredResults(filteredList);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cboLoc = new javax.swing.JComboBox<>();
        cboSapXep = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtFindByName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtFindByID = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1400, 800));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel1.setPreferredSize(new java.awt.Dimension(700, 1200));
        jScrollPane1.setViewportView(jPanel1);

        jLabel4.setText("Lọc");

        jLabel2.setText("Sắp xếp");

        btnThem.setBackground(new java.awt.Color(123, 213, 0));
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jLabel5.setText("Tìm theo tên");

        txtFindByName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFindByNameActionPerformed(evt);
            }
        });

        jLabel6.setText("Tìm theo mã");

        txtFindByID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFindByIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFindByName, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFindByID, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 969, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(16, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cboLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtFindByName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtFindByID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 669, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Thống kê", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        them themSP = new them(setDis);
        themSP.setVisible(true);
        themSP.setLocationRelativeTo(null);

    }//GEN-LAST:event_btnThemActionPerformed

    private void txtFindByNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFindByNameActionPerformed
        findByName();
    }//GEN-LAST:event_txtFindByNameActionPerformed

    private void txtFindByIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFindByIDActionPerformed
        findByID();
    }//GEN-LAST:event_txtFindByIDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cboLoc;
    private javax.swing.JComboBox<String> cboSapXep;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtFindByID;
    private javax.swing.JTextField txtFindByName;
    // End of variables declaration//GEN-END:variables
}
