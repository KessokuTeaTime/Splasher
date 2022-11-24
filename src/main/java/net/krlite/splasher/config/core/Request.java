package net.krlite.splasher.config.core;

import java.io.File;

class Request {
	interface DefaultProvider {
		String get(String namespace);
		static String empty(String namespace) {return "";}
	}

	final File file;
	final String fileName;
	private DefaultProvider provider;

	private Request(File file, String fileName) {
		this.file = file;
		this.fileName = fileName;
		this.provider = DefaultProvider::empty;
	}

	Request provider(DefaultProvider provider) {
		this.provider = provider;
		return this;
	}

	PlumeConfig request() {
		return new PlumeConfig(this);
	}

	String getConfig() {
		return provider.get(fileName) + "\n";
	}
}
