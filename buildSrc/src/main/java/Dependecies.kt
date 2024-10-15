object AppDependencies {

    const val composeVersion = Versions.composeVersion
}


object Plugins {

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinParcelize = "kotlin-parcelize"
    const val dagger = "dagger.hilt.android.plugin"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val benManesGradleVersions = "com.github.ben-manes.versions"
    const val ksp = "com.google.devtools.ksp"

    const val serialization = "kotlinx-serialization"
}

private object Versions {
    const val composeVersion = "1.7.2"
}