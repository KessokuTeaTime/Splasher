package net.krlite.splasher.mixin;

import net.krlite.splasher.SplasherMod;
import net.krlite.splasher.config.SplasherConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow @Nullable @Mutable private String splashText;
    @Shadow @Final public static Text COPYRIGHT;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if ( SplasherConfig.RANDOM_RATE.getValue().onReload() ) {
            SplasherMod.reloadSplashText();
        }

        if ( SplasherConfig.RANDOM_RATE.getValue().onClick() ) {
            this.addDrawableChild(
                    new PressableTextWidget(
                            this.width - 12 - this.textRenderer.getWidth(COPYRIGHT),
                            this.height - 10, 10, 10,
                            new LiteralText("⚡").styled(style -> style.withColor(0xFFFF00)),
                            (button) -> SplasherMod.reloadSplashText(),
                            this.textRenderer
                    )
            );
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if ( SplasherMod.shouldReloadSplashText() ) {
            splashText = MinecraftClient.getInstance().getSplashTextLoader().get();
            SplasherMod.keepSplashText();
        }
    }
}
