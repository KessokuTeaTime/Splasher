package band.kessokuteatime.splasher.fabric;

import band.kessokuteatime.splasher.Splasher;
import net.fabricmc.api.ClientModInitializer;

public final class SplasherFabric implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Splasher.config();
	}
}
