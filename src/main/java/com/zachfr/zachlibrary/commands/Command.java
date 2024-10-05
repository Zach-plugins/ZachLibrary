package com.zachfr.zachlibrary.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    public List<SubCommand> subCommands = new ArrayList<>();

    public abstract void onCommand(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public Command addSubCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
        return this;
    }

    public abstract String getName();

    public abstract String getPermission();

    public abstract String getUsage();

    public abstract String getDescription();

    public abstract String[] getAliases();
}
