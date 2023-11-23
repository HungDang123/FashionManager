/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package View.BanHang;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *
 * @author HUNG
 */

public class QRCodeGenerator {

    public static void main(String[] args) {
        String url = "http://hungdang1041.byethost7.com/?i=1"; // Địa chỉ URL của trang web
        String filePath = "D:/PRO1041/QR.png"; // Đường dẫn tập tin ảnh QR

        try {
            generateQR(url, 1250, 1250, filePath);
            System.out.println("QR code generated successfully.");
        } catch (WriterException e) {
            System.out.println("QR code generation failed. Error: " + e.getMessage());
        }
    }

    public static void generateQR(String url, int width, int height, String filePath) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);

        try {
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
