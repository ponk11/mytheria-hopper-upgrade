package me.mytheria.hoppers.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

public class ColorUtil {


    public static String color(String text) {

        return ChatColor.translateAlternateColorCodes(
                '&',
                text
        );

    }


    public static Component component(String text) {

        return Component.text(
                color(text)
        );

    }

}
