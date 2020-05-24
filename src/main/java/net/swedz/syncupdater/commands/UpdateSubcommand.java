package net.swedz.syncupdater.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class UpdateSubcommand extends SyncUpdaterSubcommand {
	public UpdateSubcommand(SyncUpdaterCommand command) {
		super(command, "update", "Run an update check for all plugins.",
				"syncupdater.update");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		int updates = this.getParentCommand().getSyncUpdater().getUpdaterHandler().performFullUpdate();
		sender.sendMessage(ChatColor.AQUA + "[SyncUpdater] " + ChatColor.WHITE +
				(updates > 0 ?
						("Found " + updates + " updates and installed them to the plugin directory.") :
						("There were no updates found!")));
		return true;
	}
}
