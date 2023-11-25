/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nhanVien.data;

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

    public static boolean kiemTraHoTen(String hoTen) {
        // Biểu thức chính quy cho phép chữ cái, dấu cách, và một số ký tự tiếng Việt
        String regex = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨƠƯàáâãèéêìíòóôõùúăđĩơư\\s-ấầẩẫậắằẳẵặéèẹẻẽêếềểễệóòọỏõôốồổỗộúùụủũưứừửữựíìịỉĩđ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(hoTen);

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

    public static boolean validateInput(String inputStr, String inputType) {
        System.out.println("if: " + inputStr);
        if (inputStr.isEmpty() || inputStr == null || inputStr.equalsIgnoreCase("Chưa có thông tin")) {
            return true;
        }

        if (inputType.equalsIgnoreCase("Password")) {
            // Kiểm tra mật khẩu: ít nhất 8 ký tự, ít nhất một chữ cái và một chữ số
            Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
            Matcher matcher = pattern.matcher(inputStr);
            return matcher.matches();
        } else if (inputType.equalsIgnoreCase("Số điện thoại")) {
            // Kiểm tra số điện thoại: 10-12 chữ số, bắt đầu bằng 0 hoặc +84
            Pattern pattern = Pattern.compile("^(0|\\+84)[0-9]{9,11}$");
            Matcher matcher = pattern.matcher(inputStr);
            return matcher.matches();
        } else if (inputType.equalsIgnoreCase("Email")) {
            // Kiểm tra email: phải có ký tự @ và có ít nhất một dấu chấm sau @
            Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");
            Matcher matcher = pattern.matcher(inputStr);
            return matcher.matches();
        } else if (inputType.equalsIgnoreCase("Căn cước công dân")) {
            // Kiểm tra số CMND: 9 hoặc 12 chữ số
            Pattern pattern = Pattern.compile("^\\d{9}|\\d{12}$");
            Matcher matcher = pattern.matcher(inputStr);
            return matcher.matches();
        }
        return false; // Trường hợp không khớp với bất kỳ loại nào
    }

    public static String checkPasswordStrength(String password) {
        int length = password.length();

        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        // Kiểm tra mức độ độ phức tạp của mật khẩu dựa trên các điều kiện
        if (length >= 12 && (hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar)) {
            return "Mạnh"; // Mật khẩu mạnh nếu đạt tất cả các yêu cầu
        } else if (length >= 12 && (hasUpperCase || hasLowerCase || hasDigit || hasSpecialChar)) {
            return "Khá"; // Mật khẩu khá nếu đạt nhiều yêu cầu hơn
        } else if (length > 8 && (hasLetter || hasDigit)) {
            return "Trung bình"; // Mật khẩu trung bình nếu đạt một số yêu cầu
        } else {
            return "Yếu"; // Mật khẩu yếu nếu không đạt các yêu cầu cơ bản
        }
    }

}
