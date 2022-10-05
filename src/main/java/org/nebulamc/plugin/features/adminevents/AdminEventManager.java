package org.nebulamc.plugin.features.adminevents;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.utils.BossBarUtil;
import org.nebulamc.plugin.utils.InviteMessageUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AdminEventManager {

    private AdminEvent currentAdminEvent;

    public AdminEvent getCurrentEvent() {
        return currentAdminEvent;
    }

    public void endCurrentEvent() {
        currentAdminEvent = null;
    }

    @SuppressWarnings("unchecked")
    public <T extends AdminEvent> T startEvent(Player host, Class<T> event, long timerLength) {
        try {
            currentAdminEvent = event.getDeclaredConstructor(
                    Player.class,
                    Date.class
            ).newInstance(host, new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(timerLength)));

            int length = (int) (timerLength * 60);
            final int[] runTimes = { 0 };

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (runTimes[0] == length) {
                        if (currentAdminEvent.getPlayers().size() < 2) {
                            currentAdminEvent.getHost().sendMessage(
                                    Component.text("")
                            );
                            endCurrentEvent();
                        } else {
                            BossBarUtil.clear();
                            currentAdminEvent.start();
                        }
                        cancel();
                    } else if (runTimes[0] == (length - 30)) {
                        runTimes[0]++;
                        InviteMessageUtil.send(currentAdminEvent);
                        currentAdminEvent.setState(AdminEvent.State.QUEUEING);
                    } else {
                        runTimes[0]++;
                        BossBarUtil.update(currentAdminEvent);
                    }
                }
            }.runTaskTimer(Nebula.getInstance(), 0, 20);

            return (T) getCurrentEvent();
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            currentAdminEvent.getHost().sendMessage(
                Component.text("An error occured with the admin event lifecycle.")
                        .color(NamedTextColor.RED)
            );
            return null;
        }
    }

}
