package band.kessokuteatime.splasher.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

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
	public Source source = Source.VANILLA;

	public enum RandomRate {
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
	}

	public enum Source {
		VANILLA(true, false),
		CUSTOM(false, true),
		VANILLA_AND_CUSTOM(true, true),
		DEFAULT(false, false);

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
	}
}
