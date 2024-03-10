package band.kessokuteatime.splasher.config.modmenu;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.config.SplasherConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class SplasherModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            Splasher.CONFIG.load();
            return AutoConfig.getConfigScreen(SplasherConfig.class, parent).get();
        };
    }
}
