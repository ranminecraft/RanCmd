package cc.ranmc.lottery;

import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import java.util.Objects;

public class Papi extends PlaceholderExpansion {

    public Papi() {}

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Ranica";
    }

    @Override
    public String getIdentifier() {
        return "ranl";
    }

    @Override
    public String getVersion(){
        return "Beta";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {

        if (params.startsWith("level")) {
            if (offlinePlayer == null) return "未知玩家";
            return String.valueOf(Main.getInstance().getDataYml()
                    .getInt(Objects.requireNonNull(offlinePlayer.getName()), 1));
        }
        return "未知变量";
    }
}