package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.base.FormattingType;
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
import java.util.Random;

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
		
		boolean enabled = Splasher.CONFIG.get().splashTextsEnabled, localized = Splasher.CONFIG.get().followsClientLanguage;
		SplasherConfig.Source source = Splasher.CONFIG.get().texts.source;

		if (!enabled || (!source.vanilla() && !source.custom())) {
			// Has nothing
			cir.setReturnValue(null);
			if (Splasher.CONFIG.get().debugInfoEnabled) Splasher.LOGGER.warn("Splash mode: " + source.name());
			return;
		}

		if (Splasher.CONFIG.get().texts.colorful) {
			// Update color and formatting before everything
			double formatting = new Random().nextDouble(1);
			Splasher.updateFormatting(FormattingType.getFormatting(formatting), new Random().nextInt(0xFFFFFF));
		}

		if (source.vanilla() && !source.custom() && !localized) {
			// Pure vanilla
			cir.cancel();
			if (Splasher.CONFIG.get().debugInfoEnabled) Splasher.LOGGER.info("Splash mode: " + source.name() + " (raw)");
			return;
		}

		String splashText = SplashTextSupplier.getSplashes(session, splashTexts);
		cir.setReturnValue(new SplashTextRenderer(splashText == null ? "" : splashText));

		if (Splasher.CONFIG.get().debugInfoEnabled && !(Splasher.CONFIG.get().texts.randomRate == SplasherConfig.RandomRate.JEB)) {
			if (localized) Splasher.LOGGER.info("Splash mode: " + source.name());
			else Splasher.LOGGER.info("Splash mode: " + source.name() + " (raw)");

			if (splashText != null) Splasher.LOGGER.info(
					"Loaded splash: '" + splashText + "' in language "
							+ (!localized ? "en_us" : MinecraftClient.getInstance().getLanguageManager().getLanguage()) + "."
			);
			else Splasher.LOGGER.warn("Loaded empty splash.");
		}
	}
}
