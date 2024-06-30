plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "mikes.dept.photoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "mikes.dept.photoapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(type = "String", name = "BASE_ENDPOINT", value = "\"https://api.unsplash.com/\"")
            buildConfigField(type = "String", name = "ACCESS_KEY", value = "\"d0AJ_kOVF_fqN3ngsCow4HpzvQHE-TzwbOp3gtbQyZo\"")
            buildConfigField(type = "String", name = "SECRET_KEY", value = "\"6PNccJVfXQTAmmZv1q_ZFDRdz0gBKFDuo3fUMPpf9t4\"")
        }
        debug {
            isDebuggable = true
            buildConfigField(type = "String", name = "BASE_ENDPOINT", value = "\"https://api.unsplash.com/\"")
            buildConfigField(type = "String", name = "ACCESS_KEY", value = "\"d0AJ_kOVF_fqN3ngsCow4HpzvQHE-TzwbOp3gtbQyZo\"")
            buildConfigField(type = "String", name = "SECRET_KEY", value = "\"6PNccJVfXQTAmmZv1q_ZFDRdz0gBKFDuo3fUMPpf9t4\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.presentation)

    // Network
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    //DI
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // Room
    implementation(libs.androidx.room)
    ksp(libs.androidx.room.compiler)

    implementation(libs.compose.paging)

    implementation(libs.androidx.appcompat)
}
