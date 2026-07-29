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

import java.util.ArrayList;

public class HopperGUI {


    private final MytheriaHoppers plugin;
    private final Location location;


    public HopperGUI(MytheriaHoppers plugin, Location location) {

        this.plugin = plugin;
        this.location = location;

    }


    public void open(Player player) {


        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                ChatColor.translateAlternateColorCodes(
                        '&',
                        plugin.getConfig()
                                .getString(
                                        "gui.title"
                                )
                )
        );


        HopperData data = plugin
                .getHopperManager()
                .getData(location);



        inventory.setItem(
                11,
                createItem(
                        Material.NETHER_STAR,
                        "&dSpeed Upgrade",
                        "&7Level: &f" + data.getSpeedLevel(),
                        "&7Click to upgrade"
                )
        );


        inventory.setItem(
                15,
                createItem(
                        Material.ENDER_EYE,
                        "&dRange Upgrade",
                        "&7Level: &f" + data.getRangeLevel(),
                        "&7Click to upgrade"
                )
        );


        player.openInventory(inventory);

    }



    private ItemStack createItem(Material material, String... lore) {

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        lore[0]
                )
        );


        ArrayList<String> lines = new ArrayList<>();

        for (int i = 1; i < lore.length; i++) {

            lines.add(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            lore[i]
                    )
            );

        }


        meta.setLore(lines);

        item.setItemMeta(meta);


        return item;
    }
}
