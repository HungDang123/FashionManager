/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.util;

/**
 *
 * @author HUNG
 */
import javax.swing.JOptionPane;

public class DialogHelper {

    public static void alert(String message) {
        JOptionPane.showMessageDialog(null, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String message) {
        int choice = JOptionPane.showConfirmDialog(null, message, "Xác nhận", JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }

    public static String prompt(String message) {
        return JOptionPane.showInputDialog(null, message, "Nhập thông tin", JOptionPane.PLAIN_MESSAGE);
    }
}
