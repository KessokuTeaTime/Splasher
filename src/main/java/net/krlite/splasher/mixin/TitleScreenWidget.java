package net.krlite.splasher.mixin;

import net.krlite.splasher.Splasher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenWidget extends Screen {
    @Shadow @Nullable @Mutable private String splashText;

    protected TitleScreenWidget(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if ((Splasher.CONFIG.randomRate.onReload() && Splasher.isReady)) Splasher.PUSHER.let();
        if (!Splasher.isReady) {
            if (Splasher.CONFIG.randomRate.onClick() && !Splasher.CONFIG.randomRate.onReload()) Splasher.PUSHER.let();
            Splasher.isReady = true;
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (Splasher.PUSHER.queue()) splashText = MinecraftClient.getInstance().getSplashTextLoader().get();
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"), index = 0)
    private float scale(float h) {
        Splasher.updateSize(
                (splashText != null ? this.textRenderer.getWidth(Text.literal(splashText)) : 0) * h,
                10 * h
        );
        return h;
    }
}
