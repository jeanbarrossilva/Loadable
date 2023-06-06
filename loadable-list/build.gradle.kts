plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Versions.java
}

dependencies {
    api(project(":loadable"))

    testImplementation(Libraries.KOTLIN_TEST)
}
