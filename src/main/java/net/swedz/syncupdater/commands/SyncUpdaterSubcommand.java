package net.swedz.syncupdater.commands;

import org.bukkit.command.CommandSender;

abstract class SyncUpdaterSubcommand {
	private final SyncUpdaterCommand command;
	private final String             label, description, permission;
	
	public SyncUpdaterSubcommand(SyncUpdaterCommand command, String label, String description, String permission) {
		this.command = command;
		this.label = label;
		this.description = description;
		this.permission = permission;
	}
	
	public SyncUpdaterSubcommand(SyncUpdaterCommand command, String label, String description) {
		this(command, label, description, null);
	}
	
	public SyncUpdaterCommand getParentCommand() {
		return command;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public boolean hasPermission(CommandSender sender) {
		return permission == null || sender.hasPermission(permission);
	}
	
	public String getUsage() {
		return "/syncupdater " + this.getLabel();
	}
	
	public abstract boolean onCommand(CommandSender sender, String[] args);
}
