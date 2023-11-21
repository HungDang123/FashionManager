/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nhanVien.Controller;

import nhanVien.data.DAO_Security;
import nhanVien.View.NhanVienCard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author hnhut
 */
public class Security_removeData implements ActionListener{
    
    private JTextField textData;
    private String dataName;
    private String maNhanVien;
    private NhanVienCard nvCard;

    public Security_removeData(JTextField textData, String dataName, String maNhanVien, NhanVienCard nvCard) {
        this.textData = textData;
        this.dataName = dataName;
        this.maNhanVien = maNhanVien;
        this.nvCard = nvCard;
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int op = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa bỏ "+dataName,"Remove",JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            textData.setText("Chưa có thông tin");
                switch (dataName) {
                    case "Password":
                        DAO_Security.removeMatKhau(maNhanVien);
                        nvCard.updateData(maNhanVien);
                        break;
                    case "Số điện thoại":
                        DAO_Security.removeSoDienThoai(maNhanVien);
                        nvCard.updateData(maNhanVien);
                        break;
                    case "Căn cước công dân":
                        DAO_Security.removeCanCuocCongDan(maNhanVien);
                        nvCard.updateData(maNhanVien);
                        break;
                    case "Email":
                        DAO_Security.removeEmail(maNhanVien);
                        nvCard.updateData(maNhanVien);
                        break;
                    default:
                        throw new AssertionError();
                }
        }
    }
    
}
