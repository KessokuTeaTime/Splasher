package band.kessokuteatime.splasher.fabric;

import band.kessokuteatime.splasher.Splasher;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public final class SplasherModMenuIntegration implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return Splasher::createConfigScreen;
	}
}
