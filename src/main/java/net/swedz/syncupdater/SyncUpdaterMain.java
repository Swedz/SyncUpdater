package net.swedz.syncupdater;

import org.bukkit.plugin.java.JavaPlugin;

public class SyncUpdaterMain extends JavaPlugin {
	@Override
	public void onEnable() {
		new SyncUpdater().onEnable(this);
	}
	
	@Override
	public void onDisable() {
		SyncUpdater.getInstance().onDisable();
	}
}
