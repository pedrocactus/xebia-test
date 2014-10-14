package com.xebiatest.boxotop.app.ui.utils;

import android.content.Context;

import java.util.List;

/**
 * Created by pierrecastex on 30/09/2014.
 */
public class Utils {
    public static int SCORE_BASE=100;
    public static int RATE_BASE=5;

    public static float gradeToRate(float grade, int scale){
        return grade/scale*5;
    }

    public static String textToQuote(String text){
        String quote = "\""+text+"\"";
        return quote;
    }
    public static String quoteToText(String quote){
        String text = quote.replace("\"", "");
        return text;
    }


}
