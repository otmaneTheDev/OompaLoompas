object App {
    const val appId = "com.otmanethedev.oompaloopa"
    const val versionName = "1.0.0"
    const val versionCode = 1
    const val minSDK = 26
    const val targetSDK = 33
}

object Dependencies {
    // Android
    const val coreKtx = "androidx.core:core-ktx:1.10.1"
    const val appCompat = "androidx.appcompat:appcompat:1.6.1"
    const val materialDesign = "com.google.android.material:material:1.9.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:2.44.2"
    const val hiltKapt = "com.google.dagger:hilt-compiler:2.44.2"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:2.9.0"

    // OkHttp logging
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:4.9.3"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:4.15.0"
    const val glideAnotationProcessor = ("com.github.bumptech.glide:compiler:4.15.0")
}

object TestDependencies {
    // Mockk
    const val mockk = "io.mockk:mockk:1.12.0"

    // JUnit
    const val junit = "junit:junit:4.13.2"
}

object SubModules {
    const val core = ":core"
    const val featureInfo = ":features:info"
}