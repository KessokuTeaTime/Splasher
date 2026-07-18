package band.kessokuteatime.splasher.neoforge;

import band.kessokuteatime.splasher.Splasher;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Splasher.ID, dist = Dist.CLIENT)
public final class SplasherNeoForge {
	public SplasherNeoForge(ModContainer modContainer) {
		Splasher.config();
		IConfigScreenFactory configScreenFactory =
				(container, parent) -> Splasher.createConfigScreen(parent);
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, configScreenFactory);
	}
}
