plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    `maven-publish`
}

java {
    sourceCompatibility = Versions.java
    targetCompatibility = Versions.java
}

dependencies {
    implementation(Libraries.KOTLINX_COROUTINES_CORE)

    testImplementation(Libraries.TURBINE)
    testImplementation(Libraries.KOTLIN_TEST)
    testImplementation(Libraries.KOTLINX_COROUTINES_TEST)
}

publishing {
    repositories {
        loadable()
    }

    publications {
        register<MavenPublication>(Variants.RELEASE) {
            groupId = Metadata.GROUP
            artifactId = Metadata.ARTIFACT
            version = Versions.Loadable.NAME

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}
