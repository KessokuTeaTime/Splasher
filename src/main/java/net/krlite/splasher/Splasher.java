package net.krlite.splasher;

import net.fabricmc.api.ModInitializer;
import net.krlite.plumeconfig.base.ConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Splasher implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("splasher");
	public static final String MOD_ID = "splasher";
	public static final ConfigFile config = new ConfigFile(MOD_ID);

	@Override
	public void onInitialize() {
		SplasherConfig splasherConfig = config.loadAndSave(SplasherConfig.class);
	}
}
