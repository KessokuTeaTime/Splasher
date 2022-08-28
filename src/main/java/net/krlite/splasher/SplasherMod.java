package net.krlite.splasher;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SplasherMod implements ClientModInitializer {
	public static final String MODID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger("Splasher");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Splash!");
	}
}
