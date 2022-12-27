package net.krlite.splasher.mixin;

import net.krlite.splasher.Splasher;
import net.krlite.splasher.SplasherConfig;
import net.krlite.splasher.supplier.SplashTextSupplier;
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

import static net.krlite.splasher.Splasher.CONFIG;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierReplacer {
	@Mutable @Shadow @Final private List<String> splashTexts;
	@Shadow @Final private Session session;

	@Inject(method = "get", at = @At("RETURN"), cancellable = true)
	private void get(CallbackInfoReturnable<String> cir) {
		if (!Splasher.PUSHER.access() || !Splasher.isReady) {
			cir.setReturnValue(null);
			return;
		}

		if (!CONFIG.enableSplashTexts || (!CONFIG.splashMode.isVanilla() && !CONFIG.splashMode.isCustom())) {
			cir.setReturnValue(null);
			if (!CONFIG.disableDebugInfo) Splasher.LOGGER.warn("Splash mode: " + CONFIG.splashMode.getLocalizedName().toUpperCase());
			return;
		}

		if (CONFIG.splashMode.isVanilla() && !CONFIG.splashMode.isCustom() && !CONFIG.followClientLanguage) {
			cir.cancel();
			if (!CONFIG.disableDebugInfo) Splasher.LOGGER.info("Splash mode: " + CONFIG.splashMode.getLocalizedName().toUpperCase() + " (raw)");
			return;
		}

		String splashText = new SplashTextSupplier().getSplashes(session, splashTexts);
		cir.setReturnValue(splashText);

		if (!(CONFIG.randomRate == SplasherConfig.RandomRate.JEB) && !CONFIG.disableDebugInfo && CONFIG.enableSplashTexts) {
			if (CONFIG.followClientLanguage) Splasher.LOGGER.info("Splash mode: " + CONFIG.splashMode.getLocalizedName().toUpperCase());
			else Splasher.LOGGER.info("Splash mode: " + CONFIG.splashMode.getLocalizedName().toUpperCase() + " (raw)");

			if (splashText != null) Splasher.LOGGER.info(
					"Loaded splash: '" + splashText + "' in language "
							+ (!CONFIG.followClientLanguage ? "en_us" : MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode()) + "."
			);
			else Splasher.LOGGER.warn("Loaded empty splash.");
		}
	}
}
