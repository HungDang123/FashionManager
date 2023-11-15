/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nhanVien.Controller;

import nhanVien.Data.DAO_Security;
import nhanVien.Data.checkNhanVien;
import nhanVien.View.NhanVienCard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author hnhut
 */
public class Security_changeData implements ActionListener {

    private JTextField textData;
    private String dataName;
    private String maNhanVien;
    private NhanVienCard nvCard;

    public Security_changeData(JTextField textField, String dataName, String maNhanVien,NhanVienCard nvCard) {
        this.textData = textField;
        this.dataName = dataName;
        this.maNhanVien = maNhanVien;
        this.nvCard = nvCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String data = JOptionPane.showInputDialog("Nhập " + dataName);
        if (data != null && !data.isEmpty()) {
            if (checkNhanVien.validateInput(data, dataName)) {
                textData.setText(data);
                switch (dataName) {
                    case "Password":
                        DAO_Security.changePw(data, maNhanVien);
                        nvCard.updateData(maNhanVien);
                        break;
                    case "Số điện thoại":
                        DAO_Security.changeSdt(data, maNhanVien);
                        nvCard.updateData(maNhanVien);
                        break;
                    case "Căn cước công dân":
                        DAO_Security.changeCccd(maNhanVien, maNhanVien);
                        nvCard.updateData(maNhanVien);
                        break;
                    case "Email":
                        DAO_Security.changeEmail(data, maNhanVien);
                        nvCard.updateData(maNhanVien);
                        break;
                    default:
                        throw new AssertionError();
                }
            } else {
                switch (dataName) {
                    case "Password":
                        JOptionPane.showMessageDialog(null, "// Kiểm tra mật khẩu: ít nhất 8 ký tự, ít nhất một chữ cái và một chữ số");
                        break;
                    case "Số điện thoại":
                        JOptionPane.showMessageDialog(null, "// // Kiểm tra số điện thoại: 10-12 chữ số, bắt đầu bằng 0 hoặc +84");
                        break;
                    case "Căn cước công dân":
                        JOptionPane.showMessageDialog(null, "// Kiểm tra số CMND: 9 hoặc 12 chữ số");
                        break;
                    case "Email":
                        JOptionPane.showMessageDialog(null, "// Kiểm tra email: phải có ký tự @ và có ít nhất một dấu chấm sau @");
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }
    }
}
