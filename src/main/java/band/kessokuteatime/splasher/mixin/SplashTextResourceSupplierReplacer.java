package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.config.SplasherConfig;
import band.kessokuteatime.splasher.supplier.SplashTextSupplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashTextRenderer;
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
public abstract class SplashTextResourceSupplierReplacer {
	@Mutable
	@Shadow
	@Final private List<String> splashTexts;

	@Shadow
	@Final
	private Session session;

	@Inject(method = "get", at = @At("RETURN"), cancellable = true)
	private void get(CallbackInfoReturnable<SplashTextRenderer> cir) {
		if (!Splasher.initialized) return;

		if (!Splasher.CONFIG.splashTextsEnabled || (!Splasher.CONFIG.source.vanilla() && !Splasher.CONFIG.source.custom())) {
			cir.setReturnValue(null);
			if (Splasher.CONFIG.debugInfoEnabled) Splasher.LOGGER.warn("Splash mode: " + Splasher.CONFIG.source.toString().toUpperCase());
			return;
		}

		if (Splasher.CONFIG.source.vanilla() && !Splasher.CONFIG.source.custom() && !Splasher.CONFIG.followsClientLanguage) {
			cir.cancel();
			if (Splasher.CONFIG.debugInfoEnabled) Splasher.LOGGER.info("Splash mode: " + Splasher.CONFIG.source.toString().toUpperCase() + " (raw)");
			return;
		}

		String splashText = SplashTextSupplier.getSplashes(session, splashTexts);
		cir.setReturnValue(new SplashTextRenderer(splashText == null ? "" : splashText));

		if (Splasher.CONFIG.debugInfoEnabled && !(Splasher.CONFIG.randomRate == SplasherConfig.RandomRate.JEB) && Splasher.CONFIG.splashTextsEnabled) {
			if (Splasher.CONFIG.followsClientLanguage) Splasher.LOGGER.info("Splash mode: " + Splasher.CONFIG.source.toString().toUpperCase());
			else Splasher.LOGGER.info("Splash mode: " + Splasher.CONFIG.source.toString().toUpperCase() + " (raw)");

			if (splashText != null) Splasher.LOGGER.info(
					"Loaded splash: '" + splashText + "' in language "
							+ (!Splasher.CONFIG.followsClientLanguage ? "en_us" : MinecraftClient.getInstance().getLanguageManager().getLanguage()) + "."
			);
			else Splasher.LOGGER.warn("Loaded empty splash.");
		}
	}
}
