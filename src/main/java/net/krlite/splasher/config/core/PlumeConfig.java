package net.krlite.splasher.config.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Scanner;

public class PlumeConfig {
	public static final Logger LOGGER = LogManager.getLogger("PlumeConfig v0.0.1a");
	private final HashMap<String, String> config = new HashMap<>();
	private final Request request;
	private boolean broken = false;

	PlumeConfig(Request request) {
		this.request = request;
		String identifier = "PlumeConfig for " + request.fileName;

		if ( !request.file.exists() ) {
			LOGGER.info("Regenerating missing " + identifier + "...");
			try {
				create();
			} catch (IOException e) {
				LOGGER.error("Failed generating " + identifier + "!");
				LOGGER.trace(e);
				broken = true;
			}
		}

		if ( !broken ) {
			try {
				load();
			} catch (IOException e) {
				LOGGER.error("Failed loading " + identifier + "!");
				LOGGER.trace(e);
				broken = true;
			}
		}
	}

	private void create() throws IOException {
		request.file.getParentFile().mkdirs();
		Files.createFile(request.file.toPath());
		write();
	}

	private void write() throws IOException {
		PrintWriter writer = new PrintWriter(request.file, StandardCharsets.UTF_8);
		writer.write(request.getConfig());
		writer.close();
	}

	private void load() throws IOException {
		Scanner scanner = new Scanner(request.file);
		for ( int line = 1; scanner.hasNextLine(); line++ ) {
			parseConfigEntry(scanner.nextLine(), line);
		}
	}

	private void parseConfigEntry(String entry, int line) {
		if ( !entry.isEmpty() && !entry.startsWith("#") ) {
			String[] entryLine = entry.split("=", 2);
			if ( entryLine.length == 2 ) config.put(entryLine[0], entryLine[1]);
			else throw new RuntimeException("Syntax error in config " + request.fileName + " on line" + line + "!");
		}
	}
}
