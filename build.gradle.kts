plugins {
	base
	java
	`maven-publish`

	alias(libs.plugins.fabric.loom)
	alias(libs.plugins.pkl)
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
}

dependencies {
	minecraft(libs.minecraft)
	mappings(libs.yarn)
	modImplementation(libs.bundles.fabric)

	implementation(libs.pkl)
	modCompileOnly(libs.bounced)
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17

	withSourcesJar()
}

pkl {
	javaCodeGenerators {
		register("pklCodeGen") {
			sourceModules.set(files("src/main/resources/Config.pkl"))
			generateJavadoc.set(true)
		}
	}
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
