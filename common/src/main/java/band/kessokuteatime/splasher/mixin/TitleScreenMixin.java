package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
	@Shadow
	private SplashRenderer splash;

	@Inject(method = "init", at = @At("RETURN"))
	private void splasher$initialize(CallbackInfo ci) {
		Splasher.onTitleScreenInitialized();
	}

	@Inject(method = "extractRenderState", at = @At("HEAD"))
	private void splasher$prepareSplash(
			GuiGraphicsExtractor graphics,
			int mouseX,
			int mouseY,
			float partialTick,
			CallbackInfo ci
	) {
		Splasher.beginFrame();
		boolean splashVisible = !Minecraft.getInstance().options.hideSplashTexts().get();
		if (splashVisible && Splasher.shouldRefreshSplash()) {
			splash = Minecraft.getInstance().getSplashManager().getSplash();
		}
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	private void splasher$handleClick(
			MouseButtonEvent event,
			boolean doubleClick,
			CallbackInfoReturnable<Boolean> cir
	) {
		if (Splasher.config().texts.randomRate.onClick()
				&& Splasher.isMouseHovering(
						((TitleScreen) (Object) this).width,
						event.x(),
						event.y()
				)) {
			Splasher.push();
			Splasher.playClickingSound();
			cir.setReturnValue(true);
		}
	}
}
