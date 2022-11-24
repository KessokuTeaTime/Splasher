package net.krlite.splasher.config.core.api;

import net.krlite.splasher.config.core.PlumeConfig;
import net.krlite.splasher.config.core.Provider;

public abstract class PlumeOptions {
	Provider provider = new Provider();

	public abstract void accept(Provider provider);
	public abstract void read(PlumeConfig config);
}
