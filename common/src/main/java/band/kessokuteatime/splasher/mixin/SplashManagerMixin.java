package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.config.SplasherConfig;
import band.kessokuteatime.splasher.supplier.SplashTextSupplier;
import net.minecraft.client.User;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SplashManager.class)
public abstract class SplashManagerMixin {
	@Shadow
	private List<Component> splashes;

	@Shadow
	@Final
	private User user;

	@Inject(method = "getSplash", at = @At("RETURN"), cancellable = true)
	private void splasher$selectSplash(CallbackInfoReturnable<SplashRenderer> cir) {
		if (!Splasher.isTitleInitialized()) {
			return;
		}

		SplasherConfig config = Splasher.config();
		SplasherConfig.Source source = config.texts.source;
		if (!config.splashTextsEnabled || (!source.vanilla() && !source.custom())) {
			cir.setReturnValue(null);
			logMode(source, false);
			return;
		}

		Splasher.refreshFormatting();
		Component splash = SplashTextSupplier.getSplash(user, splashes);
		cir.setReturnValue(splash == null ? null : new SplashRenderer(splash));
		logMode(source, !config.followsClientLanguage);
	}

	private static void logMode(SplasherConfig.Source source, boolean raw) {
		SplasherConfig config = Splasher.config();
		if (config.debugInfoEnabled && config.texts.randomRate != SplasherConfig.RandomRate.JEB) {
			Splasher.LOGGER.info("Splash mode: {}{}.", source, raw ? " (raw)" : "");
		}
	}
}
