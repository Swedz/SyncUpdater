package net.swedz.syncupdater;

import net.swedz.syncupdater.api.ServerRebootProcess;
import net.swedz.syncupdater.commands.SyncUpdaterCommand;
import net.swedz.syncupdater.update.UpdaterHandler;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SyncUpdater {
	void onEnable(JavaPlugin javaPlugin) {
		instance = this;
		plugin = javaPlugin;
		
		// Save the default config
		config = new SyncUpdaterConfig(plugin.getConfig());
		plugin.saveDefaultConfig();
		
		// Load the plugin data from the config
		this.reload();
		
		// Register our main command
		PluginCommand syncUpdaterCommand = plugin.getCommand("syncupdater");
		syncUpdaterCommand.setExecutor(new SyncUpdaterCommand(this));
	}
	
	void onDisable() {
		// Do nothing
	}
	
	public void reload() {
		// Close the old updater handler (if there was one)
		if(updaterHandler != null)
			updaterHandler.close();
		// Reload the configuration
		config.load();
		// Create a new update handler instance
		updaterHandler = config.getMode().getHandler(this);
	}
	
	private static SyncUpdater instance;
	public static SyncUpdater getInstance() {
		return instance;
	}
	
	private JavaPlugin plugin;
	public JavaPlugin getPlugin() {
		return plugin;
	}
	
	private SyncUpdaterConfig config;
	public SyncUpdaterConfig getConfig() {
		return config;
	}
	
	private UpdaterHandler updaterHandler;
	public UpdaterHandler getUpdaterHandler() {
		return updaterHandler;
	}
	
	private ServerRebootProcess rebootProcess;
	public ServerRebootProcess getRebootProcess() {
		return rebootProcess;
	}
	public void startRebootProcess() {
		rebootProcess = new ServerRebootProcess(updaterHandler);
		rebootProcess.start(plugin);
	}
	public boolean cancelRebootProcess() {
		if(rebootProcess != null) {
			rebootProcess.cancel();
			rebootProcess = null;
			return true;
		}
		return false;
	}
}
