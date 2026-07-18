package band.kessokuteatime.splasher.loader;

import band.kessokuteatime.splasher.Splasher;
import band.kessokuteatime.splasher.config.SplasherConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class SplashTextLoader {
	private final Path path;

	public SplashTextLoader(Path path) {
		this.path = path;
	}

	public List<String> load() {
		if (!Files.isRegularFile(path)) {
			if (shouldLogDebug()) {
				Splasher.LOGGER.info("Custom splash file {} was not found.", path.getFileName());
			}
			return List.of();
		}

		try {
			List<String> splashes = Files.readAllLines(path, StandardCharsets.UTF_8);
			if (splashes.isEmpty() && shouldLogDebug()) {
				Splasher.LOGGER.warn("Custom splash file {} is empty.", path.getFileName());
			}
			return splashes;
		} catch (IOException exception) {
			if (Splasher.config().texts.randomRate != SplasherConfig.RandomRate.JEB) {
				Splasher.LOGGER.error("Failed to read custom splash file {}.", path, exception);
			}
			return List.of();
		}
	}

	private static boolean shouldLogDebug() {
		return Splasher.config().debugInfoEnabled
				&& Splasher.config().texts.randomRate != SplasherConfig.RandomRate.JEB;
	}
}
