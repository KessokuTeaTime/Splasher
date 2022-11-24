package net.krlite.splasher;

import net.fabricmc.loader.api.FabricLoader;
import net.krlite.splasher.config.SplasherModConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.minecraft.text.TranslatableText;
import org.apache.commons.compress.utils.Lists;

import java.nio.file.Path;
import java.util.*;

import static net.krlite.splasher.SplasherMod.LOGGER;

public class SplashTextSupplier {
    public String getSplashes(Session session, List<String> splashTexts) {
        Path path = Path.of(FabricLoader.getInstance().getConfigDir() + "/" + SplasherMod.MOD_ID + "/customizations");
        String language = "en_us";

        if ( SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE ) {
            language = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
        }



        List<String> customSplashTexts = Lists.newArrayList();

        if ( SplasherModConfigs.SPLASH_MODE.isVanilla() ) {
            customSplashTexts.addAll(splashTexts);
        }

        if ( SplasherModConfigs.SPLASH_MODE.isCustom() ) {
            customSplashTexts.addAll(CustomSplashTextLoader.ReadCustomSplashText(path, language + ".txt"));
        }



        if ( customSplashTexts.isEmpty() ) {
            if ( SplasherModConfigs.SPLASH_MODE.isVanilla() ){
                LOGGER.warn("Minecraft has no splash loaded. Check your data as if it may be broken.");
            }
            LOGGER.error("Empty stack!");

            return null;
        }

        else {
            final int random = new Random().nextInt(customSplashTexts.size());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());



            //LOGGER.warn(customSplashTexts.toString());

            if ( SplasherModConfigs.ENABLE_FESTIVALS ) {
                if ( calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) == 24) {
                    return getXmasSplash(SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE);
                }

                if ( calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 1) {
                    return getNewYearSplash(SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE);
                }

                if ( calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 31) {
                    return getHalloweenSplash(SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE);
                }

                if ( session != null && random == 42 ) {
                    return session.getUsername().toUpperCase(Locale.ROOT) + getPlayerSplash(SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE);
                }
            }

            if ( SplasherModConfigs.SPLASH_MODE.isVanilla() && random <= splashTexts.size() ) {
                if ( SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE ) return new TranslatableText("splash.minecraft." + random).getString();
                else return customSplashTexts.get(random);
            }

            if ( SplasherModConfigs.SPLASH_MODE.isCustom() ) {
                return customSplashTexts.get(random);
            }
        }

        return null;
    }

    private String getXmasSplash(boolean translate) {
        if ( translate ) return new TranslatableText("mixin." + SplasherMod.MOD_ID + ".x_mas").getString();
        else return "Merry X-mas!";
    }

    private String getNewYearSplash(boolean translate) {
        if ( translate ) return new TranslatableText("mixin." + SplasherMod.MOD_ID + ".new_year").getString();
        else return "Happy new year!";
    }

    private String getHalloweenSplash(boolean translate) {
        if ( translate ) return new TranslatableText("mixin." + SplasherMod.MOD_ID + ".halloween").getString();
        else return "OOoooOOOoooo! Spooky!";
    }

    private String getPlayerSplash(boolean translate) {
        if ( translate ) return new TranslatableText("mixin." + SplasherMod.MOD_ID + ".is_you").getString();
        else return " IS YOU";
    }
}
