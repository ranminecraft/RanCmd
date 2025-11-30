package cc.ranmc.rancmd;

import cc.ranmc.rancmd.command.MainCommand;
import cc.ranmc.rancmd.command.MainTabComplete;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static cc.ranmc.rancmd.util.BasicUtil.color;
import static cc.ranmc.rancmd.util.BasicUtil.print;

public class Main extends JavaPlugin implements Listener {

    private static final String PLUGIN = "RanCmd";
    public static final String PREFIX = color("&b" + PLUGIN + ">>>");
    @Getter
    private YamlConfiguration dataYml;
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

        // BStats
        new Metrics(this, 28165);

        loadConfig();

        // 注册Event
        Bukkit.getPluginManager().registerEvents(this, this);

        // 注册指令
        PluginCommand mainCmd = Bukkit.getPluginCommand("rancmd");
        mainCmd.setExecutor(new MainCommand());
        mainCmd.setTabCompleter(new MainTabComplete());

        super.onEnable();
    }

    /**
     * 加载配置文件
     */
    public void loadConfig() {
        saveDefaultConfig();
        reloadConfig();

        /*File dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) saveResource("data.yml", false);
        dataYml = YamlConfiguration.loadConfiguration(dataFile);*/

        /*if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papi = new Papi();
            papi.register();
            print(PREFIX + color("&a成功加载PlaceholderAPI插件"));
        } else {
            print(PREFIX + color("&c无法找到PlaceholderAPI插件"));
        }*/
    }
}
