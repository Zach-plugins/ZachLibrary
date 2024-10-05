package com.zachfr.zachlibrary.guis;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import com.zachfr.zachlibrary.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class GuiUtils {
    public static void fillBackground(SGMenu menu, int page, ItemStack itemStack, boolean clueBackground){
        for(int i = 0; i < menu.getPageSize(); i++){
            if(menu.getButton(i) == null){
                if(clueBackground)
                    menu.stickSlot(i);
                menu.setButton(page, i, new SGButton(new ItemBuilder(itemStack).name(" ").build()));
            }
        }
    }

    public static void fillBackground(SGMenu menu, int page, ItemStack itemStack){
        fillBackground(menu, page, itemStack, false);
    }

    public static void fillBackground(SGMenu menu, ItemStack itemStack){
        fillBackground(menu, 0, itemStack, false);
    }

    public static void fillCorner(SGMenu menu, int page, ItemStack itemStack){
        int[] slot;
        switch(menu.getRowsPerPage()){
            case 1: slot = new int[]{0, 8}; break;
            case 2: slot = new int[]{0, 8, 9, 17}; break;
            case 3: slot = new int[]{0, 1, 7, 8, 9, 17, 18, 19, 25, 26}; break;
            case 4: slot = new int[]{0, 1, 7, 8, 9, 17, 18, 26, 27, 28, 34, 35}; break;
            case 5: slot = new int[]{0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44}; break;
            case 6: slot = new int[]{0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53}; break;
            default: slot = new int[]{0}; break;
        }
        for(int i : slot)
            if(menu.getButton(i) == null){
                menu.setButton(page, i, new SGButton(new ItemBuilder(itemStack).name(" ").build()));
            }
    }

    public static void fillBorder(SGMenu menu, ItemStack itemStack, Boolean stick){
        int[] slot;
        switch(menu.getRowsPerPage()){
            case 1: slot = new int[]{0, 8}; break;
            case 2: slot = new int[]{0, 8, 9, 17}; break;
            case 3: slot = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}; break;
            case 4: slot = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35}; break;
            case 5: slot = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}; break;
            case 6: slot = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53}; break;
            default: slot = new int[]{0}; break;
        }
        for(int i : slot){
            menu.setButton(i, new SGButton(new ItemBuilder(itemStack).name(" ").build()));
            if(stick)
                menu.stickSlot(i);
        }
    }
}
