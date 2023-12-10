/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package View.BanHang;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 *
 * @author HUNG
 */
public class QRCodeGenerator {

    public static void main(String[] args) {
        String url = "https://github.com/HungDang123/PRO1041";
        String filePath = "src/image/QR.jpg"; // Đường dẫn tập tin ảnh QR

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

//    public Image qr() throws IOException, WriterException {
//        String url = "00020101021138540010A00000072701240006970422011003393530730208QRIBFTTA5303704" + String.valueOf(2000000) + "5802VN63044451"; // Địa chỉ URL của trang web
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 450, 450);
//        MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
//
//        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);
//// Getting logo image
////            BufferedImage logoImage = ImageIO.read(new File(pathToStore));
////            int finalImageHeight = qrImage.getHeight() - 64;
////            int finalImageWidth = qrImage.getWidth() - 64;
////Merging both images
//        BufferedImage finalImage = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
//        Graphics2D graphics = (Graphics2D) finalImage.getGraphics();
//        graphics.drawImage(qrImage, 0, 0, null);
//        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
////            graphics.drawImage(logoImage.getScaledInstance(64, 64, 0), (int) Math.round(finalImageWidth / 2), (int) Math.round(finalImageHeight / 2), null);
////            ImageIO.write(finalImage, "png", new File(createQRPayment.class.getResource("/mticket/image/").getPath().replace("build/classes", "src") + "QRcode.png"));
//        ImageIO.write(finalImage, "png", new File("D:/PRO1041/QR.png"));
//        return finalImage;
//    }
}
