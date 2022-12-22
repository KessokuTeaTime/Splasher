package net.krlite.splasher.supplier;

import net.fabricmc.loader.api.FabricLoader;
import net.krlite.splasher.Splasher;
import net.krlite.splasher.SplasherConfig;
import net.krlite.splasher.loader.SplashTextLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.minecraft.text.Text;
import org.apache.commons.compress.utils.Lists;

import java.nio.file.Path;
import java.util.*;

public class SplashTextSupplier {
	public String getSplashes(Session session, List<String> splashTexts) {
		Path path = FabricLoader.getInstance().getConfigDir().resolve(Splasher.MOD_ID);
		SplasherConfig splasherConfig = Splasher.config.load(SplasherConfig.class);

		String language = !splasherConfig.followClientLanguage ? "en_us" : MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
		List<String> customSplashTexts = Lists.newArrayList();

		if (splasherConfig.splashMode.isVanilla()) customSplashTexts.addAll(splashTexts);
		if (splasherConfig.splashMode.isCustom()) customSplashTexts.addAll(new SplashTextLoader(path.resolve(language + ".txt").toFile()).load());

		if (customSplashTexts.isEmpty()) {
			if (splasherConfig.splashMode.isVanilla()){
				Splasher.LOGGER.warn("Minecraft has no splash loaded. Check your data as if it may be broken.");
			}
			Splasher.LOGGER.error("Empty stack!");
			return null;
		}
		final int random = new Random().nextInt(customSplashTexts.size());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());



		//LOGGER.warn(customSplashTexts.toString());

		if (splasherConfig.enableFestivals) {
			if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) == 24) {
				return getXmasSplash(splasherConfig.followClientLanguage);
			}

			if (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 1) {
				return getNewYearSplash(splasherConfig.followClientLanguage);
			}

			if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 31) {
				return getHalloweenSplash(splasherConfig.followClientLanguage);
			}

			if (session != null && random == 42) {
				return session.getUsername().toUpperCase(Locale.ROOT) + getPlayerSplash(splasherConfig.followClientLanguage);
			}
		}

		if (splasherConfig.splashMode.isVanilla() && random <= splashTexts.size()) {
			if (splasherConfig.followClientLanguage) return Text.translatable("splash.minecraft." + random).getString();
			else return customSplashTexts.get(random);
		}

		if (splasherConfig.splashMode.isCustom()) return customSplashTexts.get(random);

		return null;
	}

	private String getXmasSplash(boolean translate) {
		if (translate) return Text.translatable("festival." + Splasher.MOD_ID + ".x_mas").getString();
		else return "Merry X-mas!";
	}

	private String getNewYearSplash(boolean translate) {
		if (translate) return Text.translatable("festival." + Splasher.MOD_ID + ".new_year").getString();
		else return "Happy new year!";
	}

	private String getHalloweenSplash(boolean translate) {
		if (translate) return Text.translatable("festival." + Splasher.MOD_ID + ".halloween").getString();
		else return "OOoooOOOoooo! Spooky!";
	}

	private String getPlayerSplash(boolean translate) {
		if (translate) return Text.translatable("festival." + Splasher.MOD_ID + ".is_you").getString();
		else return " IS YOU";
	}
}
