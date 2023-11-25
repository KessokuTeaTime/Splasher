package band.kessokuteatime.splasher.neoforge;

import band.kessokuteatime.splasher.Splasher;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(Splasher.ID)
public class SplasherNeoForge {
    public SplasherNeoForge() {
        if (FMLLoader.getDist().isClient()) {
            Splasher.onInitialize();
        }
    }
}
