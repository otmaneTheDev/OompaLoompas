plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = App.appId
    compileSdk = App.targetSDK

    defaultConfig {
        applicationId = App.appId
        minSdk = App.minSDK
        targetSdk = App.targetSDK
        versionCode = App.versionCode
        versionName = App.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Android
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.materialDesign)
    implementation(Dependencies.constraintLayout)
    implementation("androidx.navigation:navigation-fragment:2.6.0")

    // Submodules
    implementation(project(SubModules.featureInfo))

    // Hilt
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltKapt)

    // Glide
    implementation(Dependencies.glide)
    annotationProcessor(Dependencies.glideAnotationProcessor)

    // Testing
    testImplementation(TestDependencies.junit)
}