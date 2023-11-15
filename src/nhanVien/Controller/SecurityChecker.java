package nhanVien.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityChecker {

    // Hàm kiểm tra mức độ bảo mật và trả về một số từ 0-100
    public static int checkSecurity(String matKhau, String soDienThoai, String email, String canCuocCongDan) {
        int securityScore = 100; // Đặt mức độ bảo mật ban đầu là 100%

        // Kiểm tra mỗi trường và trừ đi 25% nếu giá trị là "Chưa có thông tin"
        securityScore -= checkField("Mật khẩu", matKhau);
        securityScore -= checkField("Số điện thoại", soDienThoai);
        securityScore -= checkField("Email", email);
        securityScore -= checkField("Căn cước công dân", canCuocCongDan);

        return securityScore;
    }

    // Hàm kiểm tra một trường cụ thể và trả về giảm bao nhiêu phần trăm
    private static int checkField(String fieldName, String fieldValue) {
        if ("Chưa có thông tin".equals(fieldValue)) {
            System.out.println(fieldName + " không có thông tin.");
            return 25; // Trừ đi 25% nếu giá trị là "Chưa có thông tin"
        }

        int totalScore = 0;

        return Math.min(totalScore, 25);
    }

  
    // Hàm kiểm tra độ mạnh của mật khẩu
    private static int checkPasswordStrength(String matKhau) {
        // Độ dài ít nhất là 8 ký tự
        int lengthScore = Math.min(matKhau.length(), 8) * 10;

        // Kiểm tra sự phức tạp của mật khẩu (ví dụ: có chữ in hoa, chữ thường, số, ký tự đặc biệt)
        int complexityScore = 0;
        if (matKhau.matches(".*[A-Z].*")) {
            complexityScore += 20;
        }
        if (matKhau.matches(".*[a-z].*")) {
            complexityScore += 20;
        }
        if (matKhau.matches(".*\\d.*")) {
            complexityScore += 20;
        }
        if (matKhau.matches(".*[!@#$%^&*()-_=+\\[{\\]};:'\",<.>/?].*")) {
            complexityScore += 20;
        }

        return lengthScore + complexityScore;
    }

    // Hàm kiểm tra độ mạnh của số điện thoại
    private static int checkPhoneNumberStrength(String soDienThoai) {
        // Độ dài ít nhất là 10 ký tự
        int lengthScore = Math.min(soDienThoai.length(), 10) * 10;

        // Kiểm tra xem có chứa số hay không, cộng tối đa 25 điểm
        return Math.min(lengthScore + (soDienThoai.matches(".*\\d.*") ? 25 : 0), 25);
    }

    // Hàm kiểm tra độ mạnh của email
    private static int checkEmailStrength(String email) {
        // Sử dụng regex để kiểm tra định dạng email cơ bản
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+@[a-zA-Z]+\\.[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(email);

        // Kiểm tra độ dài và định dạng của email, cộng tối đa 25 điểm
        return Math.min(Math.min(email.length() * 2, 25) + (matcher.matches() ? 25 : 0), 25);
    }

    // Hàm kiểm tra độ mạnh của căn cước công dân
    private static int checkCccdStrength(String canCuocCongDan) {
        int totalScore = 0;

        // Độ dài ít nhất là 9 ký tự, cộng tối đa 25 điểm
        int lengthScore = Math.min(canCuocCongDan.length(), 9) * 10;
        totalScore += Math.min(lengthScore, 25);

        // Kiểm tra xem có chứa số hay không, cộng tối đa 25 điểm
        totalScore += (canCuocCongDan.matches(".*\\d.*") ? 25 : 0);

        return totalScore;
    }

    public static void main(String[] args) {
        // Ví dụ sử dụng hàm kiểm tra bảo mật
        String matKhau = "Chưa có thông tin";
        String soDienThoai = "1234567890";
        String email = "example@email.com";
        String canCuocCongDan = "ABC123456";

        int securityScore = checkSecurity(matKhau, soDienThoai, email, canCuocCongDan);
        System.out.println("Security Score: " + securityScore);
    }
}
