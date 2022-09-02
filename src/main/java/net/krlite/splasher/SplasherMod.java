package net.krlite.splasher;

import net.fabricmc.api.ClientModInitializer;
import net.krlite.splasher.config.SplasherModConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplasherMod implements ClientModInitializer {
	public static final String MOD_ID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger("Splasher");

	@Override
	public void onInitializeClient() {
		SplasherModConfigs.registerConfigs();
	}
}
