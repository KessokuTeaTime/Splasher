package band.kessokuteatime.splasher.supplier;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.base.FormattingType;
import band.kessokuteatime.splasher.config.SplasherWithPickle;
import net.fabricmc.loader.api.FabricLoader;
import band.kessokuteatime.splasher.loader.SplashTextLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.minecraft.text.Text;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.*;

public class SplashTextSupplier {
	private static int lastRandomIndex = -1;

	@Nullable public static String getSplashes(Session session, List<String> splashTexts) {
		Path path = FabricLoader.getInstance().getConfigDir().resolve(Splasher.ID);
		
		if (SplasherWithPickle.get().texts.colorful) {
			double formatting = new Random().nextDouble(1);
			Splasher.updateFormatting(FormattingType.getFormatting(formatting), new Random().nextInt(0xFFFFFF));
		}

		String language = !SplasherWithPickle.get().followsClientLanguage ? "en_us" : MinecraftClient.getInstance().getLanguageManager().getLanguage();
		List<String> customSplashTexts = Lists.newArrayList();

		if (SplasherWithPickle.get().texts.source.vanilla()) customSplashTexts.addAll(splashTexts);
		if (SplasherWithPickle.get().texts.source.custom()) customSplashTexts.addAll(new SplashTextLoader(path.resolve(language + ".txt").toFile()).load());

		if (customSplashTexts.isEmpty()) {
			if (SplasherWithPickle.get().texts.source.vanilla()){
				Splasher.LOGGER.warn("Minecraft has no splash loaded. Check your data as if it may be broken.");
			}
			Splasher.LOGGER.error("Empty stack!");
			return null;
		}

		final int random = nextRandomIndex(customSplashTexts.size());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		if (SplasherWithPickle.get().festivalsEnabled) {
			if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) == 24) {
				return getXmasSplash(SplasherWithPickle.get().followsClientLanguage);
			}

			if (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 1) {
				return getNewYearSplash(SplasherWithPickle.get().followsClientLanguage);
			}

			if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 31) {
				return getHalloweenSplash(SplasherWithPickle.get().followsClientLanguage);
			}

			if (session != null && random == 42) {
				return getPlayerSplash(SplasherWithPickle.get().followsClientLanguage, session.getUsername().toUpperCase(Locale.ROOT));
			}
		}

		if (SplasherWithPickle.get().texts.source.vanilla() && random <= splashTexts.size()) {
			if (SplasherWithPickle.get().followsClientLanguage) return Text.translatable("splash.minecraft." + random).getString();
			else return customSplashTexts.get(random);
		}

		if (SplasherWithPickle.get().texts.source.custom()) return customSplashTexts.get(random);

		return null;
	}

	private static int nextRandomIndex(int size) {
		if (size < 0) return -1;
		if (size == 1) return 0;
		int index = new Random().nextInt(size);

		if (index == lastRandomIndex) return nextRandomIndex(size);
		return lastRandomIndex = index;
	}

	private static String getXmasSplash(boolean translate) {
		if (translate) return Text.translatable("festival." + Splasher.ID + ".x_mas").getString();
		else return "Merry X-mas!";
	}

	private static String getNewYearSplash(boolean translate) {
		if (translate) return Text.translatable("festival." + Splasher.ID + ".new_year").getString();
		else return "Happy new year!";
	}

	private static String getHalloweenSplash(boolean translate) {
		if (translate) return Text.translatable("festival." + Splasher.ID + ".halloween").getString();
		else return "OOoooOOOoooo! Spooky!";
	}

	private static String getPlayerSplash(boolean translate, String playerName) {
		if (translate) return Text.translatable("festival." + Splasher.ID + ".is_you", playerName).getString();
		else return playerName + " IS YOU";
	}
}
