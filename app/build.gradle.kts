plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.takaobrog.androidapiapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.takaobrog.androidapiapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "env"

    productFlavors {
        create("local") {
            dimension = "env"
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8765/\"")
            resValue("string", "app_name", "Todo (local)")
        }
        create("prod") {
            dimension = "env"
            buildConfigField("String", "BASE_URL", "\"https://takaopres.blog/android_api_server_app/\"")
            resValue("string", "app_name", "Todo (debug)")
        }
    }

    androidComponents {
        beforeVariants(selector().withBuildType("release")) { variant ->
            variant.enable = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    kapt(libs.moshi.kotlin.codegen)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)

    // DataStore preferences
    implementation(libs.androidx.datastore.preferences)
}