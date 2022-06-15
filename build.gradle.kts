buildscript {

    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
        maven { setUrl("https://maven.google.com") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Kotlin.kotlinVersion}")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.android.tools.build:gradle:7.0.4")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}