package net.krlite.splasher.config;

import com.google.gson.JsonObject;
import net.krlite.plumeconfig.config.PlumeConfig;
import net.krlite.plumeconfig.config.api.PlumeConfigApi;
import net.krlite.plumeconfig.option.OptionBoolean;
import net.krlite.plumeconfig.option.OptionEnum;

public class SplasherConfig implements PlumeConfigApi {
	public static final PlumeConfig config = new PlumeConfig("splasher", "config");
	public static final OptionBoolean ENABLE_SPLASH_TEXTS = new OptionBoolean("enable_splash_texts", true);
	public static final OptionBoolean ENABLE_FESTIVALS = new OptionBoolean("enable_festivals", true);
	public static final OptionBoolean FOLLOW_CLIENT_LANGUAGE = new OptionBoolean("follow_client_language", true);
	public static final OptionBoolean DISABLE_DEBUG_INFO = new OptionBoolean("disable_debug_info", false);
	public static final OptionEnum RANDOM_RATE = new OptionEnum("random_rate", RandomRate.RELOAD_CLICK);
	public static final OptionEnum SPLASH_MODE = new OptionEnum("splash_mode", SplashMode.BOTH);

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

	public enum RandomRate {
		NEVER(false, false),
		RELOAD_CLICK(true, true),
		ON_RELOAD(true, false),
		ON_CLICK(false, true),
		Jens_Bergensten(false, false);

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

	public enum SplashMode {
		VANILLA(true, false),
		BOTH(true, true),
		CUSTOM(false, true),
		DEFAULT(false, false);

		private final boolean vanilla;
		private final boolean custom;

		SplashMode(boolean vanilla, boolean custom) {
			this.vanilla = vanilla;
			this.custom = custom;
		}

		public boolean isVanilla() {
			return vanilla;
		}
		public boolean isCustom() {
			return custom;
		}
	}
}
