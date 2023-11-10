/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hnhut
 */
public class checkNhanVien {
    public static boolean kiemTraDinhDangCCCD(String cccd) {
        String regex = "^[0-9]{3}[0-9]{1}[0-9]{2}[0-9]{6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cccd);

        return matcher.matches();
    }
    
    public static boolean kiemTraHoTen(String hoten) {
        String regex = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨƠƯàáâãèéêìíòóôõùúăđĩơư\\s-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(hoten);

        return matcher.matches();
    }
    
    public static boolean kiemTraEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    
       public static boolean kiemTraSoDienThoai(String soDienThoai) {
        // Biểu thức chính quy cho định dạng số điện thoại Việt Nam
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(soDienThoai);

        return matcher.matches();
    }
}
