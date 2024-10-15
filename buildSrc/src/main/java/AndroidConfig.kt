

object AndroidConfig {
    const val compileSdk = 34
    const val minSdk = 21
    const val targetSdk = 34
    const val detektVersion = "1.19.0"
    const val versionCode = 1
    const val versionName = "1.0"
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
    val isDebuggable: Boolean
    val isShrinkResources: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
    override val isDebuggable = true
    override val isShrinkResources = true
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
    override val isDebuggable = false
    override val isShrinkResources = true
}

