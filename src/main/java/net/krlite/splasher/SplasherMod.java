package net.krlite.splasher;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplasherMod implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("splasher");

	public static final String MODID = "splasher";

	@Override
	public void onInitializeClient() {
		LOGGER.info("Translated splashes.txt to Client Language!");
	}
}
