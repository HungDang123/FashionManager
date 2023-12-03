/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.BanHang;

import Model.hoaDon;
import com.pro1041.util.ShareHelper;
import java.util.List;
import javax.swing.*;

public class MySwingWorkerThanhToan extends SwingWorker<Void, Void> {

    private static hoaDon hoaDon;
    private static List<Object[]> list;
    private JInternalFrame internalFrame;

    public MySwingWorkerThanhToan(JInternalFrame internalFrame,  hoaDon hoaDon,List<Object[]> list) {
        this.internalFrame = internalFrame;
        this.hoaDon = hoaDon;
        this.list = list;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Đây là nơi để xử lý công việc trong background thread
        // Ví dụ: Hiển thị JDialog
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Hiển thị JDialog ở đây
                formThanhToan dialog = new formThanhToan(new javax.swing.JFrame(), true,hoaDon,list);
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
