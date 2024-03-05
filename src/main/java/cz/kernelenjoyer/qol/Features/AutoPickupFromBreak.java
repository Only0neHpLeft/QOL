package cz.kernelenjoyer.qol.Features;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class AutoPickupFromBreak implements Listener {

    private final String inventoryFullMessage = ChatColor.RED + "Inventory Full";

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Collection<ItemStack> drops = event.getBlock().getDrops();
        for (ItemStack drop : drops) {
            if (hasInventorySpace(player, drop)) {
                event.setDropItems(false);
                alertPlayerInventoryFull(player);
                return;
            } else {
                player.getInventory().addItem(drop);
            }
        }
    }

    @EventHandler
    public void onEntityItemDrop(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;
        List<ItemStack> drops = event.getDrops();
        for (ItemStack drop : drops) {
            if (hasInventorySpace(player, drop)) {
                event.getDrops().clear();
                alertPlayerInventoryFull(player);
                return;
            } else {
                player.getInventory().addItem(drop);
            }
        }
    }

    private void alertPlayerInventoryFull(Player player) {
        player.sendTitle(inventoryFullMessage, "", 20, 100, 20);
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
    }

    private boolean hasInventorySpace(Player player, ItemStack item) {
        for (ItemStack content : player.getInventory().getContents()) {
            if (content == null || content.isSimilar(item) && content.getAmount() < content.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }
}