package com.zachfr.zachlibrary.utils;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(String material) {
        if(material == null) {
            this.itemStack = new ItemStack(Material.PAPER);
            return;
        }
        material = material.toUpperCase();

        Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(material);

        if(xMaterial.isPresent() && xMaterial.get().parseItem() != null) {
            this.itemStack = xMaterial.get().parseItem();
            return;
        }

        this.itemStack = new ItemStack(Material.PAPER);
        LogUtils.error("Invalid material: " + material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder name(String name) {
        ItemMeta stackMeta = itemStack.getItemMeta();
        stackMeta.setDisplayName(ColorUtils.color(name));
        itemStack.setItemMeta(stackMeta);
        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        return lore(List.of(lore));
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta stackMeta = itemStack.getItemMeta();
        stackMeta.setLore(ColorUtils.color(lore));
        itemStack.setItemMeta(stackMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
