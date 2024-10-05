package com.zachfr.zachlibrary.language;

import com.zachfr.zachlibrary.ZachLibrary;
import com.zachfr.zachlibrary.utils.ColorUtils;
import dev.vankka.enhancedlegacytext.EnhancedLegacyText;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class Message {
    private String message;
    private static String prefix;

    public Message(String message) {
        this.message = message;
    }

    public static void setPrefix(String prefix) {
        Message.prefix = prefix;
    }

    public static String getPrefix() {
        return prefix == null || prefix.length() == 0 ? "" : prefix + " ";
    }

    public Message processPlaceholder(String placeholder, String value) {
        message = message.replaceAll("%" + placeholder + "%", value);
        return this;
    }

    public void sendMessage(CommandSender commandSender) {
        Audience player = ZachLibrary.getInstance().getAdventure().sender(commandSender);
        player.sendMessage(EnhancedLegacyText.get().buildComponent(message).build());
        //commandSender.sendMessage(ColorUtils.color(message));
    }

    public void sendPrefixedMessage(CommandSender commandSender) {
        message = getPrefix() + message;
        sendMessage(commandSender);
        //commandSender.sendMessage(ColorUtils.color(getPrefix() + message));
    }

    public String getMessage() {
        return message;
    }
}
