/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.BanHang;

import javax.swing.*;

public class MySwingWorker extends SwingWorker<Void, Void> {

    private JInternalFrame internalFrame;

    public MySwingWorker(JInternalFrame internalFrame) {
        this.internalFrame = internalFrame;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Đây là nơi để xử lý công việc trong background thread
        // Ví dụ: Hiển thị JDialog
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Hiển thị JDialog ở đây
                themKhachHangForm dialog = new themKhachHangForm(new javax.swing.JFrame(), true);
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
