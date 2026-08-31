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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HopperGUI {

    private final MytheriaHoppers plugin;
    private final Location location;

    public HopperGUI(
            MytheriaHoppers plugin,
            Location location
    ) {
        this.plugin = plugin;
        this.location = location;
    }

    @SuppressWarnings("deprecation")
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

        fill(inventory);

        createSpeedItem(
                inventory,
                data
        );

        createRangeItem(
                inventory,
                data
        );

        inventory.setItem(
                13,
                createItem(
                        Material.HOPPER,
                        "&dHopper Information",
                        List.of(
                                "&7",
                                "&7Speed Level: &f"
                                        + data.getSpeedLevel(),
                                "&7Range Level: &f"
                                        + data.getRangeLevel(),
                                "&7",
                                "&8Location: "
                                        + location.getBlockX()
                                        + ", "
                                        + location.getBlockY()
                                        + ", "
                                        + location.getBlockZ()
                        )
                )
        );

        player.openInventory(inventory);
    }

    private void createSpeedItem(
            Inventory inventory,
            HopperData data
    ) {

        int current =
                data.getSpeedLevel();

        int max =
                plugin.getConfig()
                        .getInt(
                                "settings.max-speed-level",
                                4
                        );

        if (current >= max) {

            inventory.setItem(
                    11,
                    createItem(
                            Material.NETHER_STAR,
                            "&dSpeed Upgrade",
                            List.of(
                                    "&7",
                                    "&7Current Level: &f"
                                            + current,
                                    "&7",
                                    "&d✦ &fMAX LEVEL",
                                    "&7",
                                    "&8This upgrade is fully upgraded."
                            )
                    )
            );

            return;
        }

        int next = current + 1;

        double cost =
                plugin.getConfig()
                        .getDouble(
                                "speed-upgrades."
                                        + next
                                        + ".cost"
                        );

        int ticks =
                plugin.getConfig()
                        .getInt(
                                "speed-upgrades."
                                        + next
                                        + ".transfer-ticks"
                        );

        inventory.setItem(
                11,
                createItem(
                        Material.NETHER_STAR,
                        "&dSpeed Upgrade",
                        List.of(
                                "&7",
                                "&7Current Level: &f"
                                        + current,
                                "&7Next Level: &d"
                                        + next,
                                "&7",
                                "&7Transfer Speed: &f"
                                        + ticks
                                        + " ticks",
                                "&7Cost: &a$"
                                        + formatMoney(cost),
                                "&7",
                                "&aClick to purchase"
                        )
                )
        );
    }

    private void createRangeItem(
            Inventory inventory,
            HopperData data
    ) {

        int current =
                data.getRangeLevel();

        int max =
                plugin.getConfig()
                        .getInt(
                                "settings.max-range-level",
                                4
                        );

        if (current >= max) {

            inventory.setItem(
                    15,
                    createItem(
                            Material.ENDER_EYE,
                            "&dRange Upgrade",
                            List.of(
                                    "&7",
                                    "&7Current Level: &f"
                                            + current,
                                    "&7",
                                    "&d✦ &fMAX LEVEL",
                                    "&7",
                                    "&8This upgrade is fully upgraded."
                            )
                    )
            );

            return;
        }

        int next = current + 1;

        double cost =
                plugin.getConfig()
                        .getDouble(
                                "range-upgrades."
                                        + next
                                        + ".cost"
                        );

        int range =
                plugin.getConfig()
                        .getInt(
                                "range-upgrades."
                                        + next
                                        + ".range"
                        );

        inventory.setItem(
                15,
                createItem(
                        Material.ENDER_EYE,
                        "&dRange Upgrade",
                        List.of(
                                "&7",
                                "&7Current Level: &f"
                                        + current,
                                "&7Next Level: &d"
                                        + next,
                                "&7",
                                "&7Collection Range: &f"
                                        + range
                                        + " blocks",
                                "&7Cost: &a$"
                                        + formatMoney(cost),
                                "&7",
                                "&aClick to purchase"
                        )
                )
        );
    }

    private void fill(
            Inventory inventory
    ) {

        ItemStack glass =
                new ItemStack(
                        Material.PURPLE_STAINED_GLASS_PANE
                );

        ItemMeta meta =
                glass.getItemMeta();

        if (meta != null) {
            setDisplayNameSuppressed(meta, " ");
            glass.setItemMeta(meta);
        }

        for (int i = 0; i < inventory.getSize(); i++) {

            inventory.setItem(
                    i,
                    glass
            );
        }
    }

    @SuppressWarnings("deprecation")
    private void setDisplayNameSuppressed(ItemMeta meta, String name) {
        meta.setDisplayName(name);
    }

    @SuppressWarnings("deprecation")
    private ItemStack createItem(
            Material material,
            String name,
            List<String> lore
    ) {

        ItemStack item =
                new ItemStack(material);

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null) {
            return item;
        }

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

    private String formatMoney(
            double amount
    ) {

        return NumberFormat
                .getNumberInstance(
                        Locale.US
                )
                .format(amount);
    }

    @SuppressWarnings("deprecation")
    private String color(
            String text
    ) {

        return ChatColor
                .translateAlternateColorCodes(
                        '&',
                        text
                );
    }
}
