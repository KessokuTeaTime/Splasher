import org.gradle.api.publish.maven.MavenPublication

architectury {
	common("fabric", "neoforge") {
		platformPackage("neoforge", "forge")
	}
}

dependencies {
	add("implementation", libs.fabric.loader)
	add("implementation", libs.cloth.config)
}

publishing {
	publications {
		create<MavenPublication>("mavenCommon") {
			artifactId = "${libs.versions.archives.name.get()}-common"
			from(components["java"])
		}
	}
}
