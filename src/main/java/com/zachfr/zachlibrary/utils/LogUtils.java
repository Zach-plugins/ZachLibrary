package com.zachfr.zachlibrary.utils;

import com.zachfr.zachlibrary.ZachLibrary;
import org.bukkit.Bukkit;

public class LogUtils {

    /**
     * Sends a message to the console
     * @param message The message to send
     */
    public static void send(String message) {
        Bukkit.getConsoleSender().sendMessage(ColorUtils.color("["+ ZachLibrary.getInstance().getName() + "] " + message));
    }

    /**
     * Sends a debug message to the console
     * @param message The message to send
     */
    public static void debug(String message) {
        Bukkit.getConsoleSender().sendMessage(ColorUtils.color("["+ ZachLibrary.getInstance().getName() + "] [DEBUG] " + message));
    }

    /**
     * Sends an error message to the console
     * @param message The message to send
     */
    public static void error(String message) {
        Bukkit.getConsoleSender().sendMessage(ColorUtils.color("["+ ZachLibrary.getInstance().getName() + "] &c" + message));
    }
}
