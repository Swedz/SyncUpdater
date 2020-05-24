package net.swedz.syncupdater.update.redis;

import net.swedz.syncupdater.api.redis.RedisListener;
import net.swedz.syncupdater.api.redis.RedisSubscriber;
import org.json.JSONObject;

@RedisSubscriber(channels = {"syncupdater:requestupdate"})
public class RedisUpdaterListener implements RedisListener {
	private final RedisUpdaterHandler handler;
	
	RedisUpdaterListener(RedisUpdaterHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void onRedisMessageReceived(String channel, JSONObject message) {
		handler.performFullUpdate();
	}
}
