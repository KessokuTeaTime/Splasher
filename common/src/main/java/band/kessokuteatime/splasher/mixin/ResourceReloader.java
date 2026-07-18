package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public abstract class ResourceReloader {
	@Inject(
			method = "reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;",
			at = @At("RETURN")
	)
	private void splasher$reloadConfig(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
		Splasher.reloadConfig();
	}
}
