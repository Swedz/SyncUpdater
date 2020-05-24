package net.swedz.syncupdater.update.auto;

import net.swedz.syncupdater.update.UpdaterHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

class AutomaticUpdaterRunnable extends BukkitRunnable {
	private final UpdaterHandler handler;
	
	public AutomaticUpdaterRunnable(UpdaterHandler handler) {
		this.handler = handler;
	}
	
	public AutomaticUpdaterRunnable start(JavaPlugin plugin, int interval) {
		this.runTaskTimer(plugin, 0L, interval * 20L);
		return this;
	}
	
	@Override
	public void run() {
		handler.performFullUpdate();
	}
}
