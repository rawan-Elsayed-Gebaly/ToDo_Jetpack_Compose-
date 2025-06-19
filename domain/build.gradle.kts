plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp) // Apply KSP plugin
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)  // Hilt Android dependency
    ksp(libs.hilt.android.compiler)   // Hilt compiler (using KSP for annotation processing)

    // Room dependencies
    implementation(libs.room.runtime)  // Room runtime
    implementation(libs.room.ktx)      // Room KTX for Kotlin extensions
    ksp(libs.room.compiler)          // Room compiler (using KSP for annotation processing)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)


    //Firebase auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

}