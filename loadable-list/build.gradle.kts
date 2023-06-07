plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    `maven-publish`
}

java {
    sourceCompatibility = Versions.java
}

dependencies {
    api(project(":loadable"))

    implementation(Libraries.KOTLINX_COROUTINES_CORE)

    testImplementation(Libraries.KOTLIN_TEST)
    testImplementation(Libraries.KOTLINX_COROUTINES_TEST)
    testImplementation(Libraries.TURBINE)
}

publishing {
    repositories {
        loadable()
    }

    publications {
        register<MavenPublication>(Variants.RELEASE) {
            groupId = Metadata.GROUP
            artifactId = Metadata.artifact("list")
            version = Versions.Loadable.NAME

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}
