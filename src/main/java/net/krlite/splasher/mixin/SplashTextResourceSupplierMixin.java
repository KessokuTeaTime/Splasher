package net.krlite.splasher.mixin;

import com.google.common.collect.Lists;
import net.krlite.splasher.SplasherMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.util.Session;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
	@Shadow @Final private List<String> splashTexts;
	private final List<String> customSplashTexts = Lists.newArrayList();
	private String splashText;
	private static final Random RANDOM = new Random();
	private Session session;

	public SplashTextResourceSupplierMixin(Session session) {
		this.session = session;
	}

	private void applyCustomSplashTexts() {
		customSplashTexts.clear();
		customSplashTexts.add("Pog");
		customSplashTexts.add("POG!");
		customSplashTexts.add("好耶");
	}

	@Inject(method = "get", at = @At("RETURN"), cancellable = true)
	private void injected(CallbackInfoReturnable<String> cir) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
//		applyCustomSplashTexts();

		if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
//			splashText = "Merry X-mas!";
			splashText = new TranslatableText("mixin.splasher.x_mas").getString();
			cir.setReturnValue(splashText);
		} else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
//			splashText = "Happy new year!";
			splashText = new TranslatableText("mixin.splasher.new_year").getString();
			cir.setReturnValue(splashText);
		} else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
//			splashText = "OOoooOOOoooo! Spooky!";
			splashText = new TranslatableText("mixin.splasher.spooky").getString();
			cir.setReturnValue(splashText);
		} else if (splashTexts.isEmpty()) {
			splashText = null;
			cir.setReturnValue(splashText);
		} else if (this.session != null && RANDOM.nextInt(splashTexts.size()) == 42) {
			String var10000 = this.session.getUsername();
//			splashText = var10000.toUpperCase(Locale.ROOT) + " IS YOU";
			splashText = var10000.toUpperCase(Locale.ROOT) + new TranslatableText("mixin.splasher.is_you").getString();
			cir.setReturnValue(splashText);
		} else {
//			splashText = (String)splashTexts.get(RANDOM.nextInt(splashTexts.size()));
			splashText = new TranslatableText("splash.minecraft." + RANDOM.nextInt(splashTexts.size())).getString();
			cir.setReturnValue(splashText);
		}

		String clientLanguage = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
		if (splashText != null) {
			SplasherMod.LOGGER.info("Loaded Splash Text : " + splashText + " in Language : " + clientLanguage);
		}
		else {
			SplasherMod.LOGGER.info("Loaded Empty Splash Text");
		}
	}
}
