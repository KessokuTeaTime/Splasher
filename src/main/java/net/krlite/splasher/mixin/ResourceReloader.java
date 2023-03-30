package net.krlite.splasher.mixin;

import net.krlite.splasher.Splasher;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public class ResourceReloader {
    @Inject(method = "reloadResources()Ljava/util/concurrent/CompletableFuture;", at = @At("TAIL"))
    public void reload(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        Splasher.push();
    }
}
