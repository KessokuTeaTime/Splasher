package net.krlite.splasher.mixin;

import net.krlite.splasher.SplasherMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

import static net.krlite.splasher.SplasherMod.LOGGER;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "reloadResources()Ljava/util/concurrent/CompletableFuture;", at = @At("TAIL"))
    public void injected(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        //LOGGER.warn("Injecting...");
        SplasherMod.shouldReloadSplashText = true;
    }
}