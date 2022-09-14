package net.krlite.splasher.mixin;

import net.krlite.splasher.config.SplasherModConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Shadow private @Nullable String splashText;

    @Inject(method = "render", at = @At("HEAD"))
    public void injected(CallbackInfo ci) {
        if (SplasherModConfigs.jeb) splashText = MinecraftClient.getInstance().getSplashTextLoader().get();
    }
}
