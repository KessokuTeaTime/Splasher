package net.krlite.splasher;

import net.krlite.plumeconfig.annotation.Category;
import net.krlite.plumeconfig.annotation.Comment;
import net.krlite.plumeconfig.annotation.Option;
import net.krlite.plumeconfig.api.EnumLocalizable;
import net.krlite.plumeconfig.io.LineBreak;

public class SplasherConfig {
	public @Option(key = "enable_splash_texts") boolean enableSplashTexts = true;
	public @Option(key = "enable_festivals") boolean enableFestivals = true;
	public @Option(key = "follow_client_language") boolean followClientLanguage = true;
	public @Option(comment = "Make splash texts a little colorful") boolean colorful = false;

	/* Debug */
	@Category("debug")
	public @Option(key = "debug_info", comment = "Whether to show debug info") boolean debugInfo = true;

	/* Enum */
	@Category("enum")

	@Comment("Never  - Never reload splash texts")
	@Comment("Reload - Reload after reloading resources")
	@Comment("Click  - Reload when clicking on the splash text")
	@Comment(value = "Both   - Reload both at reloading and clicking", end = LineBreak.AFTER)

	@Option(key = "random_rate", name = "Controls the rate of randomization")
	public RandomRate randomRate = RandomRate.BOTH;

	@Category("enum")

	@Comment("Disabled - Disable splash texts")
	@Comment("Vanilla  - Show only vanilla splash texts")
	@Comment("Custom   - Show only custom splash texts")
	@Comment("Both     - Show both vanilla and custom splash texts")

	@Option(key = "splash_mode", name = "Controls the display contents")
	public SplashMode splashMode = SplashMode.BOTH;

	public enum RandomRate implements EnumLocalizable {
		NEVER(false, false, "Never"),
		BOTH(true, true, "Both"),
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
