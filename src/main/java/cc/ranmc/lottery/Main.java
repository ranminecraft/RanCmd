package cc.ranmc.lottery;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main extends JavaPlugin implements Listener {

    private static final String PLUGIN = "RanLottery";

    @Getter
    private final String PREFIX = color("&b" + PLUGIN + ">>>");

    private File dataFile;
    @Getter
    private YamlConfiguration dataYml;
    private Papi papi;
    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        print("&e-----------------------");
        print("&b" + PLUGIN + " &dBy阿然");
        print("&b插件版本:"+getDescription().getVersion());
        print("&b服务器版本:"+getServer().getVersion());
        print("&chttps://www.ranmc.cc/");
        print("&e-----------------------");

        loadConfig();

        //注册Event
        Bukkit.getPluginManager().registerEvents(this, this);

        super.onEnable();
    }

    /**
     * 指令输入
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             Command cmd,
                             @NotNull String label,
                             String[] args) {


        if (cmd.getName().equalsIgnoreCase("ranl")) {
            if (args.length == 1 &&
                    args[0].equalsIgnoreCase("reload")){
                if (sender.hasPermission("ranl.admin")) {
                    loadConfig();
                    sender.sendMessage(PREFIX + color("&a重载成功"));
                    return true;
                } else {
                    sender.sendMessage(PREFIX + color("&c没有权限"));
                }
            }
        }

        if (!(sender instanceof Player)) {
            print("&c该指令不能在控制台输入");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("ranl")) {
            if (sender.hasPermission("ranl.user")) {
                // 开始抽奖
                int level = dataYml.getInt(player.getName(), 1);
                double change = getConfig().getDouble(level + ".change");
                if (change == 0) {
                    level = 1;
                    change = getConfig().getDouble("1.change");
                }
                if (change == 0) {
                    sender.sendMessage(PREFIX + color("&c配置错误请联系管理员"));
                    return true;
                }
                if (change >= Math.random()) {
                    getConfig().getStringList(level + ".final-reward").forEach(command -> {
                        if (papi != null) {
                            command = PlaceholderAPI.setPlaceholders(player, command);
                        }
                        run(command.replace("%player%", player.getName()));
                    });
                    level++;
                } else {
                    List<String> rewardList = getConfig().getStringList(level + ".other-reward");
                    String command = rewardList.get(new Random().nextInt(rewardList.size()));
                    if (papi != null) {
                        command = PlaceholderAPI.setPlaceholders(player, command);
                    }
                    run(command.replace("%player%", player.getName()));
                    level--;
                    if (level <= 0) level = 1;
                }
                dataYml.set(player.getName(), level);
                try {
                    dataYml.save(dataFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            } else {
                sender.sendMessage(PREFIX + color("&c没有权限"));
            }
        }

        sender.sendMessage(PREFIX + color("&c未知指令"));
        return true;
    }

    /**
     * 加载配置文件
     */
    private void loadConfig() {
        saveDefaultConfig();
        reloadConfig();

        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) saveResource("data.yml", false);
        dataYml = YamlConfiguration.loadConfiguration(dataFile);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papi = new Papi();
            papi.register();
            print(PREFIX + color("&a成功加载PlaceholderAPI插件"));
        } else {
            print(PREFIX + color("&c无法找到PlaceholderAPI插件"));
        }
    }

    /**
     * 执行指令
     */
    public void run(String command) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
    }

    /**
     * 文本颜色
     */
    private static String color(String text){
        return text.replace("&","§");
    }

    /**
     * 后台信息
     */
    public void print(String msg){
        Bukkit.getConsoleSender().sendMessage(color(msg));
    }

    /**
     * 公屏信息
     */
    public void say(String msg){
        Bukkit.broadcastMessage(color(msg));
    }
}
