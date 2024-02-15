package cc.ranmc;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Main extends JavaPlugin implements Listener {

    private static final String PLUGIN = "BukkitPlugin";

    @Getter
    private final String PREFIX = color("&b" + PLUGIN + ">>>");

    private File dataFile;
    private YamlConfiguration dataYml;

    @Override
    public void onEnable() {
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


        if (cmd.getName().equalsIgnoreCase("bkp")) {
            if (args.length == 1 &&
                    args[0].equalsIgnoreCase("reload")){
                if(sender.hasPermission("bkp.admin")) {
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

        sender.sendMessage(PREFIX + color("&c未知指令"));
        return true;
    }

    /**
     * 加载配置文件
     */
    private void loadConfig(){
        saveDefaultConfig();
        reloadConfig();

        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) saveResource("data.yml", false);
        dataYml = YamlConfiguration.loadConfiguration(dataFile);
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
