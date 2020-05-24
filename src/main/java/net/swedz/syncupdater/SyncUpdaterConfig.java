package net.swedz.syncupdater;

import net.swedz.syncupdater.update.UpdateMode;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class SyncUpdaterConfig {
	private final FileConfiguration configuration;
	
	private boolean    verbose;
	private UpdateMode mode;
	private String     updateDirectory;
	private int        rebootTimer;
	
	private String noPermissionMessage, updatedMessage, countdownMessage, rebootingMessage;
	
	public SyncUpdaterConfig(FileConfiguration configuration) {
		this.configuration = configuration;
		this.load();
	}
	
	public void load() {
		try {
			this.verbose = configuration.getBoolean("verbose");
			this.mode = UpdateMode.valueOf(configuration.getString("mode"));
			this.updateDirectory = configuration.getString("directory");
			this.rebootTimer = configuration.getInt("reboot-timer");
			
			ConfigurationSection messages = configuration.getConfigurationSection("messages");
			this.noPermissionMessage = this.colorize(messages.getString("no-permission"));
			this.updatedMessage = this.colorize(messages.getString("updated"));
			this.countdownMessage = this.colorize(messages.getString("countdown"));
			this.rebootingMessage = this.colorize(messages.getString("rebooting"));
		} catch (Exception ex) {
			System.out.println(ChatColor.RED + "There is a problem with your SyncUpdater config!");
			ex.printStackTrace();
		}
	}
	
	private String colorize(String string) {
		return string == null ? null : ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public FileConfiguration getConfiguration() {
		return configuration;
	}
	
	public boolean isVerbose() {
		return verbose;
	}
	
	public UpdateMode getMode() {
		return mode;
	}
	
	public File getUpdateDirectory() {
		File directory = new File(updateDirectory);
		directory.mkdirs();
		return directory;
	}
	
	public int getRebootTimer() {
		return rebootTimer;
	}
	
	public String getNoPermissionMessage() {
		return noPermissionMessage;
	}
	
	public String getUpdatedMessage() {
		return updatedMessage;
	}
	
	public String getCountdownMessage() {
		return countdownMessage;
	}
	
	public String getRebootingMessage() {
		return rebootingMessage;
	}
}
