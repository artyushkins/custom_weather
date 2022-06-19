val allDependency = listOf(
    DI.all,
    Network.all,
    Kotlin.all,
    Lifecycle.all,
    AndroidX.all,
    Libs.all
)

object DI {
    private const val version = "3.1.6"

    const val koinCore = "io.insert-koin:koin-core:$version"
    const val koinAndroid = "io.insert-koin:koin-android:$version"

    val all = listOf(koinCore, koinAndroid)
}

object Network {
    private const val retrofit_version = "2.9.0"
    private const val gson_version = "2.9.0"
    private const val okhttp_version = "4.9.0"

    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
    const val gson = "com.google.code.gson:gson:$gson_version"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:$gson_version"
    const val okHttp = "com.squareup.okhttp3:okhttp:$okhttp_version"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:$okhttp_version"

    val all = listOf(retrofit, gsonConverter, gson, okHttpLogging, okHttp)
}

object Kotlin {
    private const val coroutineVersion = "1.6.0"

    const val kotlinVersion = "1.5.30"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"

    val all = listOf(coroutinesCore, coroutinesAndroid)
}

object Lifecycle {
    private const val version = "2.4.1"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"

    val all = listOf(viewModel, runtime)
}

object AndroidX {
    const val core = "androidx.core:core-ktx:1.7.0"
    const val appCompat = "androidx.appcompat:appcompat:1.4.1"
    const val material = "com.google.android.material:material:1.5.0-alpha04"

    val all = listOf(core, appCompat, material)
}

object Libs {
    const val dotsIndicator =  "com.tbuonomo:dotsindicator:4.3"

    val all = listOf(dotsIndicator)
}