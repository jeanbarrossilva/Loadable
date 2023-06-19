import org.gradle.api.JavaVersion

object Versions {
    const val ACCOMPANIST = "0.31.3-beta"
    const val COMPOSE_COMPILER = "1.4.7"
    const val COMPOSE_MATERIAL_3 = "1.1.0"
    const val COMPOSE_UI = "1.4.3"
    const val GRADLE = "7.4.1"
    const val KOTLIN = "1.8.21"
    const val KOTLINX_COROUTINES = "1.6.4"
    const val TEST_RUNNER = "1.5.2"
    const val TURBINE = "0.12.1"

    val java = JavaVersion.VERSION_11

    object Loadable {
        const val CODE = 13
        const val NAME = "1.6.0"
        const val SDK_COMPILE = 33
        const val SDK_MIN = 21
        const val SDK_TARGET = SDK_COMPILE
    }
}
