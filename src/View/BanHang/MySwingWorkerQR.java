/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.BanHang;

import javax.swing.*;

public class MySwingWorkerQR extends SwingWorker<Void, Void> {

    private JDialog dialog;
    private static String path;

    public MySwingWorkerQR(JDialog dialog,String path) {
        this.dialog = dialog;
        this.path = path;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Đây là nơi để xử lý công việc trong background thread
        // Ví dụ: Hiển thị JDialog
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Hiển thị JDialog ở đây
                QR dialog = new QR(new javax.swing.JFrame(), true,path);
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
