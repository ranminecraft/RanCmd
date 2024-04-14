package cc.ranmc.lottery;

import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

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
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {

        if (offlinePlayer == null) return "未知玩家";

        if (params.startsWith("level")) {
            return String.valueOf(Main.getInstance().getDataYml()
                    .getInt(Objects.requireNonNull(offlinePlayer.getName()), 1));
        }
        if (params.startsWith("count")) {
            return String.valueOf(Main.getInstance().getDataYml()
                    .getInt("count." + Objects.requireNonNull(offlinePlayer.getName()), 0));
        }
        return "未知变量";
    }
}