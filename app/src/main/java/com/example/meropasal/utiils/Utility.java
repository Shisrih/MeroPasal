package com.example.meropasal.utiils;

import java.text.NumberFormat;
import java.util.Locale;

public class Utility {

    public static String getFormatedNumber(String number){
        if(!number.isEmpty()) {
            double val = Double.parseDouble(number);
            return NumberFormat.getNumberInstance(Locale.US).format(val);
        }else{
            return "0";
        }
    }
}
