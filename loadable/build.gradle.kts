plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    `maven-publish`
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
                from(components[Variants.RELEASE])
            }
        }
    }
}

@Suppress("UnstableApiUsage")
android {
    namespace = Metadata.NAMESPACE
    compileSdk = Versions.Loadable.SDK_COMPILE

    defaultConfig {
        minSdk = Versions.Loadable.SDK_MIN

        @Suppress("DEPRECATION")
        targetSdk = Versions.Loadable.SDK_TARGET

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    publishing {
        singleVariant(Variants.RELEASE) {
            withSourcesJar()
            withJavadocJar()
        }
    }

    buildTypes {
        getByName(Variants.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }

    kotlinOptions {
        jvmTarget = Versions.java.toString()
    }
}

@Suppress("SpellCheckingInspection")
dependencies {
    implementation(Libraries.VIEWMODEL_COMPOSE)
    implementation(Libraries.KOTLINX_COROUTINES_CORE)

    testImplementation(Libraries.TURBINE)
    testImplementation(Libraries.KOTLIN_TEST)
    testImplementation(Libraries.KOTLINX_COROUTINES_TEST)
}
