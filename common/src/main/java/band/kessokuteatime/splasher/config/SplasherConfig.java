package band.kessokuteatime.splasher.config;

import band.kessokuteatime.splasher.Splasher;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;

import java.util.Locale;

@Config(name = Splasher.ID)
public final class SplasherConfig implements ConfigData {
	public boolean splashTextsEnabled = true;
	public boolean festivalsEnabled = true;

	@ConfigEntry.Gui.Tooltip
	public boolean followsClientLanguage;

	public boolean debugInfoEnabled;

	@ConfigEntry.Gui.TransitiveObject
	@ConfigEntry.Category("texts")
	public Texts texts = new Texts();

	@Override
	public void validatePostLoad() throws ValidationException {
		if (texts == null) {
			throw new ValidationException("texts must be present");
		}
		if (texts.randomRate == null) {
			throw new ValidationException("texts.randomRate must be present");
		}
		if (texts.source == null) {
			throw new ValidationException("texts.source must be present");
		}
	}

	public static final class Texts {
		public boolean colorful;
		public boolean lefty;

		@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
		public RandomRate randomRate = RandomRate.ON_RELOAD_AND_CLICK;

		@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
		public Source source = Source.VANILLA_AND_CUSTOM;
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
		public String getKey() {
			return "config." + Splasher.ID + ".texts.random_rate." + name().toLowerCase(Locale.ROOT);
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
		public String getKey() {
			return "config." + Splasher.ID + ".texts.source." + name().toLowerCase(Locale.ROOT);
		}
	}
}
