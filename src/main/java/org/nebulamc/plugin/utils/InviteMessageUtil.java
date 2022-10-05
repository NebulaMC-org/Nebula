package org.nebulamc.plugin.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.adminevents.AdminEvent;
import org.nebulamc.plugin.hooks.LuckPermsHook;

public class InviteMessageUtil {

    private static final String LINE = "<gradient:#bae6ff:#ecabff>• • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • •</gradient>";

    @SuppressWarnings("ConstantConditions")
    public static void send(AdminEvent e) {

        Component lpPrefix = LegacyComponentSerializer.legacyAmpersand().deserialize(
                Nebula.getHook("LuckPerms", LuckPermsHook.class)
                        .getUser(e.getHost())
                        .getCachedData()
                        .getMetaData()
                        .getPrefix()
        ).append(Component.text(e.getHost().getName()));

        Component line = MiniMessage.miniMessage().deserialize(LINE);
        Component header = lpPrefix.append(Component.text(" is hosting a " + e.getEventName() + " admin event!").color(TextColor.fromHexString("#ecabff")));
        Component button = Component.text("JOIN EVENT").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true)
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/joinevent"));

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(
                    line.append(Component.newline())
                            .append(header)
                            .append(Component.newline())
                            .append(button)
                            .append(Component.newline())
                            .append(line)
            );
        }

    }

}
