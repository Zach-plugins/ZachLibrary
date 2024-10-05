package com.zachfr.zachlibrary.guis;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import com.samjakob.spigui.toolbar.SGToolbarBuilder;
import com.zachfr.zachlibrary.utils.ChatPromptUtils;
import com.zachfr.zachlibrary.utils.ItemBuilder;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SelectSoundGui {
    private SpiGUI guiManager;
    private Player player;
    private Consumer<XSound> consumer;
    private Plugin plugin;
    private String search = "";
    private int count = 0;

    public SelectSoundGui(SpiGUI guiManager, Player player, Consumer<XSound> consumer, Plugin plugin){
        this.guiManager = guiManager;
        this.player = player;
        this.consumer = consumer;
        this.plugin = plugin;

        player.openInventory(constructGui());
    }

    private Inventory constructGui() {
        SGMenu gui = guiManager.create("&8Select a sound", 5);
        gui.setAutomaticPaginationEnabled(true);
        gui.setToolbarBuilder(toolbarBuilder);

        int page = 0;
        int slot = 0;

        List<XSound> sounds = Arrays.stream(XSound.values()).filter(compatibleSound -> compatibleSound.name().contains(search)).collect(Collectors.toList());
        count = sounds.size();
        if(!sounds.isEmpty())
            for(XSound sound : sounds){
                gui.setButton(page, slot, new SGButton(new ItemBuilder(XMaterial.PAPER.parseItem())
                        .name("&a" + sound.name().toLowerCase().replace("_", " "))
                        .lore(
                                "&2Right-click &7to play this sound.",
                                "&2Left-click &7to select this sound.",
                                "&7Name: " + sound.name()
                        )
                        .build()).withListener(inventoryClickEvent -> {
                    if(inventoryClickEvent.getClick().isLeftClick()){
                        player.closeInventory();
                        consumer.accept(sound);
                    }
                    else if(inventoryClickEvent.isRightClick()){
                        sound.play(player);
                    }
                }));

                slot++;
                if(slot == 45){
                    slot = 0;
                    page++;
                }
            }
        else
            gui.setButton(22, new SGButton(new ItemBuilder(XMaterial.BARRIER.parseItem()).name("&4No sound found").lore("&7No sound found with " + search + " name.").build()));

        return gui.getInventory();
    }
    
    private SGToolbarBuilder toolbarBuilder = (slot, page, type, inventory) -> {
        switch (slot) {
            case 3:
                if (inventory.getCurrentPage() > 0) return new SGButton(new ItemBuilder(Material.ARROW)
                        .name("&a&l\u2190 Previous Page")
                        .lore(
                                "&aClick to move back to",
                                "&apage " + inventory.getCurrentPage() + ".")
                        .build()
                ).withListener(event -> {
                    event.setCancelled(true);
                    inventory.previousPage(event.getWhoClicked());
                });
                else return null;

            case 4:
                return new SGButton(new ItemBuilder(Material.NAME_TAG)
                        .name("&7&lPage " + (inventory.getCurrentPage() + 1) + " of " + inventory.getMaxPage())
                        .lore(
                                "&7You are currently viewing",
                                "&7page " + (inventory.getCurrentPage() + 1) + "."
                        ).build()
                ).withListener(event -> event.setCancelled(true));

            case 5:
                if (inventory.getCurrentPage() < inventory.getMaxPage() - 1) return new SGButton(new ItemBuilder(Material.ARROW)
                        .name("&a&lNext Page \u2192")
                        .lore(
                                "&aClick to move forward to",
                                "&apage " + (inventory.getCurrentPage() + 2) + "."
                        ).build()
                ).withListener(event -> {
                    event.setCancelled(true);
                    inventory.nextPage(event.getWhoClicked());
                });
                else return null;
            case 7:
                return new SGButton(new ItemBuilder(XMaterial.COMPASS.parseItem())
                        .name("&6&lSearch")
                        .lore(
                                "&7Current: " + search,
                                "&7Count: " + count
                        )
                        .build()
                ).withListener(event -> {
                    player.closeInventory();
                    ChatPromptUtils.showPrompt(plugin, player, "&6&lEnter sound name:", chatConfirmEvent -> {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            search = chatConfirmEvent.getMessage().toUpperCase();
                            player.openInventory(constructGui());
                        });
                    });
                });
            case 8:
                return new SGButton(new ItemBuilder(XMaterial.BARRIER.parseItem()).name("&aNone")
                        .lore(
                                "&7Set sound to null.",
                                "&7Disable sound."
                        ).build()).withListener(event -> {
                    player.closeInventory();
                    consumer.accept(null);
                });
            default:
                return null;
        }
    };
}
