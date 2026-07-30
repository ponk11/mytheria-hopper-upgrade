package me.mytheria.hoppers.gui;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class HopperGUI {


    private final MytheriaHoppers plugin;
    private final Location location;


    public HopperGUI(MytheriaHoppers plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
    }


    public void open(Player player) {


        HopperHolder holder =
                new HopperHolder(location);


        Inventory inventory =
                Bukkit.createInventory(
                        holder,
                        27,
                        color(
                                plugin.getConfig()
                                        .getString(
                                                "gui.title",
                                                "&d✦ Mytheria Hoppers ✦"
                                        )
                        )
                );


        holder.setInventory(inventory);


        HopperData data =
                plugin.getHopperManager()
                        .getData(location);



        inventory.setItem(
                11,
                createItem(
                        Material.NETHER_STAR,
                        "&dSpeed Upgrade",
                        List.of(
                                "&7Level: &f" + data.getSpeedLevel(),
                                "&7",
                                "&aClick to upgrade"
                        )
                )
        );


        inventory.setItem(
                15,
                createItem(
                        Material.ENDER_EYE,
                        "&dRange Upgrade",
                        List.of(
                                "&7Level: &f" + data.getRangeLevel(),
                                "&7",
                                "&aClick to upgrade"
                        )
                )
        );


        player.openInventory(inventory);

    }



    private ItemStack createItem(
            Material material,
            String name,
            List<String> lore
    ) {

        ItemStack item =
                new ItemStack(material);


        ItemMeta meta =
                item.getItemMeta();


        meta.setDisplayName(
                color(name)
        );


        meta.setLore(
                lore.stream()
                        .map(this::color)
                        .toList()
        );


        item.setItemMeta(meta);


        return item;

    }



    private String color(String text) {

        return ChatColor.translateAlternateColorCodes(
                '&',
                text
        );

    }

}
