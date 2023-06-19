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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    androidTestImplementation(Libraries.COMPOSE_UI_TEST_JUNIT_4)
    androidTestImplementation(Libraries.COMPOSE_UI_TEST_MANIFEST)
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
