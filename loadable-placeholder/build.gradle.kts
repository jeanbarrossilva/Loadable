plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    namespace = Metadata.namespace("placeholder")
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    publishing {
        singleVariant(Variants.RELEASE) {
            withSourcesJar()
            withJavadocJar()
        }
    }

    buildTypes {
        release {
            @Suppress("UnstableApiUsage")
            isMinifyEnabled = false
        }
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }

    kotlinOptions {
        jvmTarget = Versions.java.toString()
    }

    @Suppress("UnstableApiUsage")
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }
}

dependencies {
    api(project(":loadable"))

    implementation(project(":loadable-placeholder-test"))
    implementation(Libraries.ACCOMPANIST_PLACEHOLDER_MATERIAL)
    implementation(Libraries.COMPOSE_MATERIAL_3)
    implementation(Libraries.COMPOSE_UI_TOOLING)

    testImplementation(Libraries.COMPOSE_UI_TEST_JUNIT_4)
    testImplementation(Libraries.COMPOSE_UI_TEST_MANIFEST)
    testImplementation(Libraries.ROBOLECTRIC)
}

publishing {
    repositories {
        loadable()
    }

    publications {
        register<MavenPublication>(Variants.RELEASE) {
            groupId = Metadata.GROUP
            artifactId = Metadata.artifact("placeholder")
            version = Versions.Loadable.NAME

            afterEvaluate {
                from(components[Variants.RELEASE])
            }
        }
    }
}
