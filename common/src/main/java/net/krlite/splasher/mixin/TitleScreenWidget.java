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

@Mixin(MinecraftClient.class)
class MinecraftClientTrigger {
    @Inject(method = "setScreen", at = @At("TAIL"))
    private void trigger(Screen screen, CallbackInfo ci) {
        if (screen instanceof TitleScreen) {
            if ((Splasher.CONFIG.randomRate.onReload() && Splasher.initialized)) Splasher.push();
            else if (!Splasher.initialized) Splasher.initialized = true;
        }
    }
}

@Mixin(TitleScreen.class)
public class TitleScreenWidget extends Screen {
    @Shadow @Nullable @Mutable private String splashText;

    protected TitleScreenWidget(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (Splasher.shouldSplash())
            splashText = MinecraftClient.getInstance().getSplashTextLoader().get();
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"), index = 0)
    private float translateX(float x) {
        return Splasher.CONFIG.lefty ? MinecraftClient.getInstance().getWindow().getScaledWidth() - x : x;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;"))
    private float rotate(float angle) {
        return Splasher.CONFIG.lefty ? -angle : angle;
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
