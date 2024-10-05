package com.zachfr.zachlibrary.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberUtils {
    public static String formatEconomy(char currencySymbol, double number) {
        return currencySymbol + formatNumber(number);
    }

    public static String formatNumber(double number) {
        DecimalFormat decimalFormatter = new DecimalFormat(number == Math.ceil(number) ? "#,###" : "#,###.00");

        DecimalFormatSymbols symbols = decimalFormatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(',');

        decimalFormatter.setDecimalFormatSymbols(symbols);
        return decimalFormatter.format(number);
    }

    public static String formatWithSuffix(long count) {
        if (count < 1000) return String.valueOf(count);
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c", count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp-1)).replace(".0", "");
    }

    public static boolean isInt(String number) {
        if (number == null || number.equals(""))
            return false;
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String s) {
        if (s == null || s.equals(""))
            return false;
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static int getPositiveNumber(String input, int start, int end) {

        if (input.length() < start || input.length() > end) throw new IndexOutOfBoundsException("");

        int num = 0;

        for (int i = start; i < end; i++) {

            final char ch = input.charAt(i);

            if (!Character.isDigit(ch)) continue;

            final int digit = Character.getNumericValue(ch);

            num = num * 10 + digit;
        }

        return num;

    }

    public static boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean tryParseFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
