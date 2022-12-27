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
	public @Option(key = "disable_debug_info", comment = "Debug info will not be logged if true") boolean disableDebugInfo = false;

	/* Enum */
	@Category("enum")

	@Comment("Never  - Never show random splash texts")
	@Comment("Reload - Refresh after reloading resources")
	@Comment("Click  - Refresh when clicking the splash text")
	@Comment(value = "Both   - Refresh splash texts when reloading and clicking", newLine = LineBreak.AFTER)

	@Option(key = "random_rate", name = "Splash Texts Refresh Rate")
	public RandomRate randomRate = RandomRate.BOTH;

	@Category("enum")

	@Comment("Disabled - Disable splash texts, only show debug info")
	@Comment("Vanilla  - Only show vanilla splash texts")
	@Comment("Custom   - Only show custom splash texts")
	@Comment("Both     - Show both vanilla and custom splash texts")

	@Option(key = "splash_mode", name = "Splash Texts Display Mode")
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
