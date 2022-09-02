package net.krlite.splasher.config;

import com.google.gson.annotations.SerializedName;
import com.mojang.datafixers.util.Pair;
import net.krlite.splasher.SplasherMod;

public class SplasherModConfigs {
    public static SplasherSimpleConfig CONFIG;
    private static SplasherModConfigProvider configs;
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
    public static boolean FOLLOW_CLIENT_LANGUAGE;
    public static SplashMode SPLASH_MODE = SplashMode.DEFAULT;

    public static void registerConfigs() {
        configs = new SplasherModConfigProvider();

        createConfigs();

        CONFIG = SplasherSimpleConfig.of(SplasherMod.MOD_ID).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("enable_splash_texts", true), "true/false");
        configs.addKeyValuePair(new Pair<>("follow_client_language", true), "true/false");
        configs.addKeyValuePair(new Pair<>("splash_mode", SplashMode.BOTH.name()), "VANILLA/BOTH/CUSTOM");
    }

    private static void assignConfigs() {
        String splashMode;

        ENABLE_SPLASH_TEXTS = CONFIG.getOrDefault("enable_splash_texts", true);
        FOLLOW_CLIENT_LANGUAGE = CONFIG.getOrDefault("follow_client_language", true);
        splashMode = CONFIG.getOrDefault("splash_mode", SplashMode.BOTH.name());

        if (splashMode.equals(SplashMode.VANILLA.name())) SPLASH_MODE = SplashMode.VANILLA;
        if (splashMode.equals(SplashMode.BOTH.name())) SPLASH_MODE = SplashMode.BOTH;
        if (splashMode.equals(SplashMode.CUSTOM.name())) SPLASH_MODE = SplashMode.CUSTOM;

        SplasherSimpleConfig.LOGGER.info("All " + configs.getConfigsList().size() + " configs for " + SplasherMod.LOGGER.getName() + " have been set properly.");
    }
}
