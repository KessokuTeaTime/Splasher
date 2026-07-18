package band.kessokuteatime.splasher;

import band.kessokuteatime.splasher.base.FormattingType;
import band.kessokuteatime.splasher.config.SplasherConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fc;
import org.joml.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class Splasher {
	public static final String ID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static final int DEFAULT_COLOR = 0xFFFF00;
	private static final Style DEFAULT_STYLE = Style.EMPTY.withColor(DEFAULT_COLOR);

	private static final ConfigHolder<SplasherConfig> CONFIG =
			AutoConfig.register(SplasherConfig.class, Toml4jConfigSerializer::new);
	private static final RandomSource RANDOM = RandomSource.createThreadLocalInstance();

	private static boolean refreshRequested = true;
	private static boolean titleInitialized;
	private static boolean splashRendered;
	private static Matrix3x2f inverseSplashPose;
	private static int renderedTextWidth;
	private static int color = DEFAULT_COLOR;
	private static List<ChatFormatting> formattings = List.of();

	private Splasher() {
	}

	public static SplasherConfig config() {
		return CONFIG.get();
	}

	public static Screen createConfigScreen(Screen parent) {
		CONFIG.load();
		return AutoConfigClient.getConfigScreen(SplasherConfig.class, parent).get();
	}

	public static void reloadConfig() {
		CONFIG.load();
		push();
	}

	public static void onTitleScreenInitialized() {
		if (titleInitialized && config().texts.randomRate.onReload()) {
			push();
		}
		titleInitialized = true;
	}

	public static boolean isTitleInitialized() {
		return titleInitialized;
	}

	public static void push() {
		refreshRequested = true;
	}

	public static boolean shouldRefreshSplash() {
		if (config().texts.randomRate == SplasherConfig.RandomRate.JEB) {
			return true;
		}

		boolean refresh = refreshRequested;
		refreshRequested = false;
		return refresh;
	}

	public static void beginFrame() {
		splashRendered = false;
	}

	public static void captureSplashPose(Matrix3x2fc pose, int textWidth) {
		Matrix3x2f inverse = new Matrix3x2f(pose);
		if (!inverse.isFinite() || Math.abs(inverse.determinant()) < 1.0E-6F) {
			inverseSplashPose = null;
			splashRendered = false;
			return;
		}

		inverseSplashPose = inverse.invert();
		renderedTextWidth = textWidth;
		splashRendered = true;
	}

	public static boolean isMouseHovering(double screenWidth, double mouseX, double mouseY) {
		if (!config().splashTextsEnabled || !splashRendered || inverseSplashPose == null) {
			return false;
		}

		Vector2f local = inverseSplashPose.transformPosition((float) mouseX, (float) mouseY, new Vector2f());
		float left = -(renderedTextWidth / 2);
		return local.x >= left && local.x <= left + renderedTextWidth
				&& local.y >= -8 && local.y <= 2;
	}

	public static void playClickingSound() {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	public static void refreshFormatting() {
		if (!config().texts.colorful) {
			formattings = List.of();
			color = DEFAULT_COLOR;
			return;
		}

		formattings = FormattingType.getFormatting(RANDOM.nextDouble());
		color = RANDOM.nextInt(0x1000000);
	}

	public static Component formatSplash(Component component) {
		if (!config().texts.colorful) {
			return component;
		}
		return applyFormatting(component);
	}

	public static Component createLiteralSplash(String text) {
		MutableComponent result = Component.empty();
		Style style = baseSplashStyle();
		int segmentStart = 0;

		for (int index = 0; index + 1 < text.length(); index++) {
			if (text.charAt(index) != ChatFormatting.PREFIX_CODE) {
				continue;
			}

			ChatFormatting formatting = ChatFormatting.getByCode(text.charAt(index + 1));
			if (formatting == null) {
				continue;
			}

			appendSegment(result, text, segmentStart, index, style);
			style = formatting == ChatFormatting.RESET
					? DEFAULT_STYLE
					: style.applyLegacyFormat(formatting);
			index++;
			segmentStart = index + 1;
		}

		appendSegment(result, text, segmentStart, text.length(), style);
		return result;
	}

	public static Component createTranslatedSplash(String key, Object... arguments) {
		return Component.translatable(key, arguments).setStyle(baseSplashStyle());
	}

	private static Style baseSplashStyle() {
		if (!config().texts.colorful) {
			return DEFAULT_STYLE;
		}

		Style style = Style.EMPTY.withColor(color);
		for (ChatFormatting formatting : formattings) {
			style = style.applyFormat(formatting);
		}
		return style;
	}

	private static MutableComponent applyFormatting(Component component) {
		Style style = baseSplashStyle().applyTo(component.getStyle());

		MutableComponent result = MutableComponent.create(component.getContents()).setStyle(style);
		for (Component sibling : component.getSiblings()) {
			result.append(applyFormatting(sibling));
		}
		return result;
	}

	private static void appendSegment(
			MutableComponent target,
			String text,
			int start,
			int end,
			Style style
	) {
		if (start < end) {
			target.append(Component.literal(text.substring(start, end)).setStyle(style));
		}
	}
}
