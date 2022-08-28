package net.krlite.splasher;

import com.google.common.collect.Lists;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.util.Session;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SplasherMod implements ClientModInitializer {
	public static final String MODID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger("splasher");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Splash!");
	}
}
