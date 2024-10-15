import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}// Required since Gradle 4.10+.

@Suppress("JcenterRepositoryObsolete")
repositories {
    jcenter()
}