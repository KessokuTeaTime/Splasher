package net.krlite.splasher;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Splasher implements ModInitializer {
	public static final String MOD_ID = "splasher";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final SplasherConfig CONFIG = new SplasherConfig(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".toml").toFile());
	public static final Pusher PUSHER = new Pusher(CONFIG.randomRate == SplasherConfig.RandomRate.JEB);

	public record Node(double x, double y) {
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

	public record Rect(Node lu, Node ld, Node rd, Node ru) {
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

	public record Pusher(AtomicBoolean ready) {
		public Pusher(boolean ready) {
			this(new AtomicBoolean(ready));
		}

		public void push() {
			ready.set(true);
		}

		public boolean pull() {
			return ready.get() && ready.getAndSet(CONFIG.randomRate == SplasherConfig.RandomRate.JEB);
		}

		public void run(Runnable runnable) {
			if (pull()) runnable.run();
		}
	}

	// Splash text data
	private static final ArrayList<Formatting> FORMATTINGS = new ArrayList<>();
	private static int color = 0xFFFF00;
	private static float height = 0, width = 0;
	public static boolean initialized = false;

	@Override
	public void onInitialize() {
		CONFIG.load();
		CONFIG.save();
		ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof TitleScreen) {
				ScreenMouseEvents.beforeMouseClick(screen)
						.register((currentScreen, mouseX, mouseY, button) -> {if (isMouseOverSplashText(new Node(scaledWidth / 2.0 + 90, 70 - 8), new Node(mouseX, mouseY)) && CONFIG.randomRate.onClick()) PUSHER.push();});
			}
		});
	}

	public static boolean isMouseOverSplashText(Node origin, Node mouse) {
		return new Rect(
				origin.append(new Node(-width / 2, -height / 2)),
				origin.append(new Node(width / 2, height / 2))
		).rotate(origin, -20).contains(mouse);
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
