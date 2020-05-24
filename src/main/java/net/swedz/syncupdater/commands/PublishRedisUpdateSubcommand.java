package net.swedz.syncupdater.commands;

import net.swedz.syncupdater.update.redis.RedisUpdaterHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class PublishRedisUpdateSubcommand extends SyncUpdaterSubcommand {
	public PublishRedisUpdateSubcommand(SyncUpdaterCommand command) {
		super(command, "publishredisupdate", "Publish an update request to redis for all linked servers to update.",
				"syncupdater.update");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		((RedisUpdaterHandler) this.getParentCommand().getSyncUpdater().getUpdaterHandler()).publishRequestUpdate();
		sender.sendMessage(ChatColor.AQUA + "[SyncUpdater] " + ChatColor.WHITE + "Published redis update request to all linked servers.");
		return true;
	}
}
