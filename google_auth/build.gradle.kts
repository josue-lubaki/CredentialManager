import com.android.build.api.variant.BuildConfigField
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

androidComponents {
    val properties = Properties()
    val localFile = project.rootProject.file("local.properties")
    if(localFile.exists()){
        localFile.inputStream().use { properties.load(it) }
    }

    val webClientID = System.getenv("WEB_CLIENT_ID") ?: properties.getProperty("WEB_CLIENT_ID")

    onVariants(selector().withBuildType("debug")) {
        it.buildConfigFields.put("WEB_CLIENT_ID",
            BuildConfigField(
                type = "String",
                value = "\"$webClientID\"",
                comment = "WEB_CLIENT_ID"
            )
        )
    }

    onVariants(selector().withBuildType("release")) {
        it.buildConfigFields.put("WEB_CLIENT_ID",
            BuildConfigField(
                type = "String",
                value = "\"$webClientID\"",
                comment = "WEB_CLIENT_ID"
            )
        )
    }
}

android {
    namespace = "ca.josue_lubaki.google_auth"
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.credentials)

    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    implementation(libs.androidx.credentials.play.services.auth)

    implementation(libs.googleid)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
}