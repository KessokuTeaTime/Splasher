package net.krlite.splasher.mixin;

import net.krlite.splasher.SplasherMod;
import net.krlite.splasher.config.SplasherModConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow private @Nullable String splashText;
    @Shadow @Final private static Logger LOGGER;
    private final Text SPLASH = new LiteralText("Splash!");

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void injected(CallbackInfo ci) {
        if ( SplasherModConfigs.jeb ) splashText = MinecraftClient.getInstance().getSplashTextLoader().get();

        Screen screen = MinecraftClient.getInstance().currentScreen;

        if ( SplasherMod.shouldReloadSplashText ) {
            ((TitleScreenAccessor) screen).setSplashText(MinecraftClient.getInstance().getSplashTextLoader().get());

            SplasherMod.shouldReloadSplashText = false;
        }

        if ( SplasherModConfigs.RANDOM_RATE.onClick() ) {
            TextRenderer textRenderer = ((ScreenAccessor) screen).getTextRenderer();
            int splashWidth = this.textRenderer.getWidth(SPLASH);

            this.addDrawableChild(new PressableTextWidget(width / 2 - splashWidth / 2, 2, splashWidth, 10, SPLASH, (button -> {
                //LOGGER.warn("Clicked!");
                ((TitleScreenAccessor) screen).setSplashText(MinecraftClient.getInstance().getSplashTextLoader().get());
            }), textRenderer));
        }
    }
}
