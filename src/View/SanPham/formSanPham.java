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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hoang
 */
public class formSanPham extends javax.swing.JInternalFrame {

    String setDis = null;
    String hinhAnh = null;
    DAO_sanPham dao = new DAO_sanPham();
    List<sanPham> list = dao.getSelectAll();
    List<sanPham> filteredList = new ArrayList<>();
    List<sanPham> filteredListChoice = new ArrayList<>(list);

    DefaultTableModel modelThongKe;

    /**
     * Creates new form formSanPham
     */
    public formSanPham() {
        initComponents();
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        loadToData();
        initThongKe();
        fillThongKe();
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
            e.printStackTrace();
            e.printStackTrace();
        }

    }

    public void fillCard() {
        jPanel1.removeAll();
        for (sanPham sp : list) {
            card card = new card(sp);
            System.out.println(card.getPreferredSize().width);
            jPanel1.add(card);
            if (list.indexOf(sp) % 6 == 0) {
                if (list.indexOf(sp) != 0) {
                    jPanel1.setPreferredSize(new Dimension(700, jPanel1.getPreferredSize().height + 350));
                }
            }
        }
        repaint();
    }

    // Tìm kiếm sản phẩm theo tên
    public void findByName() {
        String searchTerm = txtFindByName.getText().trim().toLowerCase(); // getText findByName
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

    // Tìm kiếm sản phẩm theo mã
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

        List<sanPham> filteredOption = new ArrayList<>(filteredListChoice);

        for (sanPham pham : filteredOption) {
            System.out.println(pham.toString());
        }

        switch (selectedOption) {
            case "Tất cả":
                loadToData();
                return;
            case "Giá tăng dần":
                // Sắp xếp theo giá tăng dần
                Collections.sort(filteredOption, (sp1, sp2) -> Double.compare(sp1.getDonGia(), sp2.getDonGia()));
                break;
            case "Giá giảm dần":
                // Sắp xếp theo giá giảm dần
                Collections.sort(filteredOption, (sp1, sp2) -> Double.compare(sp2.getDonGia(), sp1.getDonGia()));
                break;
            case "Tên A -> Z":
                // Sắp xếp theo tên A -> Z
                Collections.sort(filteredOption, (sp1, sp2) -> sp2.getTenSanPham().compareToIgnoreCase(sp1.getTenSanPham()));
                break;
            case "Tên Z -> A":
                // Sắp xếp theo tên Z -> A
                Collections.sort(filteredOption, (sp1, sp2) -> sp1.getTenSanPham().compareToIgnoreCase(sp2.getTenSanPham()));
                break;
            default:
                break;
        }
        displayFilteredResults(filteredOption);
    }

    // Lọc sản phẩm
    public void locDuLieu() {
        String selectedOption2 = cboLoc.getSelectedItem().toString();

        switch (selectedOption2) {
            case "Tất cả":
                loadToData();
                return;
            case "Quần":
                // Lọc và chỉ hiển thị sản phẩm có chứa chữ "quần" trong tên
                filteredListChoice = list.stream()
                        .filter(sp -> sp.getTenSanPham().toLowerCase().contains("quần"))
                        .collect(Collectors.toList());
                displayFilteredResults(filteredListChoice);
                return;
            case "Áo":
                // Lọc và chỉ hiển thị sản phẩm có chứa chữ "Áo" trong tên
                filteredListChoice = list.stream()
                        .filter(sp -> sp.getTenSanPham().toLowerCase().contains("áo"))
                        .collect(Collectors.toList());
                displayFilteredResults(filteredListChoice);
                return;
            case "Mũ":
                // Lọc và chỉ hiển thị sản phẩm có chứa chữ "Áo" trong tên
                filteredListChoice = list.stream()
                        .filter(sp -> sp.getTenSanPham().toLowerCase().contains("mũ"))
                        .collect(Collectors.toList());
                displayFilteredResults(filteredListChoice);
                return;
            default:
                break;
        }
        displayFilteredResults(filteredList);
    }

    // Bảng thống kê, dùng custom chart
    public void initThongKe() {
        modelThongKe = new DefaultTableModel();
        String[] cols = new String[]{"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Số tiền"};
        modelThongKe.setColumnIdentifiers(cols);
        tblThongKe.setModel(modelThongKe);
    }

    public void fillThongKe() {
        modelThongKe.setRowCount(0);
        List<Object[]> thongKeSP = dao.thongKe();

        if (thongKeSP != null) {
            for (Object[] rowData : thongKeSP) {
                modelThongKe.addRow(rowData);
            }
        }

        tblThongKe.setModel(modelThongKe);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKe = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1000, 800));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 1200));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));
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
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblThongKe);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Bảng thể hiện sản phẩm tồn kho");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Thống kê sản phẩm đã bán");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(199, 199, 199)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jLabel1)))
                .addContainerGap(446, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thống kê", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
                .addContainerGap())
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
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblThongKe;
    private javax.swing.JTextField txtFindByID;
    private javax.swing.JTextField txtFindByName;
    // End of variables declaration//GEN-END:variables
}
