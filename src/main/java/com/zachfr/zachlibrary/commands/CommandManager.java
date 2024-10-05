package com.zachfr.zachlibrary.commands;

import com.zachfr.zachlibrary.utils.ColorUtils;
import com.zachfr.zachlibrary.utils.LogUtils;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final HashMap<String, com.zachfr.zachlibrary.commands.Command> commands = new HashMap<>();

    private final JavaPlugin plugin;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public CommandManager addCommand(com.zachfr.zachlibrary.commands.Command command) {
        PluginCommand pluginCommand = plugin.getCommand(command.getName());
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
            commands.put(command.getName(), command);
            LogUtils.debug("Registered command: " + command.getName());
        }
        return this;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        com.zachfr.zachlibrary.commands.Command cmd = commands.get(command.getName());
        if (cmd != null) {
            if(args.length > 0){
                for(SubCommand subCommand : cmd.getSubCommands()){
                    if(subCommand.getName().equalsIgnoreCase(args[0]) || Arrays.asList(subCommand.getAliases()).contains(args[0])){
                        if(sender instanceof Player){
                            Player player = (Player) sender;
                            if (subCommand.getPermission() != null && !player.hasPermission(subCommand.getPermission())) {
                                player.sendMessage(ColorUtils.color("&cYou do not have permission to use this command!"));
                                return true;
                            }
                        }
                        subCommand.onCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                        return true;
                    }
                }
            }

            if(sender instanceof Player){
                Player player = (Player) sender;
                if (cmd.getPermission() != null && !player.hasPermission(cmd.getPermission())) {
                    player.sendMessage(ColorUtils.color("&cYou do not have permission to use this command!"));
                    return true;
                }
            }

            cmd.onCommand(sender, args);
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        com.zachfr.zachlibrary.commands.Command cmd = commands.get(command.getName());
        List<String> tabComplete = new ArrayList<>();
        if(cmd != null){
            if(args.length > 0){
                for(SubCommand subCommand : cmd.getSubCommands()){
                    if(!sender.hasPermission(subCommand.getPermission()))
                        continue;
                    if(subCommand.getName().toLowerCase().startsWith(args[0].toLowerCase()) || Arrays.asList(subCommand.getAliases()).contains(args[0].toLowerCase())){
                        tabComplete.add(subCommand.getName());

                        if(args.length > 1)
                            return subCommand.onTabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
                    }
                }
            }
            if(tabComplete.size() > 0)
                return tabComplete;
            return cmd.onTabComplete(sender, args);
        }
        return tabComplete;
    }
}
