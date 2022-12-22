package net.krlite.splasher;

import net.fabricmc.api.ModInitializer;
import net.krlite.plumeconfig.base.ConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Splasher implements ModInitializer {
	public static final String MOD_ID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ConfigFile config = new ConfigFile(MOD_ID);

	private static boolean shouldReloadSplashText;

	@Override
	public void onInitialize() {
		shouldReloadSplashText = (config.loadAndSave(SplasherConfig.class).randomRate == SplasherConfig.RandomRate.JEB);
	}

	public static boolean shouldReloadSplashText() {
		return shouldReloadSplashText;
	}

	public static void reloadSplashText() {
		shouldReloadSplashText = true;
	}

	public static void keepSplashText() {
		shouldReloadSplashText = config.load(SplasherConfig.class).randomRate == SplasherConfig.RandomRate.JEB;
	}
}
