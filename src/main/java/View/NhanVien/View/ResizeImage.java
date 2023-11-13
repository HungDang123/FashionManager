/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.NhanVien.View;

/**
 *
 * @author hnhut
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResizeImage {

    public static void main(String[] args) {
        // Đường dẫn đến file ảnh ban đầu
        String inputImagePath = "C:\\Users\\hnhut\\Downloads\\pexels-ahmed-ツ-14681409.jpg";
        
        // Đường dẫn đến file ảnh sau khi chỉnh sửa kích thước
        String outputImagePath = "C:\\Users\\hnhut\\Downloads\\pexels-ahmed-ツ-14681409.jpg";
        
        // Kích thước mới của ảnh
        int newWidth = 140;
        int newHeight = 120;

        try {
            // Đọc file ảnh gốc vào BufferedImage
            BufferedImage originalImage = ImageIO.read(new File(inputImagePath));
            
            // Tạo một BufferedImage mới với kích thước mong muốn
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            
            // Scale và vẽ ảnh gốc lên ảnh mới
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            // Ghi ảnh mới ra file
            ImageIO.write(resizedImage, "jpg", new File(outputImagePath));

            System.out.println("Đã chỉnh sửa kích thước ảnh thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}

