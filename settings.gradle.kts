pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

// if rootProject.name matches project.name, it fails to generate output for nmcpZipAggregation task
rootProject.name = "problem4j-root"

include(":problem4j-core")
