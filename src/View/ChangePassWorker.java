/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.hoaDonContainer;
import View.ChangePass;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ChangePassWorker extends SwingWorker<Void, Void> {

    private JInternalFrame internalFrame;
    public ChangePassWorker(JInternalFrame internalFrame) {
        this.internalFrame = internalFrame;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Đây là nơi để xử lý công việc trong background thread
        // Ví dụ: Hiển thị JDialog
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Hiển thị JDialog ở đây
                ChangePass dialog = new ChangePass(new javax.swing.JFrame(),true);
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
