package net.krlite.splasher.mixin;

import net.krlite.splasher.Splasher;
import net.krlite.splasher.SplasherConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenWidget extends Screen {
    @Shadow @Nullable @Mutable private String splashText;
    @Shadow @Final public static Text COPYRIGHT;

    protected TitleScreenWidget(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        SplasherConfig splasherConfig = Splasher.config.load(SplasherConfig.class);

        if (splasherConfig.randomRate.onReload()) Splasher.reloadSplashText();

        if (splasherConfig.randomRate.onClick()) {
            this.addDrawableChild(
                    new PressableTextWidget(
                            this.width - 12 - this.textRenderer.getWidth(COPYRIGHT),
                            this.height - 10, 10, 10,
                            Text.literal("⚡").formatted(Formatting.YELLOW),
                            (button) -> Splasher.reloadSplashText(),
                            this.textRenderer
                    )
            );
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (Splasher.shouldReloadSplashText()) {
            splashText = MinecraftClient.getInstance().getSplashTextLoader().get();
            Splasher.keepSplashText();
        }
    }
}
