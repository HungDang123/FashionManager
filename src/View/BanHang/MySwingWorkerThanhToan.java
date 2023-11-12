/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.BanHang;

import Model.khachHang;
import com.pro1041.util.ShareHelper;
import java.util.List;
import javax.swing.*;

public class MySwingWorkerThanhToan extends SwingWorker<Void, Void> {

    private static khachHang kh = ShareHelper.khachHang;
    private static float tongTien;
    private static String hoaDon;
    private static List<Object[]> list;
    private JInternalFrame internalFrame;

    public MySwingWorkerThanhToan(JInternalFrame internalFrame, khachHang kh, Float tongTien, String hoaDon,List<Object[]> list) {
        this.internalFrame = internalFrame;
        this.kh = kh;
        this.hoaDon = hoaDon;
        this.tongTien = tongTien;
        this.list = list;
        System.out.println(" MySwingWorkerThanhToan"+kh.toString());
        System.out.println(" MySwingWorkerThanhToan"+tongTien);
        System.out.println(" MySwingWorkerThanhToan"+hoaDon);
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Đây là nơi để xử lý công việc trong background thread
        // Ví dụ: Hiển thị JDialog
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Hiển thị JDialog ở đây
                formThanhToan dialog = new formThanhToan(new javax.swing.JFrame(), true, kh,tongTien,hoaDon,list);
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
        }
    }
}
