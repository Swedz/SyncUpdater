package net.swedz.syncupdater.update.auto;

import net.swedz.syncupdater.SyncUpdater;
import net.swedz.syncupdater.update.UpdaterHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class AutomaticUpdaterHandler extends UpdaterHandler {
	private int                      interval;
	private AutomaticUpdaterRunnable runnable;
	
	public AutomaticUpdaterHandler(SyncUpdater syncUpdater) {
		super(syncUpdater);
		try {
			FileConfiguration config = syncUpdater.getConfig().getConfiguration();
			ConfigurationSection automaticConfigSection = config.getConfigurationSection("automatic");
			this.interval = automaticConfigSection.getInt("interval");
			this.runnable = new AutomaticUpdaterRunnable(this)
					.start(syncUpdater.getPlugin(), interval);
		} catch (Exception ex) {
			Bukkit.getLogger().warning("[SyncUpdater] Failed to read configuration for section: 'automatic'.");
			ex.printStackTrace();
		}
	}
	
	public int getInterval() {
		return interval;
	}
	
	public AutomaticUpdaterRunnable getRunnable() {
		return runnable;
	}
	
	@Override
	public void close() {
		runnable.cancel();
	}
}
