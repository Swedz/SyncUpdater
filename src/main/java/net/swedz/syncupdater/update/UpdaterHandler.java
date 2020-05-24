package net.swedz.syncupdater.update;

import net.swedz.syncupdater.SyncUpdater;
import net.swedz.syncupdater.api.AttemptRebootEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public abstract class UpdaterHandler {
	protected final SyncUpdater syncUpdater;
	
	public UpdaterHandler(SyncUpdater syncUpdater) {
		this.syncUpdater = syncUpdater;
	}
	
	public SyncUpdater getSyncUpdater() {
		return syncUpdater;
	}
	
	protected String generateSHA256(File file) {
		try {
			// Generate the SHA256 for the given file
			// We use this to check if we need to update a plugin or note
			byte[] data = Files.readAllBytes(file.toPath());
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(data);
			return Base64.getEncoder().encodeToString(digest.digest());
		} catch (IOException | NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public File getPluginsDirectory() {
		return new File(System.getProperty("user.dir") + "/plugins/");
	}
	
	protected void performUpdate(File file) {
		try {
			long start = System.currentTimeMillis();
			// Copy the file from the update directory to the plugin directory of this server
			Files.copy(
					file.toPath(),
					new File(this.getPluginsDirectory(), file.getName()).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			// If we have verbose on, log what just happened
			if(syncUpdater.getConfig().isVerbose()) {
				System.out.println("[SyncUpdater] Queued up update for " + file.getName() + " in " + (System.currentTimeMillis() - start) + "ms.");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	protected int performAllUpdates() {
		// Find all of the plugins currently installed
		final Map<String, String> plugins = new HashMap<>();
		for(File pluginFile : this.getPluginsDirectory().listFiles())
			if(pluginFile.isFile()) {
				String sha256 = this.generateSHA256(pluginFile);
				plugins.put(pluginFile.getName(), sha256);
			}
		
		// Iterate over all of the plugins we may need to update
		int updates = 0;
		for(File sourceFile : this.getSyncUpdater().getConfig().getUpdateDirectory().listFiles())
			if(plugins.containsKey(sourceFile.getName())) {
				String sourceSha = this.generateSHA256(sourceFile);
				String pluginSha = plugins.get(sourceFile.getName());
				// The hash codes are mismatching, we need to update
				if(!sourceSha.equalsIgnoreCase(pluginSha)) {
					// Update the file
					this.performUpdate(sourceFile);
					updates++;
				}
			}
		
		// Return the amount of updates we performed
		return updates;
	}
	
	public int performFullUpdate() {
		// Perform the updates (if any)
		int updates = this.performAllUpdates();
		// We made some changes to the plugin directory
		if(updates > 0) {
			// Broadcast to the permitted users about the update
			Bukkit.broadcast(
					ChatColor.GRAY + "" + ChatColor.ITALIC + "[SyncUpdater: Performed update on " + updates + " plugins]",
					"syncupdater.verbose");
			
			// Attempt to reboot the server
			AttemptRebootEvent attemptRebootEvent = new AttemptRebootEvent();
			this.getSyncUpdater().getPlugin().getServer().getPluginManager().callEvent(attemptRebootEvent);
			if(!attemptRebootEvent.isCancelled()) {
				// Broadcast the updated message (if they have one set)
				String updatedMessage = this.getSyncUpdater().getConfig().getUpdatedMessage();
				if(updatedMessage != null)
					Bukkit.broadcastMessage(
							ChatColor.translateAlternateColorCodes('&', updatedMessage));
				
				// Queue up the reboot process
				this.getSyncUpdater().startRebootProcess();
			}
			
			// Updates were made
			return updates;
		}
		
		// No updates were made
		return updates;
	}
	
	public abstract void close();
}
