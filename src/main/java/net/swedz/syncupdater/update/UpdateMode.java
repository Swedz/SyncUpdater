package net.swedz.syncupdater.update;

import net.swedz.syncupdater.SyncUpdater;
import net.swedz.syncupdater.update.auto.AutomaticUpdaterHandler;
import net.swedz.syncupdater.update.manual.ManualUpdaterHandler;
import net.swedz.syncupdater.update.redis.RedisUpdaterHandler;

public enum UpdateMode {
	AUTOMATIC {
		@Override
		public UpdaterHandler getHandler(SyncUpdater syncUpdater) {
			return new AutomaticUpdaterHandler(syncUpdater);
		}
	},
	REDIS {
		@Override
		public UpdaterHandler getHandler(SyncUpdater syncUpdater) {
			return new RedisUpdaterHandler(syncUpdater);
		}
	},
	MANUAL {
		@Override
		public UpdaterHandler getHandler(SyncUpdater syncUpdater) {
			return new ManualUpdaterHandler(syncUpdater);
		}
	};
	
	public UpdaterHandler getHandler(SyncUpdater syncUpdater) {
		throw new IllegalStateException("Make sure you override getHandler() in UpdateMode!");
	}
}
