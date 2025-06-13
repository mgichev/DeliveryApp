import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.deliveryapp"
    compileSdk = 35

    var properties = Properties()

    defaultConfig {
        applicationId = "com.example.deliveryapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keystoreFile = project.rootProject.file("api.properties")

        properties.load(keystoreFile.inputStream())

        val apiKeyMapSDK = properties.getProperty("YANDEX_MAP_SDK_API") ?: "NO_SUCH_FILE"
        val apiStaticMap = properties.getProperty("STATIC_MAP_API") ?: "NO_SUCH_FILE"

        buildConfigField(
            type = "String",
            name = "YANDEX_MAP_SDK_API",
            value = apiKeyMapSDK
        )

        buildConfigField(
            type = "String",
            name = "API_STATIC_MAP",
            value = apiStaticMap
        )
    }



    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }



    buildTypes {


        debug {

        }

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
    implementation(libs.firebase.firestore)
    val nav_version = "2.8.9"
    val koin_version = "4.0.2"
    val yandex_map_version = "4.12.0-full"
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("com.yandex.android:maps.mobile:$yandex_map_version")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}