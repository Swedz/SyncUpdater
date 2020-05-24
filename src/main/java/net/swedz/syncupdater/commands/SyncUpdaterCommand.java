package net.swedz.syncupdater.commands;

import com.google.common.collect.Lists;
import net.swedz.syncupdater.SyncUpdater;
import net.swedz.syncupdater.update.redis.RedisUpdaterHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO i might want to redo this eventually, its kinda weird ngl
//  i might use my BukkitCommand wrapper, but idk yet
public class SyncUpdaterCommand implements CommandExecutor {
	private final SyncUpdater syncUpdater;
	
	public SyncUpdaterCommand(SyncUpdater syncUpdater) {
		this.syncUpdater = syncUpdater;
	}
	
	public SyncUpdater getSyncUpdater() {
		return syncUpdater;
	}
	
	public List<SyncUpdaterSubcommand> getSubcommands() {
		List<SyncUpdaterSubcommand> commands = Lists.newArrayList();
		
		commands.add(new HelpSubcommand(this));
		commands.add(new ReloadSubcommand(this));
		commands.add(new UpdateSubcommand(this));
		if(syncUpdater.getUpdaterHandler() instanceof RedisUpdaterHandler)
			commands.add(new PublishRedisUpdateSubcommand(this));
		
		return commands;
	}
	
	private SyncUpdaterSubcommand getSubcommand(String subcommand) {
		return this.getSubcommands().stream()
				.filter((cmd) -> cmd.getLabel().equalsIgnoreCase(subcommand))
				.findFirst().orElse(null);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// They gave no arguments
		if(args.length == 0) {
			sender.sendMessage(ChatColor.AQUA + "[SyncUpdater] " + ChatColor.WHITE + "Running version " + syncUpdater.getPlugin().getDescription().getVersion() + " by Swedz.");
		}
		
		// They are using arguments
		else {
			String subcommandArg = args[0];
			SyncUpdaterSubcommand subcommand = this.getSubcommand(subcommandArg);
			
			// There is no subcommand for that argument
			if(subcommand == null) {
				sender.sendMessage(ChatColor.RED + "Invalid arguments. For a list of valid subcommands use: /" + label + " help");
				return true;
			}
			
			// They don't have permission
			if(!subcommand.hasPermission(sender)) {
				String noPermissionMessage = syncUpdater.getConfig().getNoPermissionMessage();
				if(noPermissionMessage != null)
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermissionMessage));
				return true;
			}
			
			// Run the subcommand
			subcommand.onCommand(sender, Arrays.copyOfRange(args, 1, args.length));
		}
		
		return true;
	}
}
