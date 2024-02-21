package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.config.SplasherWithPickle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
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
            if ((SplasherWithPickle.get().texts.randomRate.onReload() && Splasher.initialized)) Splasher.push();
            else if (!Splasher.initialized) Splasher.initialized = true;
        }
    }
}

@Mixin(TitleScreen.class)
class TitleScreenMixin {
    @Shadow @Nullable private SplashTextRenderer splashText;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SplashTextRenderer;render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/client/font/TextRenderer;I)V", shift = At.Shift.BEFORE))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (Splasher.shouldSplash()) {
            splashText = MinecraftClient.getInstance().getSplashTextLoader().get();
        }
    }
}

@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {
    @Shadow @Final private String text;

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"), index = 0)
    private float translateX(float x) {
        return SplasherWithPickle.get().texts.lefty ? MinecraftClient.getInstance().getWindow().getScaledWidth() - x : x;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;"))
    private float rotate(float angle) {
        return SplasherWithPickle.get().texts.lefty ? -angle : angle;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"), index = 0)
    private float scale(float h) {
        Splasher.updateSize(
                (text != null ? MinecraftClient.getInstance().textRenderer.getWidth(Text.literal(text)) : 0) * h,
                10 * h
        );
        return h;
    }
}
