package net.krlite.splasher.config;

import com.google.gson.annotations.SerializedName;
import com.mojang.datafixers.util.Pair;
import net.krlite.splasher.SplasherMod;

public class SplasherModConfigs {
    public static SplasherSimpleConfig CONFIG;
    public static boolean jeb = false;
    public static boolean shouldReloadSplashText = false;
    private static SplasherModConfigProvider configs;
    public enum RandomRate {
        NEVER(false, false),
        RELOAD_CLICK(true, true),
        ON_RELOAD(true, false),
        ON_CLICK(false, true);

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

    public static boolean ENABLE_SPLASH_TEXTS;
    public static boolean ENABLE_FESTIVALS;
    public static boolean FOLLOW_CLIENT_LANGUAGE;
    public static RandomRate RANDOM_RATE = RandomRate.ON_RELOAD;
    public static SplashMode SPLASH_MODE = SplashMode.DEFAULT;

    public static void registerConfigs() {
        configs = new SplasherModConfigProvider();

        createConfigs();

        CONFIG = SplasherSimpleConfig.of(SplasherMod.MOD_ID).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("enable_splash_texts", true), "true/false");
        configs.addKeyValuePair(new Pair<>("enable_festivals", true), "true/false");
        configs.addKeyValuePair(new Pair<>("follow_client_language", true), "true/false");
        configs.addKeyValuePair(new Pair<>("random_rate",RandomRate.ON_RELOAD.name()), "NEVER/ON_RELOAD/ON_CLICK/RELOAD_CLICK");
        configs.addKeyValuePair(new Pair<>("splash_mode", SplashMode.BOTH.name()), "VANILLA/BOTH/CUSTOM");
    }

    private static void assignConfigs() {
        String randomRate, splashMode;

        ENABLE_SPLASH_TEXTS = CONFIG.getOrDefault("enable_splash_texts", true);
        ENABLE_FESTIVALS = CONFIG.getOrDefault("enable_festivals", true);
        FOLLOW_CLIENT_LANGUAGE = CONFIG.getOrDefault("follow_client_language", true);
        randomRate = CONFIG.getOrDefault("random_rate", RandomRate.ON_RELOAD.name());
        splashMode = CONFIG.getOrDefault("splash_mode", SplashMode.BOTH.name());

        if ( randomRate.equals(RandomRate.NEVER.name()) ) RANDOM_RATE = RandomRate.NEVER;
        if ( randomRate.equals(RandomRate.RELOAD_CLICK.name()) ) RANDOM_RATE = RandomRate.RELOAD_CLICK;
        if ( randomRate.equals(RandomRate.ON_RELOAD.name()) ) RANDOM_RATE = RandomRate.ON_RELOAD;
        if ( randomRate.equals(RandomRate.ON_CLICK.name()) ) RANDOM_RATE = RandomRate.ON_CLICK;
        if ( randomRate.equals("Jens Bergensten") ) jeb = true;

        if ( splashMode.equals(SplashMode.VANILLA.name()) ) SPLASH_MODE = SplashMode.VANILLA;
        if ( splashMode.equals(SplashMode.BOTH.name()) ) SPLASH_MODE = SplashMode.BOTH;
        if ( splashMode.equals(SplashMode.CUSTOM.name()) ) SPLASH_MODE = SplashMode.CUSTOM;

        SplasherSimpleConfig.LOGGER.info("All " + configs.getConfigsList().size() + " configs for " + SplasherMod.LOGGER.getName() + " have been set properly.");
    }
}
