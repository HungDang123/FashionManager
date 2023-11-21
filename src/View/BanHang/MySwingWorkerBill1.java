/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.BanHang;

import Model.chiTietHoaDon;
import Model.hoaDon;
import java.util.List;
import javax.swing.*;

public class MySwingWorkerBill1 extends SwingWorker<Void, Void> {

    private JInternalFrame internalFrame;
    private static chiTietHoaDon chiTiet;
    private static List<Object[]> listHD;

    public MySwingWorkerBill1(JInternalFrame internalFrame,chiTietHoaDon chiTiet, List<Object[]> listHD) {
        this.internalFrame = internalFrame;
        this.chiTiet = chiTiet;
        this.listHD = listHD;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Đây là nơi để xử lý công việc trong background thread
        // Ví dụ: Hiển thị JDialog
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Hiển thị JDialog ở đây
                Bill1 dialog = new Bill1(new javax.swing.JFrame(), true, chiTiet,listHD);
                dialog.setVisible(true);
            }
        });
        return null;
    }

    @Override
    protected void done() {
        // Xử lý khi công việc trong background thread hoàn thành
        // Đảm bảo rằng JInternalFrame không bị mất
        if (internalFrame != null) {
            internalFrame.toFront();
            System.out.println("");
        }
    }
}
