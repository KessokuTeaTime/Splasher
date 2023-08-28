package net.krlite.splasher;

import dev.architectury.platform.Platform;
import net.krlite.splasher.config.SplasherConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Splasher {
	public static final String NAME = "Splasher", ID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static final SplasherConfig CONFIG = new SplasherConfig();
	private static final AtomicBoolean shouldSplash = new AtomicBoolean(CONFIG.randomRate == SplasherConfig.RandomRate.JEB);

	public static final boolean isBouncedLoaded = Platform.isModLoaded("bounced");

	record Node(double x, double y) {
		public double getCross(Node p1, Node p2) {
			return (p2.x - p1.x) * (this.y - p1.y) - (this.x - p1.x) * (p2.y - p1.y);
		}

		public Node rotate(Node origin, double clockwiseDegree) {
			return new Node(
					(this.x - origin.x) * Math.cos(Math.toRadians(clockwiseDegree)) - (this.y - origin.y) * Math.sin(Math.toRadians(clockwiseDegree)) + origin.x,
					(this.x - origin.x) * Math.sin(Math.toRadians(clockwiseDegree)) + (this.y - origin.y) * Math.cos(Math.toRadians(clockwiseDegree)) + origin.y
			);
		}

		public Node append(Node node) {
			return new Node(this.x + node.x, this.y + node.y);
		}
	}

	record Rect(Node lu, Node ld, Node rd, Node ru) {
		public Rect(Node lu, Node rd) {
			this(lu, new Node(lu.x, rd.y), rd, new Node(rd.x, lu.y));
		}

		public boolean contains(Node node) {
			return node.getCross(ld, lu) * node.getCross(ru, rd) >= 0 && node.getCross(lu, ru) * node.getCross(rd, ld) >= 0;
		}

		public Rect rotate(Node origin, double clockwiseDegree) {
			return new Rect(lu.rotate(origin, clockwiseDegree), ld.rotate(origin, clockwiseDegree), rd.rotate(origin, clockwiseDegree), ru.rotate(origin, clockwiseDegree));
		}
	}

	public static void push() {
		shouldSplash.set(true);
	}

	public static boolean shouldSplash() {
		return shouldSplash.getAndSet(false);
	}

	// Splash text data
	private static final ArrayList<Formatting> FORMATTINGS = new ArrayList<>();
	private static int color = 0xFFFF00;
	private static float height = 0, width = 0;
	public static boolean initialized = false;

	public static void onInitClient() {
		CONFIG.save();
	}

	public static void playClickingSound() {
		if (MinecraftClient.getInstance() != null)
			MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	public static boolean isMouseHovering(double width, double mouseX, double mouseY) {
		// Public so other mods can use
		return isMouseHovering(new Node(width / 2.0 + (CONFIG.lefty ? -90 : 90), 70 - 6), new Node(mouseX, mouseY));
	}

	static boolean isMouseHovering(Node origin, Node mouse) {
		return CONFIG.enableSplashTexts && new Rect(
				origin.append(new Node(-width / 2, -height / 2)),
				origin.append(new Node(width / 2, height / 2))
		).rotate(origin, CONFIG.lefty ? 20 : -20).contains(mouse);
	}

	public static void updateSize(float width, float height) {
		Splasher.width = width;
		Splasher.height = height;
	}

	public static void updateFormatting(ArrayList<Formatting> formattings, int color) {
		if (CONFIG.colorful) {
			Splasher.color = color;
			if (formattings != null) {
				Splasher.FORMATTINGS.clear();
				Splasher.FORMATTINGS.addAll(formattings.stream().filter(Objects::nonNull).distinct().collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
			}
		}
	}

	public static Text getFormattedSplashText(String text) {
		MutableText splashText = Text.literal(text);
		FORMATTINGS.forEach(splashText::formatted);
		return splashText;
	}

	public static int getColor() {
		return color;
	}
}
