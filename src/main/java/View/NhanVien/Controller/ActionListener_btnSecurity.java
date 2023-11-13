/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.NhanVien.Controller;

import View.NhanVien.View.NhanVienCard;
import View.NhanVien.View.nhanVien_Form_Security;
import View.NhanVien.View.nhanVien_Form_changePassword;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

/**
 *
 * @author hnhut
 */
public class ActionListener_btnSecurity  implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
          nhanVien_Form_Security cd = new nhanVien_Form_Security();
                JDialog j = new JDialog(cd, "Security", true);
                j.setContentPane(cd.getContentPane());
                j.pack();
                j.setLocationRelativeTo(null);
                j.setVisible(true);
    }
    
}
