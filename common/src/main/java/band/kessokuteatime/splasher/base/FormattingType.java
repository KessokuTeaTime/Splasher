package band.kessokuteatime.splasher.base;

import net.minecraft.ChatFormatting;
import net.minecraft.util.Mth;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum FormattingType {
	NONE(null, 0.5),
	STRIKETHROUGH(ChatFormatting.STRIKETHROUGH, 0.12),
	UNDERLINE(ChatFormatting.UNDERLINE, 0.75),
	ITALIC(ChatFormatting.ITALIC, 0.8),
	BOLD(ChatFormatting.BOLD, 0.03),
	OBFUSCATED(ChatFormatting.OBFUSCATED, 1);

	private final ChatFormatting formatting;
	private final double weight;

	FormattingType(ChatFormatting formatting, double weight) {
		this.formatting = formatting;
		this.weight = Mth.clamp(weight, 0, 1);
	}

	public static List<ChatFormatting> getFormatting(double weight) {
		double clampedWeight = Mth.clamp(weight, 0, 1);
		return Arrays.stream(values())
				.filter(type -> Math.abs(type.weight - clampedWeight) <= 0.17)
				.map(type -> type.formatting)
				.filter(Objects::nonNull)
				.distinct()
				.toList();
	}
}
