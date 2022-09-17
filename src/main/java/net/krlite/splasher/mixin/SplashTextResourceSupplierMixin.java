package net.krlite.splasher.mixin;

import net.krlite.splasher.SplashTextSupplier;
import net.krlite.splasher.SplasherMod;
import net.krlite.splasher.config.SplasherModConfigs;
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
	private boolean trigger = true;
	private String restore = null;

	@Inject(method = "get", at = @At("RETURN"), cancellable = true)
	private void injected(CallbackInfoReturnable<String> cir) {
		if ( trigger ) {
			trigger = false;

			return;
		}

		if ( !SplasherModConfigs.ENABLE_SPLASH_TEXTS || (!SplasherModConfigs.SPLASH_MODE.isVanilla() && !SplasherModConfigs.SPLASH_MODE.isCustom()) ) {
			cir.setReturnValue(null);
			LOGGER.warn("Splash mode: DISABLED");

			return;
		}

		if ( SplasherModConfigs.SPLASH_MODE.isVanilla() && !SplasherModConfigs.SPLASH_MODE.isCustom() && !SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE ) {
			cir.cancel();
			LOGGER.info("Splash mode: VANILLA (raw)");

			return;
		}

		String splashText = new SplashTextSupplier().getSplashes(session, splashTexts);

		if ( SplasherModConfigs.shouldReloadSplashText) {
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

		if ( SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE ) {
			language = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
		}

		if ( !SplasherModConfigs.jeb ) {
			if ( SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE ) LOGGER.info("Splash mode: " + SplasherModConfigs.SPLASH_MODE);
			else LOGGER.info("Splash mode: " + SplasherModConfigs.SPLASH_MODE + " (raw)");

			if ( splashText != null ) LOGGER.info("Loaded splash: '" + splashText + "' in language " + language + ".");
			else LOGGER.warn("Loaded empty splash.");
		}
	}
}
