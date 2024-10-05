package com.zachfr.zachlibrary.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {
public abstract void onCommand(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract String getName();

    public abstract String getPermission();

    public abstract String getUsage();

    public abstract String getDescription();

    public abstract String[] getAliases();
}
