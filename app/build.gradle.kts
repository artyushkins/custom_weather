plugins {
    id("com.android.application")
}

android {

    buildToolsVersion = AppConfig.BUILD_TOOLS_VERSION
    compileSdk = AppConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VERSION
        targetSdk = AppConfig.TARGET_SDK_VERSION
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    allDependency.forEach { dependency ->
        dependency.forEach(::implementation)
    }

    implementation(project(":domain"))
    implementation(project(":data"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
}