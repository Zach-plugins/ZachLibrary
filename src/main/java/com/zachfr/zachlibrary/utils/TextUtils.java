package com.zachfr.zachlibrary.utils;

public class TextUtils {
    private final static int CENTER_PX = 154;

    public static String[] centerMessage(String[] message, String string){
        String line = String.join(" ", message);
        line = centerMessage(line);
        return line.split("\\" + string);
    }

    public static String centerMessage(String message){
        return centerMessage(message, 0, true, false);
    }

    public static String centerMessage(String message, int length, Boolean color, Boolean onlyCompensate){
        if(message == null || message.equals("") || !(message.contains("<center>") && message.contains("</center"))){
            if(onlyCompensate)
                return "";
            else
                return color ? ColorUtils.color(message) : message;
        }
        
        int toCompensate = getCenterPx(message, length, color, onlyCompensate);
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        if(onlyCompensate)
            return sb.toString();
        else
            return  sb + message;
    }
    
    public static int getCenterPx(String message, int length, Boolean color, Boolean onlyCompensate){
        message = (color ? ColorUtils.color(message.replaceAll("<center>|</center>", "")) : message.replaceAll("<center>|</center>", ""));
        message = (color ? ColorUtils.color(message.replaceAll("&#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|\\{#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})}.*?", "")) : message.replaceAll("&#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|\\{#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})}.*?", ""));

        int messagePxSize = length;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§' || c == '&'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        return toCompensate;
    }
}
