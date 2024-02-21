package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.config.SplasherConfig;
import band.kessokuteatime.splasher.config.SplasherWithPickle;
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

		if (!SplasherWithPickle.get().splashTextsEnabled || (!SplasherWithPickle.get().texts.source.vanilla() && !SplasherWithPickle.get().texts.source.custom())) {
			cir.setReturnValue(null);
			if (SplasherWithPickle.get().debug.debugInfoEnabled) Splasher.LOGGER.warn("Splash mode: " + SplasherWithPickle.get().texts.source.toString());
			return;
		}

		if (SplasherWithPickle.get().texts.source.vanilla() && !SplasherWithPickle.get().texts.source.custom() && !SplasherWithPickle.get().followsClientLanguage) {
			cir.cancel();
			if (SplasherWithPickle.get().debug.debugInfoEnabled) Splasher.LOGGER.info("Splash mode: " + SplasherWithPickle.get().texts.source.toString() + " (raw)");
			return;
		}

		String splashText = SplashTextSupplier.getSplashes(session, splashTexts);
		cir.setReturnValue(new SplashTextRenderer(splashText == null ? "" : splashText));

		if (SplasherWithPickle.get().debug.debugInfoEnabled && !(SplasherWithPickle.get().texts.randomRate == SplasherConfig.RandomRate.JENS) && SplasherWithPickle.get().splashTextsEnabled) {
			if (SplasherWithPickle.get().followsClientLanguage) Splasher.LOGGER.info("Splash mode: " + SplasherWithPickle.get().texts.source.toString());
			else Splasher.LOGGER.info("Splash mode: " + SplasherWithPickle.get().texts.source.toString() + " (raw)");

			if (splashText != null) Splasher.LOGGER.info(
					"Loaded splash: '" + splashText + "' in language "
							+ (!SplasherWithPickle.get().followsClientLanguage ? "en_us" : MinecraftClient.getInstance().getLanguageManager().getLanguage()) + "."
			);
			else Splasher.LOGGER.warn("Loaded empty splash.");
		}
	}
}
