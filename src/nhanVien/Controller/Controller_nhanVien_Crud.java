/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nhanVien.Controller;

import nhanVien.Data.nhanVien_data_DAO;
import nhanVien.View.NhanVienCard;
import nhanVien.View.nhanVien_Form_Edit;
import nhanVien.View.nhanVien_Form_Security;
import nhanVien.View.nhanVien_Form_changePassword;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author hnhut
 */
public class Controller_nhanVien_Crud {
    
    
    // Controller Delete
    public static class Controller_nvDelete implements ActionListener{

    private NhanVienCard nvCard;
    public  Controller_nvDelete(NhanVienCard nvCard) {
        this.nvCard = nvCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          int op = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa Nhân Viên: "+nvCard.getNhanVienInstance().getMaNhanVien(), 
                  "Delete", JOptionPane.YES_NO_OPTION);
          if (op == JOptionPane.YES_OPTION) {
              new nhanVien_data_DAO().delete(nvCard.getNhanVienInstance().getMaNhanVien());
              JOptionPane.showMessageDialog(null, "Xóa thành công", "OK", JOptionPane.INFORMATION_MESSAGE);
              nvCard.updateParent();
          }
    }
    }
    
    public static Controller_nvDelete getController_nvDelete(NhanVienCard nvCard) {
        return new Controller_nvDelete(nvCard);
    }
    
    
    
        // Controller Edit
    public static class Controller_nvEdit implements ActionListener{

    private NhanVienCard nvCard;
    public Controller_nvEdit(NhanVienCard nvCard) {
        this.nvCard = nvCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          nhanVien_Form_Edit edit = new nhanVien_Form_Edit(nvCard.getNhanVienInstance(), nvCard);
                edit.setVisible(true);
                edit.setLocationRelativeTo(null);
    }
 
}
    
     public static Controller_nvEdit getController_nvEdit(NhanVienCard nvCard) {
        return new Controller_nvEdit(nvCard);
    }
     
     
    
     // Controller change Password
  public static class Controller_changePw implements ActionListener{

    private NhanVienCard nvCard;
    public Controller_changePw(NhanVienCard nvCard) {
        this.nvCard = nvCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          nhanVien_Form_changePassword cd = new nhanVien_Form_changePassword(nvCard.getNhanVienInstance().getMaNhanVien(), nvCard);
                JDialog j = new JDialog(cd, "Change Password", true);
                j.setContentPane(cd.getContentPane());
                j.pack();
                j.setLocationRelativeTo(null);
                j.setVisible(true);
    }   
}  
   public static Controller_changePw getController_nvChangePw(NhanVienCard nvCard) {
        return new Controller_changePw(nvCard);
    }
   
   
   // Controller nvUpdateSecurity
   public static class Controller_nvUpdateSecurity  implements ActionListener{

    private NhanVienCard nvCard;

    public Controller_nvUpdateSecurity(NhanVienCard nvCard) {
        this.nvCard = nvCard;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
          nhanVien_Form_Security cd = new nhanVien_Form_Security(nvCard);
                JDialog j = new JDialog(cd, "Security", true);
                j.setContentPane(cd.getContentPane());
                j.pack();
                j.setLocationRelativeTo(null);
                j.setVisible(true);
    }
   }
    
    public static Controller_nvUpdateSecurity getController_nvUpdateSecurity(NhanVienCard nvCard) {
        return new Controller_nvUpdateSecurity(nvCard);
    }
    
    }
    

 


