# Releasing

## Sonatype Snapshots

See [`gradle-publish-snapshot.yml`](.github/workflows/gradle-publish-snapshot.yml) for publishing snapshot version
instructions. Workflow requires manual trigger for snapshot build so it's not published regularly.

Artifacts are published to Snapshot Repository, using following Gradle task.

```bash
./gradlew -Pversion=<version> publishAllPublicationsToCentralPortalSnapshots
```

### Accessing packages from Sonatype Snapshots

1. Maven:
   ```xml
   <repositories>
       <repository>
           <id>sonatype-snapshots</id>
           <url>https://central.sonatype.com/repository/maven-snapshots/</url>
           <releases>
               <enabled>false</enabled>
           </releases>
           <snapshots>
               <enabled>true</enabled>
               <updatePolicy>always</updatePolicy>
           </snapshots>
       </repository>
   </repositories>
   <dependencies>
   <dependency>
       <groupId>io.github.malczuuu.problem4j</groupId>
       <artifactId>problem4j-core</artifactId>
       <version>1.2.0-SNAPSHOT</version>
   </dependency>
   </dependencies>
   ```
2. Gradle (Kotlin DSL):
   ```kotlin
   repositories {
       mavenCentral()
       maven {
           url = uri("https://central.sonatype.com/repository/maven-snapshots/")
           content {
               includeGroup("io.github.malczuuu.problem4j")
           }
           mavenContent {
               snapshotsOnly()
           }
       }
   }
   dependencies {
       implementation("io.github.malczuuu.problem4j:problem4j-core:1.2.0-SNAPSHOT")
   }
   ```

## Maven Central

1. Keep Git tags with `vX.Y.Z-suffix` format. GitHub Actions job will only trigger on such tags and will remove `v`
   prefix.
2. After publishing a release, update [`next_version.txt`](.github/utils/next_version.txt) for snapshot builds
   automation.
3. The releasing procedure only uploads the artifacts to Sonatype repository. You need to manually log in to Sonatype to
   push the artifacts to Maven Central.

See [`gradle-publish-release.yml`](.github/workflows/gradle-publish-release.yml) for publishing release versions
instructions.

Set the following environment variables in your CI/CD (GitHub Actions, etc.):

```txt
# generated on Sonatype account
PUBLISHING_USERNAME=<username>
PUBLISHING_PASSWORD=<password>

# generated PGP key for signing artifacts
SIGNING_KEY=<PGP key>
SIGNING_PASSWORD=<PGP password>
```

Artifacts are published to Maven Central via Sonatype, using following Gradle task.

```bash
./gradlew -Pversion=<version> -Psign publishAllPublicationsToCentralPortal
```

This command uses `nmcp` Gradle plugin - [link](https://github.com/GradleUp/nmcp).
