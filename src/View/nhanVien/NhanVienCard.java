package View.nhanVien;

import Model.nhanVien;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class NhanVienCard extends JPanel {

    private nhanVien nhanVienInstance;

    public NhanVienCard(nhanVien nv) {
        this.nhanVienInstance = nv;
        this.setLayout(new BorderLayout());
        this.setBorder(new BevelBorder(BevelBorder.RAISED));

        // Header (Mã nhân viên, Chức Vụ)
        JPanel pnHeader = new JPanel(new GridBagLayout());
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.anchor = GridBagConstraints.WEST;
        headerConstraints.insets = new Insets(5, 10, 5, 10);

        JLabel labelMaNV = new JLabel("Mã nhân viên: " + nv.getMaNhanVien());
        JLabel labelChucVu = new JLabel("Chức vụ: " + (nv.getChucVu()? "Quản Lý" : "Nhân Viên"));

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

        JLabel labelImage = new JLabel();
        Icon icon = new ImageIcon(nv.getHinhAnh());
        labelImage.setIcon(icon);

        JLabel labelName = new JLabel("Họ và tên: " + nv.getHoVaTen());
        
        java.sql.Date sqlDate = new java.sql.Date(nv.getNgaySinh().getTime());
        JLabel labelNgaySinh = new JLabel("Ngày Sinh: " + sqlDate);
        JLabel labelGioiTinh = new JLabel("Giới Tính: " + (nv.getGioiTinh()? "Nam" : "Nữ"));
        JLabel labelCCCD = new JLabel("CCCD: " + nv.getCanCuocCongDan());

        midderConstraints.gridx = 0;
        midderConstraints.gridy = 0;
        pnMidder.add(labelImage, midderConstraints);

        midderConstraints.gridy = 1;
        pnMidder.add(labelName, midderConstraints);

        midderConstraints.gridy = 2;
        pnMidder.add(labelNgaySinh, midderConstraints);

        midderConstraints.gridy = 3;
        pnMidder.add(labelGioiTinh, midderConstraints);

        midderConstraints.gridy = 4;
        pnMidder.add(labelCCCD, midderConstraints);

        this.add(pnMidder, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 280));

        // Footer
        JPanel pnFooter = new JPanel();
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JButton btnChangePw = new JButton("Change Password");

        pnFooter.add(btnDelete);
        pnFooter.add(btnEdit);
        pnFooter.add(btnChangePw);

        this.add(pnFooter, BorderLayout.SOUTH);
    }
    
    
}
