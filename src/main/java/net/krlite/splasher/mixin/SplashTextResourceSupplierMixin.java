package net.krlite.splasher.mixin;

import net.krlite.splasher.SplasherMod;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
	public String splashText = "Pog";

	@Inject(method = "get", at = @At("RETURN"), cancellable = true)
	private void injected(CallbackInfoReturnable<String> cir) {
		cir.setReturnValue(splashText);
		SplasherMod.LOGGER.info("Loaded Splash Text : " + splashText);
	}
}
