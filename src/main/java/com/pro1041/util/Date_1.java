/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pro1041.util;

import java.time.DayOfWeek;

/**
 *
 * @author HUNG
 */
public class Date_1 {
    private String day ,month,year;

    public Date_1(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date_1() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    
}
