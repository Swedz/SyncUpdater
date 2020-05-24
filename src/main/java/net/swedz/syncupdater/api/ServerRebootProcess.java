package net.swedz.syncupdater.api;

import net.swedz.syncupdater.update.UpdaterHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerRebootProcess extends BukkitRunnable {
	private final UpdaterHandler handler;
	
	private int started, counter;
	
	public ServerRebootProcess(UpdaterHandler handler) {
		this.handler = handler;
	}
	
	public void start(JavaPlugin plugin) {
		started = handler.getSyncUpdater().getConfig().getRebootTimer();
		counter = started;
		this.runTaskTimer(plugin, 20L, 20L);
	}
	
	@Override
	public void run() {
		// We are still counting down
		if(counter > 0) {
			String countdownMessage = handler.getSyncUpdater().getConfig().getCountdownMessage();
			if(countdownMessage != null) {
				// We have a X divisible by 60 (minute) seconds left
				if(counter % 60 == 0) {
					int minutesLeft = counter / 60;
					Bukkit.broadcastMessage(countdownMessage.replace("%time%", minutesLeft + " minute" + (minutesLeft > 1 ? "s" : "")));
				}
				// We have starting time or 15 or 10 or 5 or less seconds left
				else if(counter == started || counter == 15 || counter == 10 || counter <= 5) {
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', countdownMessage.replace("%time%", counter + " second" + (counter > 1 ? "s" : ""))));
				}
			}
			// Count down the counter
			counter--;
		}
		// The server needs to reboot now
		else {
			// Conduct the reboot and stop the process
			this.cancel();
			String rebootingMessage = handler.getSyncUpdater().getConfig().getRebootingMessage();
			if(rebootingMessage != null)
				Bukkit.broadcastMessage(
						ChatColor.translateAlternateColorCodes('&', rebootingMessage));
			Bukkit.shutdown();
		}
	}
}
