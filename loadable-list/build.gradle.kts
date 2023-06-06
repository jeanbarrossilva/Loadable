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

    testImplementation(Libraries.KOTLIN_TEST)
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
