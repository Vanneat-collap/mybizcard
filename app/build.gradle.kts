plugins {
    id("com.android.application")
}

android {
    namespace = "com.vannet.mybizcard_rebuild"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.vannet.mybizcard_rebuild"
        minSdk = 24
        targetSdk = 33
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


dependencies {

    implementation(project(path = ":smart_lib"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    /*
      An issue was found when checking AAR metadata: Dependency 'androidx.activity:activity:1.8.0' requires libraries and applications that
      depend on it to compile against version 34 or later of the
      Android APIs.
      implementation("com.google.android.material:material:1.11.0")
      */
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}