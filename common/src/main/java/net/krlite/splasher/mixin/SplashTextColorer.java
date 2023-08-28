package net.krlite.splasher.mixin;

import net.krlite.splasher.Splasher;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(TitleScreen.class)
public class SplashTextColorer extends DrawableHelper {
	@Redirect(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawCenteredText(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"
			), slice = @Slice(
					to = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isDemo()Z")
			)
	)
	private void colorSplashText(MatrixStack matrixStack, TextRenderer textRenderer, String text, int xCentered, int y, int color) {
		drawCenteredTextWithShadow(
				matrixStack, textRenderer, Splasher.getFormattedSplashText(text).asOrderedText(),
				xCentered, y, Splasher.CONFIG.colorful ? Splasher.getColor() : color
		);
	}
}
