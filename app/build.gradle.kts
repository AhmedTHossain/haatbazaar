plugins {
    alias(libs.plugins.androidApplication)
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.apptechbd.haatbazaar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apptechbd.haatbazaar"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //imageview library
    implementation(libs.circleimageview)
    //import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    //when using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.auth)
    //implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    // Declare the dependency for the Cloud Firestore library
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.auth)
    //Glide
    implementation(libs.glide)
    //shimmer layout
    implementation(libs.shimmer)
    //Flexbox layout
    implementation(libs.google.flexbox)
    implementation(libs.browser)
    //SpinKit progress view
//    implementation(libs.android.spinkit)
}