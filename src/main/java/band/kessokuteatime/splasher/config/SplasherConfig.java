package band.kessokuteatime.splasher.config;

import band.kessokuteatime.splasher.Splasher;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import org.jetbrains.annotations.NotNull;

@Config(name = "splasher")
public class SplasherConfig implements ConfigData {
	public boolean splashTextsEnabled = true;
	public boolean festivalsEnabled = true;
	@ConfigEntry.Gui.Tooltip
	public boolean followsClientLanguage = false;
	public boolean debugInfoEnabled = false;

	@ConfigEntry.Category("texts")
	public boolean colorful = false;

	@ConfigEntry.Category("texts")
	public boolean lefty = false;

	@ConfigEntry.Category("texts")
	@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
	public RandomRate randomRate = RandomRate.ON_RELOAD_AND_CLICK;

	@ConfigEntry.Category("texts")
	@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
	public Source source = Source.VANILLA_AND_CUSTOM;

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
			return "config." + Splasher.ID + ".texts.source." + toString().toLowerCase();
		}
	}
}
