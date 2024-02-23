plugins {
	base
	java
	idea
	`maven-publish`
	alias(libs.plugins.fabric.loom)
}

group = libs.versions.maven.group
version = libs.versions.mod

base {
	archivesName.set(libs.versions.archives.name)
}

repositories {
	mavenCentral()
	maven { url = uri("https://jitpack.io") }
	maven { url = uri("https://api.modrinth.com/maven") }
	maven { url = uri("https://maven.shedaniel.me/") }
}

dependencies {
	minecraft(libs.minecraft)
	mappings(libs.yarn)
	modImplementation(libs.bundles.fabric)

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
		inputs.property("version", version)

		filesMatching("fabric.mod.json") {
			expand(mapOf("version" to version))
		}
	}

	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesName}" }
		}
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
