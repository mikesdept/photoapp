plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "mikes.dept.data"
    compileSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(projects.domain)

    //DI
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // Room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    // Gson
    implementation(libs.gson)

    // OkHttp
    api(libs.logging.interceptor)

    implementation(libs.compose.paging)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}
