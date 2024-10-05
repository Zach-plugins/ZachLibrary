package com.zachfr.zachlibrary.utils;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.PermissionDefault;

import java.util.Set;

public class PermissionUtils {
    public static int getNumberFromPermission(Player player, String permission, boolean bypassPermission, int def){
        final Set<PermissionAttachmentInfo> permissions = player.getEffectivePermissions();

        if (bypassPermission && player.hasPermission(permission + ".bypass")) return Integer.MAX_VALUE;

        boolean set = false;
        int highest = 0;

        for (PermissionAttachmentInfo info : permissions) {

            final String perm = info.getPermission();

            if (!perm.startsWith(permission)) continue;

            final int index = perm.lastIndexOf('.');

            if (index == -1 || index == perm.length()) continue;

            final int number = NumberUtils.getPositiveNumber(perm, index, perm.length());

            if (number >= highest) {
                highest = number;
                set = true;
            }
        }

        return set ? highest : def;
    }

    public static double getNumberFromPermissionDouble(Player player, String permission, boolean bypassPermission, double def) {
        final Set<PermissionAttachmentInfo> permissions = player.getEffectivePermissions();

        if (bypassPermission && player.hasPermission(permission + ".bypass")) return Double.MAX_VALUE;

        boolean set = false;
        double highest = 0;

        for (PermissionAttachmentInfo info : permissions) {

            final String perm = info.getPermission();

            if (!perm.startsWith(permission)) continue;

            final double number = perm.replace(permission + ".", "").matches("^[0-9]+(\\.[0-9]+)?$") ? Double.parseDouble(perm.replace(permission + ".", "")) : 1;

            if (number >= highest) {
                highest = number;
                set = true;
            }
        }

        return set ? highest : def;
    }

    public static Boolean hasPermission(Player player, String permission) {
        Permission p = new Permission(permission, PermissionDefault.FALSE);
        return player.hasPermission(p);
    }

    public static Boolean hasPerm(Player player, String permission) {
        boolean negative = permission.startsWith("-");
        permission = negative ? permission.substring(1) : permission;

        Permission p = new Permission(permission, PermissionDefault.FALSE);
        return player.hasPermission(p) == !negative;
    }
}
