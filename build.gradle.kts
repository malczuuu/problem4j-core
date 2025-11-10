import com.diffplug.spotless.LineEnding

plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.diffplug.spotless").version("8.0.0")
    id("com.gradleup.nmcp").version("1.2.0")
}

group = "io.github.malczuuu.problem4j"

/**
 * In order to avoid hardcoding snapshot versions, we derive the version from the current Git commit hash. For CI/CD add
 * -Pversion={releaseVersion} parameter to match Git tag.
 */
version =
    if (version == "unspecified") {
        getSnapshotVersion(rootProject.rootDir)
    } else {
        version
    }

java {
    toolchain.languageVersion = JavaLanguageVersion.of(8)
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform(libs.junit.bom))

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)

    testImplementation(libs.assertj.core)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])

            pom {
                name = project.name
                description = "Core library implementing Problem model according to RFC7807"
                url = "https://github.com/malczuuu/${rootProject.name}"
                inceptionYear = "2025"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }
                developers {
                    developer {
                        id = "malczuuu"
                        name = "Damian Malczewski"
                        url = "https://github.com/malczuuu"
                    }
                }
                issueManagement {
                    system = "GitHub Issues"
                    url = "https://github.com/malczuuu/${rootProject.name}/issues"
                }
                scm {
                    connection = "scm:git:https://github.com/malczuuu/${rootProject.name}.git"
                    developerConnection = "scm:git:git@github.com:malczuuu/${rootProject.name}.git"
                    url = "https://github.com/malczuuu/${rootProject.name}"
                }
            }
        }
    }
}

nmcp {
    publishAllPublicationsToCentralPortal {
        username = System.getenv("PUBLISHING_USERNAME")
        password = System.getenv("PUBLISHING_PASSWORD")

        publishingType = "USER_MANAGED"
    }
}

signing {
    if (project.hasProperty("sign")) {
        useInMemoryPgpKeys(
            System.getenv("SIGNING_KEY"),
            System.getenv("SIGNING_PASSWORD"),
        )
        sign(publishing.publications["maven"])
    }
}

spotless {
    format("misc") {
        target("**/.gitattributes", "**/.gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    java {
        target("**/src/**/*.java")
        forbidWildcardImports()

        googleJavaFormat("1.28.0")
        lineEndings = LineEnding.UNIX
    }

    kotlin {
        target("**/src/**/*.kt")

        ktfmt("0.59").metaStyle()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    kotlinGradle {
        target("**/*.gradle.kts")

        ktlint()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }
}

/**
 * Usage:
 *   ./gradlew printVersion
 */
tasks.register("printVersion") {
    description = "Prints the current project version to the console"
    group = "help"
    doLast {
        println("${project.name} version: ${project.version}")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
}

tasks.withType<Jar>().configureEach {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Build-Jdk-Spec" to java.toolchain.languageVersion.get().toString(),
            "Created-By" to "Gradle ${gradle.gradleVersion}",
        )
    }
}

/**
 * Disable doclint to avoid errors and warnings on missing JavaDoc comments.
 */
tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).apply {
        addStringOption("Xdoclint:none", "-quiet")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
