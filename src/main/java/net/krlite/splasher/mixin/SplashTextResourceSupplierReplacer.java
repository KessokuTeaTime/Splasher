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

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierReplacer {
	@Mutable @Shadow @Final private List<String> splashTexts;
	@Shadow @Final private Session session;
	private int trigger = 0;
	private String restore = null;

	@Inject(method = "get", at = @At("RETURN"), cancellable = true)
	private void get(CallbackInfoReturnable<String> cir) {
		SplasherConfig splasherConfig = Splasher.config.load(SplasherConfig.class);

		if (trigger < 1 + (splasherConfig.randomRate.onReload() ? 1 : 0)) {
			trigger++;
			return;
		}

		if (!splasherConfig.enableSplashTexts || (!splasherConfig.splashMode.isVanilla() && !splasherConfig.splashMode.isCustom())) {
			cir.setReturnValue(null);
			if (!splasherConfig.disableDebugInfo) Splasher.LOGGER.warn("Splash mode: " + splasherConfig.splashMode.getLocalizedName().toUpperCase());
			return;
		}

		if (splasherConfig.splashMode.isVanilla() && !splasherConfig.splashMode.isCustom() && !splasherConfig.followClientLanguage) {
			cir.cancel();
			if (!splasherConfig.disableDebugInfo) Splasher.LOGGER.info("Splash mode: " + splasherConfig.splashMode.getLocalizedName().toUpperCase() + " (raw)");
			return;
		}

		if (Splasher.shouldReloadSplashText()) restore = null;
		String splashText = new SplashTextSupplier().getSplashes(session, splashTexts);

		if ( restore != null ) {
			cir.setReturnValue(restore);
			return;
		} else restore = splashText;

		cir.setReturnValue(restore);

		if (!(splasherConfig.randomRate == SplasherConfig.RandomRate.JEB) && !splasherConfig.disableDebugInfo) {
			if (splasherConfig.followClientLanguage) Splasher.LOGGER.info("Splash mode: " + splasherConfig.splashMode.getLocalizedName().toUpperCase());
			else Splasher.LOGGER.info("Splash mode: " + splasherConfig.splashMode.getLocalizedName().toUpperCase() + " (raw)");

			if (splashText != null) Splasher.LOGGER.info(
					"Loaded splash: '" + splashText + "' in language "
							+ (!splasherConfig.followClientLanguage ? "en_us" : MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode()) + "."
			);
			else Splasher.LOGGER.warn("Loaded empty splash.");
		}
	}
}
