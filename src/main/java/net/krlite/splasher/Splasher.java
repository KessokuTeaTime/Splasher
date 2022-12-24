package net.krlite.splasher;

import net.fabricmc.api.ModInitializer;
import net.krlite.plumeconfig.base.ConfigFile;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;

public class Splasher implements ModInitializer {
	public static final String MOD_ID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ConfigFile config = new ConfigFile(MOD_ID);

	private static boolean shouldReloadSplashText;
	private static final ArrayList<Formatting> formattings = new ArrayList<>();
	private static int color = 0xFFFF00;

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

	public static void updateFormatting(ArrayList<Formatting> formattings, int color) {
		if (config.load(SplasherConfig.class).colorful) {
			Splasher.color = color;
			if (formattings != null) {
				Splasher.formattings.clear();
				Splasher.formattings.addAll(formattings.stream().filter(Objects::nonNull).distinct().collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
			}
		}
	}

	public static Text getFormattedSplashText(String text) {
		MutableText splashText = Text.literal(text);
		formattings.forEach(splashText::formatted);
		return splashText;
	}

	public static int getColor() {
		return color;
	}
}
