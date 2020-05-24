package net.swedz.syncupdater.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class ReloadSubcommand extends SyncUpdaterSubcommand {
	public ReloadSubcommand(SyncUpdaterCommand command) {
		super(command, "reload", "Re-load the configuration file.",
				"syncupdater.reload");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		this.getParentCommand().getSyncUpdater().reload();
		sender.sendMessage(ChatColor.AQUA + "[SyncUpdater] " + ChatColor.WHITE + "Reloaded the configuration file.");
		return true;
	}
}
