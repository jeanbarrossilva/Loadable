import java.net.URI
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/** Adds the repository in which Loadable is located. **/
fun RepositoryHandler.loadable(): MavenArtifactRepository {
    return maven {
        url = URI.create("https://maven.pkg.github.com/jeanbarrossilva/loadable")

        credentials {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}
