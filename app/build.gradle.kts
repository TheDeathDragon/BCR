import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.text.SimpleDateFormat
import java.util.Date

val apkFileName = "JzhkCallRecorder.apk"

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

android {
    namespace = "la.shiro.call.recorder"

    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "la.shiro.call.recorder"
        minSdk = 28
        targetSdk = 34
        versionCode = 340004
        versionName = "3.4.4"
        resourceConfigurations.addAll(listOf(
            "ar",
            "de",
            "en",
            "es",
            "fr",
            "hi",
            "it",
            "iw",
            "pl",
            "pt-rPT",
            "ru",
            "sk",
            "tr",
            "ur",
            "uk",
            "zh-rCN",
            "zh-rTW",
        ))

        buildConfigField("String", "PROJECT_URL_AT_COMMIT",
            "\"N/A\"")

        buildConfigField("String", "PROVIDER_AUTHORITY",
            "APPLICATION_ID + \".provider\"")
        resValue("string", "provider_authority", "$applicationId.provider")
    }

    signingConfigs {
        create("release") {
            storeFile = file("jzhk.keystore")
            storePassword = "android"
            keyAlias = "android"
            keyPassword = "android"
        }
        getByName("debug") {
            storeFile = file("jzhk.keystore")
            storePassword = "android"
            keyAlias = "android"
            keyPassword = "android"
        }
    }
    buildTypes {
        getByName("debug") {
            buildConfigField("boolean", "FORCE_DEBUG_MODE", "true")
        }

        create("debugOpt") {
            buildConfigField("boolean", "FORCE_DEBUG_MODE", "true")

            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            buildConfigField("boolean", "FORCE_DEBUG_MODE", "false")

            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    lint {
        // The translations are always going to lag behind new strings being
        // added to values/strings.xml
        disable += "MissingTranslation"
    }
    applicationVariants.all {
        outputs.all {
            if (this is ApkVariantOutputImpl) {
                outputFileName = "JzhkCallRecorder.apk"
            }
        }
    }
}

allprojects{
    tasks.register<Zip>("zipReleaseApkAndAssets") {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
        val date = dateFormat.format(Date())
        val outputDir = file("dist")
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }

        if (file("release").exists()) {
            from("release/${apkFileName}") {
                into("JzhkCallRecorder")
            }
        } else {
            from("build/outputs/apk/release/${apkFileName}") {
                into("JzhkCallRecorder")
            }
        }

        from("etc") {
            into("JzhkCallRecorder")
        }
        archiveFileName.set("JzhkCallRecorder_${date}.zip")
        destinationDirectory.set(outputDir)

        doLast {
            println("ZIP file created at: ${outputDir.absolutePath}/${archiveFileName.get()}")
        }
    }

    afterEvaluate {
        tasks.getByName("assembleRelease").finalizedBy("zipReleaseApkAndAssets")
        tasks.getByName("zipReleaseApkAndAssets").dependsOn("assembleRelease")
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.documentfile)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.material)
    implementation(libs.kudzu)
    implementation(libs.activity)
}