import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

/**
 * Utility class for determining the version of the project based on Git commit hash.
 */
class Versioning {

    private static final String UNSPECIFIED = "unspecified"

    /**
     * Get the version of the project.
     * - Returns the abbreviated commit hash of HEAD.
     * - On error → returns "unspecified".
     *
     * Proper version should be assigned in CI/CD pipeline for release builds by running Gradle tasks with
     * -Pversion=<version> property.
     *
     * @param projectRootDir the root directory of the project (containing the .git directory)
     * @return the version string
     */
    static String getSnapshotVersion(File projectRootDir) {
        def builder =
                new FileRepositoryBuilder()
                        .setGitDir(new File(projectRootDir, ".git"))
                        .readEnvironment()
                        .findGitDir()

        try (Repository repository = builder.build()) {
            def headId = repository.resolve("HEAD")
            if (headId == null) {
                return UNSPECIFIED
            }

            try (RevWalk revWalk = new RevWalk(repository)) {
                RevCommit headCommit = revWalk.parseCommit(headId)
                return headCommit.getId().name().substring(0, 7)
            }
        } catch (Exception e) {
            System.err.println("Error determining version: " + e)
            return UNSPECIFIED
        }
    }
}
