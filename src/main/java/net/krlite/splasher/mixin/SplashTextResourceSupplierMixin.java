package net.krlite.splasher.mixin;

import net.krlite.splasher.SplashTextSupplier;
import net.krlite.splasher.SplasherMod;
import net.krlite.splasher.config.SplasherConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.krlite.splasher.SplasherMod.LOGGER;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
	@Mutable @Shadow @Final private List<String> splashTexts;
	@Shadow @Final private Session session;
	private int trigger = 0;
	private String restore = null;

	@Inject(method = "get", at = @At("RETURN"), cancellable = true)
	private void injected(CallbackInfoReturnable<String> cir) {
		if ( trigger < 1 + (((SplasherConfig.RandomRate) SplasherConfig.RANDOM_RATE.getValue()).onReload() ? 1 : 0) ) {
			trigger++;

			return;
		}

		if ( !SplasherConfig.ENABLE_SPLASH_TEXTS.getValue() || (!((SplasherConfig.SplashMode) SplasherConfig.SPLASH_MODE.getValue()).isVanilla() && !((SplasherConfig.SplashMode) SplasherConfig.SPLASH_MODE.getValue()).isCustom()) ) {
			cir.setReturnValue(null);
			if ( !SplasherConfig.DISABLE_DEBUG_INFO.getValue() ) {
				LOGGER.warn("Splash mode: DISABLED");
			}

			return;
		}

		if ( ((SplasherConfig.SplashMode) SplasherConfig.SPLASH_MODE.getValue()).isVanilla() && !((SplasherConfig.SplashMode) SplasherConfig.SPLASH_MODE.getValue()).isCustom() && !SplasherConfig.FOLLOW_CLIENT_LANGUAGE.getValue() ) {
			cir.cancel();
			if ( !SplasherConfig.DISABLE_DEBUG_INFO.getValue() ) {
				LOGGER.info("Splash mode: VANILLA (raw)");
			}

			return;
		}

		String splashText = new SplashTextSupplier().getSplashes(session, splashTexts);

		if ( SplasherMod.shouldReloadSplashText() ) {
			restore = null;
		}

		if ( restore != null ) {
			cir.setReturnValue(restore);

			return;
		}

		else {
			restore = splashText;
		}

		cir.setReturnValue(splashText);



		String language = "en_us";

		if ( SplasherConfig.FOLLOW_CLIENT_LANGUAGE.getValue() ) {
			language = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
		}

		if ( !(SplasherConfig.RANDOM_RATE.getValue() == SplasherConfig.RandomRate.Jens_Bergensten) && !SplasherConfig.DISABLE_DEBUG_INFO.getValue() ) {
			if ( SplasherConfig.FOLLOW_CLIENT_LANGUAGE.getValue() ) LOGGER.info("Splash mode: " + SplasherConfig.SPLASH_MODE.getValueRaw());
			else LOGGER.info("Splash mode: " + SplasherConfig.SPLASH_MODE.getValueRaw() + " (raw)");

			if ( splashText != null ) LOGGER.info("Loaded splash: '" + splashText + "' in language " + language + ".");
			else LOGGER.warn("Loaded empty splash.");
		}
	}
}
