/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.util;

import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author HUNG
 */
public class date {
    public static void main(String[] args) throws ParseException {
        // Chuyển đổi Date sang String
        Date currentDate = DateHelper.now();
        String dateString = DateHelper.toString(currentDate, "yyyy-MM-dd");
        System.out.println("Current Date String: " + dateString);

        // Chuyển String sang Date
        String inputDate = "2023-08-15";
        try {
            Date parsedDate = DateHelper.toDate(inputDate, "yyyy-MM-dd");
            System.out.println("Parsed Date: " + parsedDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }

        // Lấy thời gian hiện tại
        Date now = DateHelper.now();
        System.out.println("Current Date: " + now);

        // Thêm số ngày vào thời gian hiện tại
        int daysToAdd = 5;
        Date futureDate = DateHelper.add(daysToAdd);
        System.out.println("Future Date: " + futureDate);

        // Thêm số ngày vào một ngày cụ thể
        Date specificDate = DateHelper.toDate("2023-08-15", "yyyy-MM-dd");
        int daysToAddToSpecificDate = 10;
        Date newDate = DateHelper.addDays(specificDate, daysToAddToSpecificDate);
        System.out.println("New Date: " + newDate);
    }
}