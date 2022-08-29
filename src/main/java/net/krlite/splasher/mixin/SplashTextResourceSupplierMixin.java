package net.krlite.splasher.mixin;

import com.google.common.collect.Lists;
import net.fabricmc.loader.api.FabricLoader;
import net.krlite.splasher.CustomSplashTextLoader;
import net.krlite.splasher.SplasherMod;
import net.krlite.splasher.config.SplasherModConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.util.Session;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.*;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
	@Mutable @Shadow @Final private List<String> splashTexts;
	private static final Random RANDOM = new Random();
	private final Session session;

	public SplashTextResourceSupplierMixin(Session session) {
		this.session = session;
	}

	@Inject(method = "get", at = @At("RETURN"), cancellable = true)
	private void injected(CallbackInfoReturnable<String> cir) {
		if (!SplasherModConfigs.ENABLE_SPLASH_TEXTS) {
			cir.setReturnValue(null);
			SplasherMod.LOGGER.info("Splash mode: DISABLED");

			return;
		}

		if (SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE) SplasherMod.LOGGER.info("Splash mode: " + SplasherModConfigs.SPLASH_MODE);
		else SplasherMod.LOGGER.info("Splash mode: " + SplasherModConfigs.SPLASH_MODE + " (without translations).");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		String splashText;
		String clientLanguage = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
		Path path = Path.of(FabricLoader.getInstance().getConfigDir() + "/splasher/customizations");
		List<String> customSplashTexts;

		if (SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE) customSplashTexts = CustomSplashTextLoader.ReadCustomSplashText(path, clientLanguage + ".txt");
		else customSplashTexts = CustomSplashTextLoader.ReadCustomSplashText(path, "en_us.txt");

		if (!Objects.equals(SplasherModConfigs.SPLASH_MODE, SplasherModConfigs.SPLASH_MODE_ENUM.CUSTOM.name())) {
			if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) == 24) {
				if (!SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE) splashText = "Merry X-mas!";
				else splashText = new TranslatableText("mixin.splasher.x_mas").getString();
				cir.setReturnValue(splashText);
			}

			else if (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 1) {
				if (!SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE) splashText = "Happy new year!";
				else splashText = new TranslatableText("mixin.splasher.new_year").getString();
				cir.setReturnValue(splashText);
			}

			else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 31) {
				if (!SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE) splashText = "OOoooOOOoooo! Spooky!";
				else splashText = new TranslatableText("mixin.splasher.spooky").getString();
				cir.setReturnValue(splashText);
			}

			else if (splashTexts.isEmpty()) {
				splashText = null;
				cir.setReturnValue(null);
			}

			else if (this.session != null && RANDOM.nextInt(splashTexts.size()) == 42) {
				String var10000 = this.session.getUsername();
				if (!SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE) splashText = var10000.toUpperCase(Locale.ROOT) + " IS YOU";
				else splashText = var10000.toUpperCase(Locale.ROOT) + new TranslatableText("mixin.splasher.is_you").getString();
				cir.setReturnValue(splashText);
			}

			else if (Objects.equals(SplasherModConfigs.SPLASH_MODE, SplasherModConfigs.SPLASH_MODE_ENUM.BOTH.name())) {
				int random;
				if (customSplashTexts.isEmpty()) random = RANDOM.nextInt(splashTexts.size());
				else random = RANDOM.nextInt(splashTexts.size() + customSplashTexts.size());

				if (random > splashTexts.size()) {
					splashText = customSplashTexts.get(random - splashTexts.size());
					cir.setReturnValue(splashText);
				}

				else {
					if (!SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE) splashText = (String)splashTexts.get(RANDOM.nextInt(splashTexts.size()));
					else splashText = new TranslatableText(("splash.minecraft." + RANDOM.nextInt(splashTexts.size()))).getString();
					cir.setReturnValue(splashText);
				}
			}

			else {
				if (!SplasherModConfigs.FOLLOW_CLIENT_LANGUAGE) splashText = (String)splashTexts.get(RANDOM.nextInt(splashTexts.size()));
				else splashText = new TranslatableText("splash.minecraft." + RANDOM.nextInt(splashTexts.size())).getString();
				cir.setReturnValue(splashText);

			}
		}
		else if(customSplashTexts.isEmpty()) {
			splashText = null;
			cir.setReturnValue(null);
		}

		else {
			splashText = customSplashTexts.get(RANDOM.nextInt(customSplashTexts.size()));
			cir.setReturnValue(splashText);
		}

		if (splashText != null) SplasherMod.LOGGER.info("Loaded splash: '" + splashText + "' in language " + clientLanguage + ".");
		else if (!Objects.equals(SplasherModConfigs.SPLASH_MODE, SplasherModConfigs.SPLASH_MODE_ENUM.CUSTOM.name())) SplasherMod.LOGGER.warn("Loaded empty splashes! Please check if your Minecraft data is lost.");
	}
}
