plugins {
    id("com.android.application")
    id("kotlin-android")
}

@Suppress("UnstableApiUsage")
android {
    namespace = Metadata.namespace("app")
    compileSdk = Versions.Loadable.SDK_COMPILE

    defaultConfig {
        applicationId = Metadata.GROUP
        minSdk = Versions.Loadable.SDK_MIN
        targetSdk = Versions.Loadable.SDK_TARGET
        versionCode = Versions.Loadable.CODE
        versionName = Versions.Loadable.NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(Variants.RELEASE) {
            isMinifyEnabled = true
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
