package band.kessokuteatime.splasher.mixin;

import band.kessokuteatime.splasher.Splasher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SplashRenderer.class)
public abstract class SplashRendererMixin {
	@Shadow
	@Final
	private Component splash;

	@ModifyArg(
			method = "extractRenderState",
			at = @At(
					value = "INVOKE",
					target = "Lorg/joml/Matrix3x2f;translate(FF)Lorg/joml/Matrix3x2f;"
			),
			index = 0
	)
	private float splasher$moveToLeft(float x) {
		return Splasher.config().texts.lefty ? x - 246 : x;
	}

	@ModifyArg(
			method = "extractRenderState",
			at = @At(
					value = "INVOKE",
					target = "Lorg/joml/Matrix3x2f;rotate(F)Lorg/joml/Matrix3x2f;"
			)
	)
	private float splasher$rotate(float angle) {
		return Splasher.config().texts.lefty ? -angle : angle;
	}

	@ModifyArg(
			method = "extractRenderState",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;withPose(Lorg/joml/Matrix3x2fc;)Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;"
			),
			index = 0
	)
	private Matrix3x2fc splasher$capturePose(Matrix3x2fc pose) {
		Splasher.captureSplashPose(pose, Minecraft.getInstance().font.width(splash));
		return pose;
	}
}
