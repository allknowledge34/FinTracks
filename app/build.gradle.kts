plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}


android {
    namespace = "com.sachin.fintrack"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sachin.fintrack"
        minSdk = 26
        targetSdk = 35
        versionCode = 5
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.storage)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.github.AnyChart:AnyChart-Android:1.1.5")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation("com.google.android.gms:play-services-ads:24.2.0")
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.3.4")
    implementation("nl.joery.animatedbottombar:library:1.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.google.ai.client.generativeai:generativeai:0.6.0")
    implementation ("com.google.guava:guava:31.0.1-android")
    implementation ("org.reactivestreams:reactive-streams:1.0.4")
    implementation("com.google.android.gms:play-services-auth:21.3.0")

}