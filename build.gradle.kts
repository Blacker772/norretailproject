import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("androidx.navigation.safeargs") version "2.7.7" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
    kotlin("kapt") version "1.9.22" //1.9.10
    kotlin("jvm") version "1.9.22"
}

val apiServer: String by lazy {
    val properties = Properties()
    file("local.properties").inputStream().use { properties.load(it) }
    properties.getProperty("API_SERVER") ?: ""
}

allprojects {
    ext["apiServer"] = apiServer
}