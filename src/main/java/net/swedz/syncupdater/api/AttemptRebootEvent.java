package net.swedz.syncupdater.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AttemptRebootEvent extends Event implements Cancellable {
	private boolean cancelled;
	
	public AttemptRebootEvent() {
		this.cancelled = false;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	/*
	 * Internal handling for bukkit-end stuff
	 */
	private static final HandlerList HANDLERS = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}