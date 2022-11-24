package net.krlite.splasher.config.core;

public class Provider implements Request.DefaultProvider {
	private String config = "";

	public void addCategory(String name) {
		config += "# | " + name + " |\n\n";
	}

	public void addOption(Option<?> option) {
	}

	@Override
	public String get(String namespace) {
		return config;
	}
}
