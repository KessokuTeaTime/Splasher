package band.kessokuteatime.splasher.config;

import net.fabricmc.loader.api.FabricLoader;
import org.pkl.config.java.ConfigEvaluator;
import org.pkl.core.ModuleSource;
import org.pkl.core.ValueRenderers;
import org.pkl.core.util.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.UnaryOperator;

public class SplasherWithPickle {
    public static final File FILE = FabricLoader.getInstance().getConfigDir().resolve("splasher.pcf").toFile();

    public static final ModuleSource MODULE_SOURCE = ModuleSource.file(FILE);

    private static @Nullable SplasherConfig config;

    public static SplasherConfig get() {
        return config;
    }

    public static void load() {
        try (var evaluator = ConfigEvaluator.preconfigured()) {
            config = evaluator.evaluate(MODULE_SOURCE).as(SplasherConfig.class);
        }
    }

    public static void set(UnaryOperator<SplasherConfig> operator) {
        config = operator.apply(config);
    }

    public static void save() {
        try(var writer = new FileWriter(FILE)) {
            if (config != null) {
                ValueRenderers.pcf(writer, "", false, false).renderDocument();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
