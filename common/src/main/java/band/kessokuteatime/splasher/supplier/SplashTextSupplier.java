package band.kessokuteatime.splasher.supplier;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.config.SplasherConfig;
import band.kessokuteatime.splasher.loader.SplashTextLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SpecialDates;

import java.nio.file.Path;
import java.time.MonthDay;
import java.util.List;
import java.util.Locale;

public final class SplashTextSupplier {
	private static final RandomSource RANDOM = RandomSource.createThreadLocalInstance();
	private static int lastRandomIndex = -1;

	private SplashTextSupplier() {
	}

	public static Component getSplash(User user, List<Component> vanillaSplashes) {
		SplasherConfig config = Splasher.config();
		SplasherConfig.Source source = config.texts.source;

		List<Component> vanilla = source.vanilla() ? vanillaSplashes : List.of();
		List<String> custom = source.custom() ? loadCustomSplashes(config) : List.of();
		int poolSize = vanilla.size() + custom.size();
		if (poolSize == 0) {
			if (shouldLogDebug(config)) {
				Splasher.LOGGER.warn("No splash texts are available for source {}.", source);
			}
			return null;
		}

		int randomIndex = nextRandomIndex(poolSize);
		if (config.festivalsEnabled) {
			Component specialSplash = getSpecialSplash(user, randomIndex, config.followsClientLanguage);
			if (specialSplash != null) {
				return specialSplash;
			}
		}

		if (randomIndex < vanilla.size()) {
			if (config.followsClientLanguage) {
				return Splasher.createTranslatedSplash("splash.minecraft." + randomIndex);
			}
			return Splasher.formatSplash(vanilla.get(randomIndex));
		}

		return Splasher.createLiteralSplash(custom.get(randomIndex - vanilla.size()));
	}

	private static List<String> loadCustomSplashes(SplasherConfig config) {
		Minecraft minecraft = Minecraft.getInstance();
		String language = config.followsClientLanguage
				? minecraft.getLanguageManager().getSelected()
				: "en_us";
		Path path = minecraft.gameDirectory.toPath()
				.resolve("config")
				.resolve(Splasher.ID)
				.resolve(language + ".txt");
		return new SplashTextLoader(path).load();
	}

	private static Component getSpecialSplash(User user, int randomIndex, boolean translate) {
		MonthDay today = SpecialDates.dayNow();
		if (today.equals(SpecialDates.CHRISTMAS)) {
			return special("festival.splasher.x_mas", "Merry X-mas!", translate);
		}
		if (today.equals(SpecialDates.NEW_YEAR)) {
			return special("festival.splasher.new_year", "Happy new year!", translate);
		}
		if (today.equals(SpecialDates.HALLOWEEN)) {
			return special("festival.splasher.halloween", "OOoooOOOoooo! Spooky!", translate);
		}
		if (user != null && randomIndex == 42) {
			String playerName = user.getName().toUpperCase(Locale.ROOT);
			return translate
					? Splasher.createTranslatedSplash("festival.splasher.is_you", playerName)
					: Splasher.createLiteralSplash(playerName + " IS YOU");
		}
		return null;
	}

	private static Component special(String key, String fallback, boolean translate) {
		return translate
				? Splasher.createTranslatedSplash(key)
				: Splasher.createLiteralSplash(fallback);
	}

	private static int nextRandomIndex(int size) {
		if (size == 1) {
			lastRandomIndex = 0;
			return 0;
		}

		int index = RANDOM.nextInt(size);
		if (index == lastRandomIndex) {
			index = (index + 1 + RANDOM.nextInt(size - 1)) % size;
		}
		lastRandomIndex = index;
		return index;
	}

	private static boolean shouldLogDebug(SplasherConfig config) {
		return config.debugInfoEnabled && config.texts.randomRate != SplasherConfig.RandomRate.JEB;
	}
}
