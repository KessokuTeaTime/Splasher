package net.krlite.splasher.config.core;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public final class Option <T> {
	private final Text name, tooltip;
	private final T value, defaultValue;
	private final String comment;

	public void parse(String source) {
	}

	public Option(String key, T defaultValue, String comment) {
		this.name = new TranslatableText(key);
		this.tooltip = new TranslatableText(key + ".tooltip");
		this.value = this.defaultValue = defaultValue;
		this.comment = comment;
	}
}
