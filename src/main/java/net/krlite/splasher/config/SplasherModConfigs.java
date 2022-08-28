package net.krlite.splasher.config;

import com.mojang.datafixers.util.Pair;
import net.krlite.splasher.SplasherMod;

public class SplasherModConfigs {
    public static SplasherSimpleConfig CONFIG;
    private static SplasherModConfigProvider configs;
    public enum SPLASH_MODE_ENUM { VANILLA, BOTH, CUSTOM }

    public static boolean ENABLE_SPLASH_TEXTS;
    public static boolean FOLLOW_CLIENT_LANGUAGE;

    public static boolean USE_PURE_VANILLA;
    public static String SPLASH_MODE;

    public static void registerConfigs() {
        configs = new SplasherModConfigProvider();
        createConfigs();

        CONFIG = SplasherSimpleConfig.of(SplasherMod.MODID).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("enable_splash_texts", true), "true/false");
        configs.addKeyValuePair(new Pair<>("follow_client_language", true), "true/false");
        configs.addKeyValuePair(new Pair<>("use_pure_vanilla", false), "true/false");
        configs.addKeyValuePair(new Pair<>("splash_mode", SPLASH_MODE_ENUM.BOTH.name()), "VANILLA/BOTH/CUSTOM");
    }

    private static void assignConfigs() {
        ENABLE_SPLASH_TEXTS = CONFIG.getOrDefault("enable_splash_texts", true);
        FOLLOW_CLIENT_LANGUAGE = CONFIG.getOrDefault("follow_client_language", true);
        USE_PURE_VANILLA = CONFIG.getOrDefault("use_pure_vanilla", false);
        SPLASH_MODE = CONFIG.getOrDefault("splash_mode", SPLASH_MODE_ENUM.BOTH.name());

        SplasherSimpleConfig.LOGGER.info("All " + configs.getConfigsList().size() + " configs for " + SplasherMod.LOGGER.getName() + " have been set properly.");
    }
}
