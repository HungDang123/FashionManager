/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.util;

/**
 *
 * @author HUNG
 */
import Model.nhanVien;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ShareHelper {

    public static nhanVien USER; // Thông tin nhân viên đăng nhập
    public static final Image APP_ICON; // Biểu tượng của ứng dụng

    static {
        // Khởi tạo biểu tượng của ứng dụng
        Image icon = null;
        try {
            File iconFile = new File("C:\\Users\\HUNG\\OneDrive\\Hình ảnh\\Saved Pictures\\339068686_226572036593332_828360317069871275_n.png");
            if (iconFile.exists()) {
                icon = ImageIO.read(iconFile);
            } else {
                System.out.println("File không tồn tại: " + iconFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        APP_ICON = icon;
    }

    public static void logoff() {
        USER = null;
    }

    public static boolean authenticated() {
        return USER != null;
    }

    public static void saveLogo(File file) {
        try {
            ImageIO.write(ImageIO.read(file), "png", new File("\"C:\\Users\\HUNG\\OneDrive\\Hình ảnh\\Saved Pictures\\339068686_226572036593332_828360317069871275_n.png"));
            System.out.println("Logo saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon readLogo(String filePath) {
        ImageIcon logoIcon = null;
        try {
            Image logoImage = ImageIO.read(new File(filePath));
            logoIcon = new ImageIcon(logoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logoIcon;
    }
}
