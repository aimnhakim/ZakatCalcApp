package com.example.zakatcalcapp;

public class Utils {
    public static double safeParse(String s) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
}