/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.util;

/**
 *
 * @author hnhut
 */
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageResizer {
             private  static String inputURL = "C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041\\src\\image\\";
             private  static String outputURL = "C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041\\src\\image\\";

    public static boolean Resize(String inputURL, String outputURL) {
        int newWidth = 150; // Replace this with the desired width of the resized image
        int newHeight = 130; // Replace this with the desired height of the resized image
        try {
            // Read the input image
            BufferedImage originalImage = ImageIO.read(new File(inputURL));

            // Create a new BufferedImage with the desired width and height
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            // Scale the original image to the new size
            Image tmp = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            resizedImage.getGraphics().drawImage(tmp, 0, 0, null);

            // Write the resized image to the output file
            ImageIO.write(resizedImage, "jpg", new File(outputURL));

            System.out.println("Image resized successfully!");
            return true;
        } catch (IOException e) {
            System.out.println("Input:");
            System.out.println("Error occurred while resizing the image: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean ResizeAll(int number) {
        for (int i = 0; i < number; i++) {
            inputURL = "C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041\\src\\image\\";
               inputURL += "a"+i+".jpg"; 
               outputURL = inputURL;
            if (!Resize(inputURL, outputURL)) {
                return false;
            }
            System.out.println("Resize: "+inputURL);
        }
        return true;
    }
    
    public static void main(String[] args) {
        ResizeAll(20);
    }
}

