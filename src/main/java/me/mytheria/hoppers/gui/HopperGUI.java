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
                                                "gui.title"
                                        )
                        )
                );



        holder.setInventory(inventory);



        HopperData data =
                plugin.getHopperManager()
                        .getData(location);



        fill(inventory);



        int nextSpeed =
                data.getSpeedLevel() + 1;


        int nextRange =
                data.getRangeLevel() + 1;



        inventory.setItem(
                11,
                create(
                        Material.NETHER_STAR,
                        "&dSpeed Upgrade",
                        List.of(
                                "&7Current Level: &f"
                                        + data.getSpeedLevel(),
                                "&7",
                                "&7Cost: &a$"
                                        + plugin.getConfig()
                                        .getInt(
                                                "speed-upgrades."
                                                        + nextSpeed
                                                        + ".cost"
                                        ),
                                "&7",
                                "&aClick to upgrade"
                        )
                )
        );



        inventory.setItem(
                15,
                create(
                        Material.ENDER_EYE,
                        "&dRange Upgrade",
                        List.of(
                                "&7Current Level: &f"
                                        + data.getRangeLevel(),
                                "&7",
                                "&7Cost: &a$"
                                        + plugin.getConfig()
                                        .getInt(
                                                "range-upgrades."
                                                        + nextRange
                                                        + ".cost"
                                        ),
                                "&7",
                                "&aClick to upgrade"
                        )
                )
        );



        inventory.setItem(
                13,
                create(
                        Material.HOPPER,
                        "&dHopper Information",
                        List.of(
                                "&7Speed Level: &f"
                                        + data.getSpeedLevel(),
                                "&7Range Level: &f"
                                        + data.getRangeLevel()
                        )
                )
        );



        player.openInventory(inventory);

    }



    private void fill(Inventory inventory) {


        ItemStack glass =
                new ItemStack(
                        Material.PINK_STAINED_GLASS_PANE
                );


        ItemMeta meta =
                glass.getItemMeta();


        meta.setDisplayName(" ");


        glass.setItemMeta(meta);



        for (int i = 0; i < inventory.getSize(); i++) {


            if (inventory.getItem(i) == null) {

                inventory.setItem(
                        i,
                        glass
                );

            }

        }

    }



    private ItemStack create(
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



        ArrayList<String> lines =
                new ArrayList<>();


        for (String line : lore) {

            lines.add(
                    color(line)
            );

        }


        meta.setLore(lines);


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
