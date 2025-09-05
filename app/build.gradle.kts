import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.addAll(
            listOf(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi"
            )
        )
    }
}

android {
    namespace = "uk.udemy.recordshop"
    compileSdk = 36

    defaultConfig {
        applicationId = "uk.udemy.recordshop"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Load properties
        val properties = Properties()
        val localPropsFile = project.rootProject.file("local.properties")

        if (localPropsFile.exists()) {
            properties.load(localPropsFile.inputStream())
        }

        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField(
            "String",
            "BASE_URL_BACKEND_WIRELESS",
            "\"${properties.getProperty("base.url.backend.wireless")}\""
        )

        resValue(
            "string",
            "wireless_ip",
            properties.getProperty("wireless.ip")
        )

        // Generate network security config during configuration phase
        val templateFile = file("src/main/res/xml/network_security_config_template.xml")
        val targetFile = file("src/main/res/xml/network_security_config.xml")

        if (templateFile.exists() && localPropsFile.exists()) {
            val wirelessIp = properties.getProperty("wireless.ip") ?: "192.168.1.100"
            val content = templateFile.readText().replace("WIRELESS_IP_PLACEHOLDER", wirelessIp)
            targetFile.parentFile.mkdirs()
            targetFile.writeText(content)
            println("✅ Generated network_security_config.xml with IP: $wirelessIp")
        }
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
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    ksp {
        arg("dagger.fastInit", "enabled")
        arg("dagger.hilt.android.internal.disableAndroidSuperclassValidation", "true")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit, GSON & Interceptor
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Compose ViewModel
    implementation(libs.lifecycle.viewmodel.compose)

    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Glide Compose
    implementation(libs.compose.glide)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization)

    //Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.androidx.material.icons.extended)
}