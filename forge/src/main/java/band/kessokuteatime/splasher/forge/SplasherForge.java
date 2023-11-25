package band.kessokuteatime.splasher.forge;

import band.kessokuteatime.splasher.Splasher;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Splasher.ID)
public class SplasherForge {
    public SplasherForge() {
        EventBuses.registerModEventBus(Splasher.ID, FMLJavaModLoadingContext.get().getModEventBus());
        if (FMLLoader.getDist().isClient()) {
            Splasher.onInitialize();
        }
    }
}
