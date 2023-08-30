package net.krlite.splasher.loader;

import com.google.common.collect.Lists;
import net.krlite.splasher.Splasher;
import net.krlite.splasher.config.SplasherConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SplashTextLoader {
	private final File file;

	public SplashTextLoader(File file) {
		this.file = file;
	}

	private ArrayList<String> loadFromFile() throws FileNotFoundException {
		ArrayList<String> result = Lists.newArrayList();

		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) result.add(scanner.nextLine());

		if (result.isEmpty() && !(Splasher.CONFIG.randomRate == SplasherConfig.RandomRate.JEB)) Splasher.LOGGER.warn("Loaded empty custom splash text.");
		return result;
	}

	public List<String> load() {
		if (file.exists()) {
			try {
				return loadFromFile();
			} catch (Exception exception) {
				if (!(Splasher.CONFIG.randomRate == SplasherConfig.RandomRate.JEB)) Splasher.LOGGER.debug("Failed to load custom splash texts: ", exception);
			}
		}
		if (!(Splasher.CONFIG.randomRate == SplasherConfig.RandomRate.JEB)) Splasher.LOGGER.error("Failed to load custom splash texts: File " + file.getName() + " not found.");
		return new ArrayList<>();
	}
}
