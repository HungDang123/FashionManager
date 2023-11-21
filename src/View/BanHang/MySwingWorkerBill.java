/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.BanHang;

import Model.hoaDon;
import java.util.List;
import javax.swing.*;

public class MySwingWorkerBill extends SwingWorker<Void, Void> {

    private JDialog dialog;
    public static float tongTien;
    public static hoaDon hoaDon;
    public static List<Object[]> list;

    public MySwingWorkerBill(JDialog dialog, float tongTien, hoaDon hoaDon, List<Object[]> list) {
        this.dialog = dialog;
        this.hoaDon = hoaDon;
        this.tongTien = tongTien;
        this.list = list;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Đây là nơi để xử lý công việc trong background thread
        // Ví dụ: Hiển thị JDialog
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Hiển thị JDialog ở đây
                Bill dialog = new Bill(new javax.swing.JFrame(), true, tongTien, hoaDon, list);
                dialog.setVisible(true);
            }
        });
        return null;
    }

    @Override
    protected void done() {
        // Xử lý khi công việc trong background thread hoàn thành
        // Đảm bảo rằng JInternalFrame không bị mất
        if (dialog != null) {
            dialog.toFront();
            System.out.println("");
        }
    }
}
