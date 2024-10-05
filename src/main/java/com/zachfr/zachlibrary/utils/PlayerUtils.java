package com.zachfr.zachlibrary.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;
import java.util.Map;

/**
 * A class containing useful methods for players
 */
public class PlayerUtils {

    /**
     * Gives an item to a player
     * @param player The player to give the item to
     * @param itemStack The item to give
     */
    public static void giveItem(Player player, ItemStack itemStack){
        Map<Integer, ItemStack> leftOver = player.getInventory().addItem(itemStack);
        for(ItemStack item : leftOver.values()) {
            if(item.getAmount() > item.getMaxStackSize()){
                for(int i = 0; i < item.getAmount() / item.getMaxStackSize(); i++){
                    ItemStack stack = item.clone();
                    stack.setAmount(item.getMaxStackSize());
                    player.getWorld().dropItem(player.getLocation(), stack);
                }
                item.setAmount(item.getAmount() % item.getMaxStackSize());
                player.getWorld().dropItem(player.getLocation(), item);
            } else
                player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    /**
     * Sets the item in the main hand of a player
     * @param player The player to set the item for
     * @param itemStack The item to set
     */
    @SuppressWarnings("deprecation")
    public static void setInMainHand(Player player, ItemStack itemStack){
        if(ServerVersion.isServerVersionAbove(ServerVersion.V1_8))
            player.getInventory().setItemInMainHand(itemStack);
        else
            player.getInventory().setItemInHand(itemStack);
    }

    /**
     * Check if a player has a permission
     * @param player The player to check
     * @param permission The permission to check
     * @return Whether the player has the permission
     */
    public static Boolean hasPermission(Player player, String permission) {
        boolean negative = permission.startsWith("-");
        permission = negative ? permission.substring(1) : permission;

        Permission p = new Permission(permission, PermissionDefault.FALSE);
        return player.hasPermission(p) == !negative;
    }

    /**
     * Check if a player has any of a list of permissions
     * @param player The player to check
     * @param permissions The permissions to check
     * @return Whether the player has all the permissions
     */
    public static Boolean hasPermission(Player player, List<String> permissions) {
        for(String permission : permissions)
            if(hasPermission(player, permission))
                return true;
        return false;
    }
}
