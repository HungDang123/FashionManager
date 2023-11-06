/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.nhanVien;

/**
 *
 * @author hnhut
 */
import View.nhanVien.NhanVienCard;
import Model.nhanVien;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class ProductListApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Product List");
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(3, 0, 0, 0)); // Đặt layout cho panel sản phẩm

        ArrayList<nhanVien> nv = new ArrayList<>();
//        nhanVien nv1 = new nhanVien("NV001", "Nguyễn Văn A", true, new Date(), true, "123456789", "0123456789", "C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041\\src\\image\\a16.jpg");
//        nhanVien nv2 = new nhanVien("NV002", "Nguyễn Thị B", false, new Date(), false, "987654321", "0987654321", "C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041\\src\\image\\a12.jpg");
//        nhanVien nv3 = new nhanVien("NV003", "Trần Văn C", true, new Date(), true, "111223344", "0369852147", "C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041\\src\\image\\a13.jpg");
//        nhanVien nv4 = new nhanVien("NV004", "Lê Thị D", false, new Date(), false, "998877665", "0909090909", "C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041\\src\\image\\a11.jpg");
//        nhanVien nv5 = new nhanVien("NV005", "Phạm Văn E", true, new Date(), true, "5566778899", "0123456789", "C:\\Users\\hnhut\\OneDrive\\Máy tính\\Pro1041_Nhom2\\PRO1041\\src\\image\\a5.jpg");

//        nv.add(nv1);
//        nv.add(nv2);
//        nv.add(nv3);
//        nv.add(nv4);
//        nv.add(nv5);

        for (nhanVien nvs : nv) {
            NhanVienCard productCard = new NhanVienCard(nvs);
            productPanel.add(productCard);
        }

        JScrollPane scrollPane = new JScrollPane(productPanel);
        frame.add(scrollPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
