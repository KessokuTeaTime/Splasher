package net.krlite.splasher;

import net.krlite.plumeconfig.annotation.Category;
import net.krlite.plumeconfig.annotation.Option;
import net.krlite.plumeconfig.api.EnumLocalizable;

public class SplasherConfig {
	public @Option(key = "enable_splash_texts") boolean enableSplashTexts = true;
	public @Option(key = "enable_festivals") boolean enableFestivals = true;
	public @Option(key = "follow_client_language") boolean followClientLanguage = true;

	@Category("debug")
	public @Option(key = "disable_debug_info", comment = "Debug info will not be logged if disabled") boolean disableDebugInfo = false;

	@Category("enum")
	@Option(key = "random_rate", comment = "Never, Reload, Click, Reload or Click")
	public RandomRate randomRate = RandomRate.RELOAD_CLICK;
	@Category("enum")
	@Option(key = "splash_mode", comment = "Disabled, Vanilla, Custom, Both")
	public SplashMode splashMode = SplashMode.BOTH;

	public enum RandomRate implements EnumLocalizable {
		NEVER(false, false, "Never"),
		RELOAD_CLICK(true, true, "Reload or Click"),
		ON_RELOAD(true, false, "Reload"),
		ON_CLICK(false, true, "Click"),
		JEB(false, false, "Jens Bergensten");

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
		public String getLocalizedName() {
			return name;
		}
	}

	public enum SplashMode implements EnumLocalizable {
		VANILLA(true, false, "Vanilla"),
		BOTH(true, true, "Both"),
		CUSTOM(false, true, "Custom"),
		DEFAULT(false, false, "Disabled");

		private final boolean vanilla;
		private final boolean custom;
		private final String name;

		SplashMode(boolean vanilla, boolean custom, String name) {
			this.vanilla = vanilla;
			this.custom = custom;
			this.name = name;
		}

		public boolean isVanilla() {
			return vanilla;
		}
		public boolean isCustom() {
			return custom;
		}

		@Override
		public String getLocalizedName() {
			return name;
		}
	}
}
