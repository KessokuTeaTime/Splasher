package band.kessokuteatime.splasher.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "splasher")
public class SplasherConfig implements ConfigData {
	public boolean splashTextsEnabled = true;
	public boolean festivalsEnabled = true;
	public boolean followsClientLanguage = true;

	public boolean debugInfoEnabled = false;

	@ConfigEntry.Category("texts")
	public boolean colorful = false;

	@ConfigEntry.Category("texts")
	public boolean lefty = false;

	@ConfigEntry.Category("texts")
	public RandomRate randomRate = RandomRate.BOTH;

	@ConfigEntry.Category("texts")
	public Source source = Source.VANILLA;

	public enum RandomRate {
		NEVER(false, false, "never"),
		ON_RELOAD(true, false, "reload"),
		ON_CLICK(false, true, "click"),
		BOTH(true, true, "reload and click"),
		JEB(false, false, "jeb");

		private final boolean reload;
		private final boolean click;
		private final String name;

		RandomRate(boolean reload, boolean click, String name) {
			this.reload = reload;
			this.click = click;
			this.name = name;
		}

		public boolean onReload() {
			return reload;
		}
		public boolean onClick() {
			return click;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public enum Source {
		VANILLA(true, false, "vanilla"),
		CUSTOM(false, true, "custom"),
		BOTH(true, true, "vanilla and custom"),
		DEFAULT(false, false, "disabled");

		private final boolean vanilla;
		private final boolean custom;
		private final String name;

		Source(boolean vanilla, boolean custom, String name) {
			this.vanilla = vanilla;
			this.custom = custom;
			this.name = name;
		}

		public boolean vanilla() {
			return vanilla;
		}
		public boolean custom() {
			return custom;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
