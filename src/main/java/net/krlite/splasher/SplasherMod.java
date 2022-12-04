package net.krlite.splasher;

import net.fabricmc.api.ClientModInitializer;
import net.krlite.splasher.config.SplasherConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplasherMod implements ClientModInitializer {
	public static final String MOD_ID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger("Splasher");
	private static boolean shouldReloadSplashText = (SplasherConfig.RANDOM_RATE.getValue() == SplasherConfig.RandomRate.JEB);

	public static boolean shouldReloadSplashText() {
		return shouldReloadSplashText;
	}

	public static void reloadSplashText() {
		shouldReloadSplashText = true;
	}

	public static void keepSplashText() {
		shouldReloadSplashText = (SplasherConfig.RANDOM_RATE.getValue() == SplasherConfig.RandomRate.JEB);
	}

	@Override
	public void onInitializeClient() {
	}
}
