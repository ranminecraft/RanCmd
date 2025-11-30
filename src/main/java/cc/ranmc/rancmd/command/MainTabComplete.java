package cc.ranmc.rancmd.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender,
                                      @NotNull Command command,
                                      @NotNull String alias,
                                      String[] args) {
        if (!sender.hasPermission("rancmd.admin")) return new ArrayList<>();
        if (args.length == 1) return Arrays.asList("reload", "help", "cmd");
        if (args.length == 2 && args[0].equals("cmd")) return null;
        if (args.length == 3 && args[0].equals("cmd")) return Arrays.asList("指令(不带斜杠)");;
        return new ArrayList<>();
    }

}