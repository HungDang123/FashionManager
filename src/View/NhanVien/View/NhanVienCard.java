package View.NhanVien.View;

import Model.nhanVien;
import View.NhanVien.Controller.Controller_nhanVien_Crud;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class NhanVienCard extends JPanel {

    private nhanVien nhanVienInstance;
    static nhanVien_InternalForm container;

    private JLabel labelMaNV;
    private JLabel labelChucVu;
    private JLabel labelImage;
    private JLabel labelName;
    private JLabel labelNgaySinh;
    private JLabel labelGioiTinh;
    private JLabel labelMatKhau;

    public NhanVienCard(nhanVien nv, nhanVien_InternalForm container) {
        this.nhanVienInstance = nv;
        NhanVienCard.container = container;
        this.setLayout(new BorderLayout());
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        if (nv.getHinhAnh() == null) {
            nv.setHinhAnh("C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041_5\\src\\image\\user.png");
        }

        // Header (Mã nhân viên, Chức Vụ)
        JPanel pnHeader = new JPanel(new GridBagLayout());
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.anchor = GridBagConstraints.WEST;
        headerConstraints.insets = new Insets(5, 10, 5, 10);

        labelMaNV = new JLabel("Mã nhân viên: " + nv.getMaNhanVien());
        labelChucVu = new JLabel("Chức vụ: " + (nv.getChucVu() ? "Quản Lý" : "Nhân Viên"));

        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        pnHeader.add(labelMaNV, headerConstraints);

        headerConstraints.gridy = 1;
        pnHeader.add(labelChucVu, headerConstraints);

        this.add(pnHeader, BorderLayout.NORTH);

        // Midder (Image, Name, NgaySinh, GioiTinh, CCCD)
        JPanel pnMidder = new JPanel(new GridBagLayout());
        GridBagConstraints midderConstraints = new GridBagConstraints();
        midderConstraints.anchor = GridBagConstraints.WEST;
        midderConstraints.insets = new Insets(5, 10, 5, 10);

        labelImage = new JLabel();
        Icon icon = new ImageIcon(nv.getHinhAnh());
        labelImage.setIcon(icon);

        labelName = new JLabel("Họ và tên: " + nv.getHoVaTen());
        labelMatKhau = new JLabel("Mật khẩu: " + nv.getMatKhau());
        System.out.println("image:" + nv.getHinhAnh());
        java.sql.Date sqlDate = new java.sql.Date(nv.getNgaySinh().getTime());
        Font font = new Font("verdana", Font.PLAIN, 14);
        labelNgaySinh = new JLabel("Ngày Sinh: " + sqlDate);
        labelNgaySinh.setFont(font);
        labelName.setFont(font);
        Font fontBold = new Font("verdana", Font.BOLD, 14);
        labelChucVu.setFont(fontBold);
        labelMaNV.setFont(fontBold);
        labelMatKhau.setFont(font);

        labelGioiTinh = new JLabel("Giới Tính: " + (nv.getGioiTinh() ? "Nam" : "Nữ"));
        labelGioiTinh.setFont(font);
//        JLabel labelCCCD = new JLabel("CCCD: " + nv.getCanCuocCongDan());
//        JLabel labelEmail = new JLabel("Email: "+nv.getGmail());

        midderConstraints.gridx = 0;
        midderConstraints.gridy = 0;
        pnMidder.add(labelImage, midderConstraints);

        midderConstraints.gridy = 1;
        pnMidder.add(labelName, midderConstraints);

        midderConstraints.gridy = 2;
        pnMidder.add(labelNgaySinh, midderConstraints);

        midderConstraints.gridy = 3;
        pnMidder.add(labelGioiTinh, midderConstraints);

//        midderConstraints.gridy = 4;
//        pnMidder.add(labelCCCD, midderConstraints);
//        midderConstraints.gridy = 5;
//        pnMidder.add(labelEmail,midderConstraints);
        midderConstraints.gridy = 4;
        pnMidder.add(labelMatKhau, midderConstraints);

        this.add(pnMidder, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(370, 320));

        // Footer
        JPanel pnFooter = new JPanel();

        JButton btnEdit = new JButton("Edit");

        JButton btnDelete = new JButton("Delete");

        JButton btnChangePw = new JButton("Change Password");

        JButton btn_Security = new JButton("Security");

        btnEdit.addActionListener(Controller_nhanVien_Crud.getController_nvEdit(this));
        btnDelete.addActionListener(Controller_nhanVien_Crud.getController_nvDelete(this));
        btnChangePw.addActionListener(Controller_nhanVien_Crud.getController_nvChangePw(this));
        btn_Security.addActionListener(Controller_nhanVien_Crud.getController_nvUpdateSecurity(this));

        pnFooter.add(btn_Security);
        pnFooter.add(btnDelete);
        pnFooter.add(btnEdit);
        pnFooter.add(btnChangePw);

        this.add(pnFooter, BorderLayout.SOUTH);

    }

    public nhanVien getNhanVienInstance() {
        return nhanVienInstance;
    }

    public void setNhanVienInstance(nhanVien nhanVienInstance) {
        this.nhanVienInstance = nhanVienInstance;
    }

    public void updatePassworđ(String Password) {
        this.nhanVienInstance.setMatKhau(Password);
        this.labelMatKhau.setText("Mật khẩu: " + this.nhanVienInstance.getMatKhau());
    }

    public void updateParent() {
        container.update();
    }

    public void updateCard(nhanVien nvM) {
        this.nhanVienInstance = nvM;
        labelMaNV.setText("Mã nhân viên: " + nhanVienInstance.getMaNhanVien());
        labelName.setText("Họ và tên: " + nhanVienInstance.getHoVaTen());
        labelChucVu.setText("Chức Vụ: " + (nhanVienInstance.getChucVu() ? "Quản lý" : "Nhân Viên"));

        labelNgaySinh.setText("Ngày Sinh: " + nhanVienInstance.getNgaySinh());
        labelImage = new JLabel();
        Icon icon = new ImageIcon(nhanVienInstance.getHinhAnh());
        labelImage.setIcon(icon);
        labelGioiTinh.setText("Giới tính: " + (nhanVienInstance.getGioiTinh() ? "Nam" : "Nữ"));
        labelMatKhau.setText("Mật khẩu: " + nhanVienInstance.getMatKhau());

        labelImage = new JLabel();
        icon = new ImageIcon(nhanVienInstance.getHinhAnh());
        labelImage.setIcon(icon);

        updateParent();

    }

    void setMatKhau(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setSoDienThoai(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setGmail(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setCanCuocCongDan(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
