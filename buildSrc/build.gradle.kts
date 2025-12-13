plugins {
    `kotlin-dsl`
}

// To hide warnings that Kotlin does not support 25 JDK target yet, to be revisited in the future.
if (JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_25)) {
    kotlin {
        jvmToolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
}

repositories {
    mavenCentral()
}
