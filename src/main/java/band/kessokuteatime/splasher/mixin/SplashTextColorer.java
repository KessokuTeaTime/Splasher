package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SplashTextRenderer.class)
public class SplashTextColorer {
	@Redirect(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"
			)
	)
	private void colorSplashText(DrawContext context, TextRenderer textRenderer, String text, int xCentered, int y, int color) {
		context.drawCenteredTextWithShadow(
				textRenderer, Splasher.getFormattedSplashText(text),
				xCentered, y, Splasher.CONFIG.texts.colorful ? Splasher.getColor() : color
		);
	}
}
