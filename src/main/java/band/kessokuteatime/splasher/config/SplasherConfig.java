package band.kessokuteatime.splasher.config;

import band.kessokuteatime.splasher.Splasher;
import com.electronwill.nightconfig.core.serde.annotations.SerdeComment;
import com.electronwill.nightconfig.core.serde.annotations.SerdeDefault;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(name = "splasher")
public class SplasherConfig implements ConfigData {
	private transient final Supplier<Boolean> splashTextsEnabledProvider = () -> true;

	@SerdeDefault(provider = "splashTextsEnabledProvider")
	@SerdeComment(" Whether to show splash texts or not.")
	public boolean splashTextsEnabled = splashTextsEnabledProvider.get();

	private transient final Supplier<Boolean> festivalsEnabledProvider = () -> true;

	@SerdeDefault(provider = "festivalsEnabledProvider")
	@SerdeComment(" Whether to enable special splash texts on festivals or not.")
	public boolean festivalsEnabled = festivalsEnabledProvider.get();

	private transient final Supplier<Boolean> followsClientLanguageProvider = () -> false;

	@SerdeDefault(provider = "followsClientLanguageProvider")
	@SerdeComment(" Whether to localize the splash texts according to the client language or not.")
	@ConfigEntry.Gui.Tooltip
	public boolean followsClientLanguage = followsClientLanguageProvider.get();

	private transient final Supplier<Boolean> debugInfoEnabledProvider = () -> false;

	@SerdeDefault(provider = "debugInfoEnabledProvider")
	@SerdeComment(" Whether to print the verbose debug info or not.")
	public boolean debugInfoEnabled = debugInfoEnabledProvider.get();

	private transient final Supplier<Texts> textsProvider = Texts::new;

	@SerdeDefault(provider = "textsProvider")
	@ConfigEntry.Gui.TransitiveObject
	@ConfigEntry.Category("texts")
	public Texts texts = textsProvider.get();

	public static class Texts {
		private transient final Supplier<Boolean> colorfulProvider = () -> false;

		@SerdeDefault(provider = "colorfulProvider")
		@SerdeComment(" Whether to randomly tint and style the splash texts on reload or not.")
		public boolean colorful = colorfulProvider.get();

		private transient final Supplier<Boolean> leftyProvider = () -> false;

		@SerdeDefault(provider = "leftyProvider")
		@SerdeComment(" Whether to flip the splash texts to the left not.")
		public boolean lefty = leftyProvider.get();

		private transient final Supplier<RandomRate> randomRateProvider = () -> RandomRate.ON_RELOAD_AND_CLICK;

		@SerdeDefault(provider = "randomRateProvider")
		@SerdeComment(" Controls the rate and chance of splash texts to reload.")

		@SerdeComment(" Acceptable values:")
		@SerdeComment(" - NEVER: never reloads.")
		@SerdeComment(" - ON_RELOAD: reloads on title screen reinitialization.")
		@SerdeComment(" - ON_CLICK: reloads on click on the text.")
		@SerdeComment(" - ON_RELOAD_AND_CLICK: reloads on both title screen reinitialization and click on the text.")

		@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
		public RandomRate randomRate = randomRateProvider.get();

		private transient final Supplier<Source> sourceProvider = () -> Source.VANILLA_AND_CUSTOM;

		@SerdeDefault(provider = "sourceProvider")
		@SerdeComment(" Controls the source of splash texts.")

		@SerdeComment(" Acceptable values:")
		@SerdeComment(" - VANILLA: only use the builtin vanilla splash texts.")
		@SerdeComment(" - CUSTOM: only use the custom splash texts under the config directory.")
		@SerdeComment(" - VANILLA_AND_CUSTOM: use both the builtin vanilla and custom splash texts.")
		@SerdeComment(" - NONE: no splash texts available.")

		@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
		public Source source = sourceProvider.get();
	}

	public enum RandomRate implements SelectionListEntry.Translatable {
		NEVER(false, false),
		ON_RELOAD(true, false),
		ON_CLICK(false, true),
		ON_RELOAD_AND_CLICK(true, true),
		JEB(false, false);

		private final boolean reload;
		private final boolean click;

		RandomRate(boolean reload, boolean click) {
			this.reload = reload;
			this.click = click;
		}

		public boolean onReload() {
			return reload;
		}
		public boolean onClick() {
			return click;
		}

		@Override
		public @NotNull String getKey() {
			return "config." + Splasher.ID + ".texts.random_rate." + toString().toLowerCase();
		}
	}

	public enum Source implements SelectionListEntry.Translatable {
		VANILLA(true, false),
		CUSTOM(false, true),
		VANILLA_AND_CUSTOM(true, true),
		NONE(false, false);

		private final boolean vanilla;
		private final boolean custom;

		Source(boolean vanilla, boolean custom) {
			this.vanilla = vanilla;
			this.custom = custom;
		}

		public boolean vanilla() {
			return vanilla;
		}
		public boolean custom() {
			return custom;
		}

		@Override
		public @NotNull String getKey() {
			return String.join(".", "config", Splasher.ID, "texts", "source", toString().toLowerCase());
		}
	}
}
