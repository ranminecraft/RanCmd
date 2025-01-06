package cc.ranmc.lottery;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static cc.ranmc.lottery.Main.color;

public class CostUtil {

    public static boolean isCostItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return item.getType().toString()
                .equals(Main.getInstance()
                        .getConfig().getString("cost-item.material"))
                && meta.hasDisplayName() && meta.getDisplayName()
                    .equals(color(Main.getInstance()
                            .getConfig().getString("cost-item.name", "&6消耗物品")));
    }

    public static boolean cost(Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < 45; i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && isCostItem(item)) {
                int amount = item.getAmount();
                amount--;
                if (amount <= 0) {
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                } else {
                    item.setAmount(amount);
                    player.getInventory().setItem(i, item);
                }
                return true;
            }
        }
        return false;
    }
}
