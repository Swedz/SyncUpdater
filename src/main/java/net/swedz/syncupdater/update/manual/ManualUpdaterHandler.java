package net.swedz.syncupdater.update.manual;

import net.swedz.syncupdater.SyncUpdater;
import net.swedz.syncupdater.update.UpdaterHandler;

public class ManualUpdaterHandler extends UpdaterHandler {
	public ManualUpdaterHandler(SyncUpdater syncUpdater) {
		super(syncUpdater);
	}
	
	@Override
	public void close() {
		// Do nothing
	}
}
