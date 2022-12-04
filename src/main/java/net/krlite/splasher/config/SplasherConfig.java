package net.krlite.splasher.config;

import com.google.gson.JsonObject;
import net.krlite.plumeconfig.config.PlumeConfig;
import net.krlite.plumeconfig.config.api.PlumeConfigApi;
import net.krlite.plumeconfig.option.OptionBoolean;
import net.krlite.plumeconfig.option.OptionEnumLocalized;
import net.krlite.plumeconfig.option.core.ILocalizable;

public class SplasherConfig implements PlumeConfigApi {
	public static final PlumeConfig config = new PlumeConfig("splasher", "config");
	public static final OptionBoolean ENABLE_SPLASH_TEXTS = new OptionBoolean("enable_splash_texts", true);
	public static final OptionBoolean ENABLE_FESTIVALS = new OptionBoolean("enable_festivals", true);
	public static final OptionBoolean FOLLOW_CLIENT_LANGUAGE = new OptionBoolean("follow_client_language", true);
	public static final OptionBoolean DISABLE_DEBUG_INFO = new OptionBoolean("disable_debug_info", false);
	public static final OptionEnumLocalized<RandomRate> RANDOM_RATE = new OptionEnumLocalized<>("random_rate", RandomRate.RELOAD_CLICK);
	public static final OptionEnumLocalized<SplashMode> SPLASH_MODE = new OptionEnumLocalized<>("splash_mode", SplashMode.BOTH);

	public static void readConfig(JsonObject configs) {
		ENABLE_SPLASH_TEXTS.parse(configs);
		ENABLE_FESTIVALS.parse(configs);
		FOLLOW_CLIENT_LANGUAGE.parse(configs);
		DISABLE_DEBUG_INFO.parse(configs);
		RANDOM_RATE.parse(configs);
		SPLASH_MODE.parse(configs);
	}

	public static void writeConfig() {
		config.write(
				ENABLE_SPLASH_TEXTS,
				ENABLE_FESTIVALS,
				FOLLOW_CLIENT_LANGUAGE,
				DISABLE_DEBUG_INFO,
				RANDOM_RATE,
				SPLASH_MODE
		);
	}

	@Override
	public void onInitialize() {
		readConfig(config.read());
		writeConfig();
	}

	public enum RandomRate implements ILocalizable {
		NEVER(false, false, "never"),
		RELOAD_CLICK(true, true, "reload_click"),
		ON_RELOAD(true, false, "reload"),
		ON_CLICK(false, true, "click"),
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

	public enum SplashMode implements ILocalizable {
		VANILLA(true, false, "vanilla"),
		BOTH(true, true, "both"),
		CUSTOM(false, true, "custom"),
		DEFAULT(false, false, "disabled");

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
