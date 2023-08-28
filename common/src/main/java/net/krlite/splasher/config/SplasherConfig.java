package net.krlite.splasher.config;

import dev.architectury.platform.Platform;
import net.krlite.pierced.annotation.Comment;
import net.krlite.pierced.annotation.Silent;
import net.krlite.pierced.annotation.Table;
import net.krlite.pierced.config.Pierced;
import net.krlite.pierced.core.EnumLocalizable;
import net.krlite.splasher.Splasher;

import java.io.File;

public class SplasherConfig extends Pierced {
	@Silent
	private static final File file = Platform.getConfigFolder().resolve(Splasher.ID + ".toml").toFile();

	public SplasherConfig() {
		super(SplasherConfig.class, file);
		load();
	}

	public boolean enableSplashTexts = true;
	public boolean enableFestivals = true;
	public boolean followClientLanguage = true;

	/* Debug */
	@Table("debug")
	@Comment("Show debug info")
	public boolean debugInfo = false;

	/* Splash */
	@Table("splash")
	@Comment("Make splash texts a little colorful")
	public boolean colorful = false;

	@Table("splash")
	@Comment("Moves splash texts to the left")
	public boolean lefty = false;

	@Table("splash")
	@Comment("Controls the splash text random rate")
	@Comment
	@Comment("Never  - Never reload splash texts")
	@Comment("Reload - Reload after reloading resources")
	@Comment("Click  - Reload when clicking on the splash text")
	@Comment("Both   - Reload both at reloading and clicking")
	public RandomRate randomRate = RandomRate.BOTH;

	@Table("splash")

	@Comment("Controls the splash text contents")
	@Comment
	@Comment("Disabled - Disable splash texts")
	@Comment("Vanilla  - Show only vanilla splash texts")
	@Comment("Custom   - Show only custom splash texts")
	@Comment("Both     - Show both vanilla and custom splash texts")
	public SplashMode splashMode = SplashMode.VANILLA;

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
