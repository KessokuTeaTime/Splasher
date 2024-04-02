plugins {
	base
	java
	idea
	`maven-publish`
	alias(libs.plugins.architectury.loom)
}

group = libs.versions.maven.group.get()
version = "${libs.versions.minecraft.get()}-${libs.versions.mod.get()}"
base.archivesName.set("${libs.versions.archives.name}-forge")

loom {
	forge {
		mixinConfig("splasher.mixins.json")
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://jitpack.io") }
	maven { url = uri("https://api.modrinth.com/maven") }
	maven { url = uri("https://maven.shedaniel.me/") } // Cloth Config
	maven { url = uri("https://maven.terraformersmc.com/releases/") } // Mod Menu
}

dependencies {
	minecraft(libs.minecraft)
	mappings(libs.yarn) { artifact { classifier = "v2" } }
	forge(libs.forge)
	modApi(libs.architectury.api)

	modApi(libs.cloth.config)
	modCompileOnly(libs.bounced)
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17

	withSourcesJar()
}

tasks {
	processResources {
		inputs.property("version", libs.versions.mod.get())

		filesMatching("META-INF/mods.toml") {
			expand(mapOf("version" to libs.versions.mod.get()))
		}
	}

	jar {
		from("LICENSE")
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	repositories {
	}
}
