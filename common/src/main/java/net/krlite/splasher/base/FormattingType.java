package net.krlite.splasher.base;

import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public enum FormattingType {
	NONE(null, 0.5),
	STRIKETHROUGH(Formatting.STRIKETHROUGH, 0.12),
	UNDERLINE(Formatting.UNDERLINE, 0.75),
	ITALIC(Formatting.ITALIC, 0.8),
	BOLD(Formatting.BOLD, 0.03),
	OBFUSCATED(Formatting.OBFUSCATED, 1);

	@Nullable
	private final Formatting formatting;
	private final double weight; // Between 0 and 1


	FormattingType(@Nullable Formatting formatting, double weight) {
		this.formatting = formatting;
		this.weight = MathHelper.clamp(weight, 0, 1);
	}

	public double getWeight() {
		return weight;
	}

	@Nullable
	public Formatting getFormatting() {
		return formatting;
	}

	public static ArrayList<Formatting> getFormatting(double weight) {
		return Arrays.stream(FormattingType.values())
				.filter(formattingType -> Math.abs(formattingType.weight - MathHelper.clamp(weight, 0, 1))<= 0.17)
				.collect(ArrayList::new, (list, f) -> list.add(f.formatting), ArrayList::addAll);
	}
}
