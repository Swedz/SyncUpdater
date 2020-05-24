package net.swedz.syncupdater.update.redis;

import net.swedz.syncupdater.api.redis.RedisConnectionWrapper;
import net.swedz.syncupdater.SyncUpdater;
import net.swedz.syncupdater.update.UpdaterHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.json.JSONObject;

public class RedisUpdaterHandler extends UpdaterHandler {
	private RedisConnectionWrapper connection;
	
	public RedisUpdaterHandler(SyncUpdater syncUpdater) {
		super(syncUpdater);
		
		// Read the configuration options for the redis connection
		String hostname;
		int port;
		String password;
		try {
			ConfigurationSection redisSection = syncUpdater.getConfig().getConfiguration().getConfigurationSection("redis");
			hostname = redisSection.getString("hostname");
			port = redisSection.getInt("port");
			password = redisSection.getString("password");
		} catch (Exception ex) {
			Bukkit.getLogger().warning("[SyncUpdater] Failed to read configuration for section: 'redis'.");
			ex.printStackTrace();
			return;
		}
		
		// Establish the redis connection
		try {
			connection = new RedisConnectionWrapper(hostname, port, password, 10 * 1000);
			connection.registerListeners(new RedisUpdaterListener(this));
		} catch (Exception ex) {
			Bukkit.getLogger().severe("[SyncUpdater] Failed to establish connection to the redis database.");
			ex.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		connection.getJedisPool().close();
	}
	
	public RedisConnectionWrapper getConnection() {
		return connection;
	}
	
	public void publishRequestUpdate() {
		connection.asyncResourceCall((resource) ->
				resource.publish("syncupdater:requestupdate", new JSONObject().toString()));
	}
}
