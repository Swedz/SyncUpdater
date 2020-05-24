package net.swedz.syncupdater.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

class HelpSubcommand extends SyncUpdaterSubcommand {
	public HelpSubcommand(SyncUpdaterCommand command) {
		super(command, "help", "View all of the available subcommands.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		List<SyncUpdaterSubcommand> subcommands = this.getParentCommand().getSubcommands();
		sender.sendMessage(ChatColor.AQUA + "[SyncUpdater] " + ChatColor.WHITE + "Listing all available commands (" + subcommands.size() + "):");
		for(SyncUpdaterSubcommand subcommand : subcommands)
			sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + subcommand.getLabel() + ": " + subcommand.getDescription());
		return true;
	}
}
