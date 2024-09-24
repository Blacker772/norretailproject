import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    base
    `project-report`
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.testapp"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.testapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "MAPKIT_API_KEY", "\"${rootProject.extra["mapkitApiKey"]}\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

}

kapt {
    correctErrorTypes = true
    arguments {
        arg("dagger.hilt.android.internal.disableAndroidSuperclassValidation", "true")
        arg("dagger.hilt.android.internal.projectType", "APP")
    }
}

base {
    archivesName.set("gradle")
    distsDirectory.set(layout.buildDirectory.dir("custom-dist"))
    libsDirectory.set(layout.buildDirectory.dir("custom-libs"))
}
tasks.withType<HtmlDependencyReportTask>() {
    projectReportDirectory.set(project.layout.buildDirectory.dir("reports/custom"))
}

//noinspection UseTomlInstead
dependencies {

    implementation ("com.yandex.android:maps.mobile:4.8.0-full")

    implementation(libs.car.ui.lib)
    //Retrofit
    val retrofitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //Navigation
    val navVersion = "2.8.1"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //lifecycle
    val lifecycleVersion = "2.8.6"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    //Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //Android Core
    val core = "1.13.1"
    implementation("androidx.core:core-ktx:$core")

    //App Combat
    val appCombat = "1.7.0"
    implementation("androidx.appcompat:appcompat:$appCombat")

    //MaterialDesign
    val material = "1.12.0"
    implementation("com.google.android.material:material:$material")

    //ConstraintLayout
    val constraint = "2.1.4"
    implementation("androidx.constraintlayout:constraintlayout:$constraint")

    //Okhttp
    val okhttpVersion = "4.12.0"
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    //Coil
    val coil = "2.7.0"
    implementation("io.coil-kt:coil:$coil")

    //Testing
    val testing = "4.13.2"
    testImplementation("junit:junit:$testing")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //Dagger Hilt
    val dagger = "2.46.1"
    //noinspection GradleDependency
    implementation("com.google.dagger:hilt-android:$dagger")
    //noinspection GradleDependency
    kapt("com.google.dagger:hilt-android-compiler:$dagger")

    //Refresh Layout
    val refresh = "1.1.0"
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:$refresh")

    //LottieAnimation
    val lottieVersion = "6.5.2"
    implementation("com.airbnb.android:lottie:$lottieVersion")

    implementation("com.google.android.material:material:1.12.0")
}