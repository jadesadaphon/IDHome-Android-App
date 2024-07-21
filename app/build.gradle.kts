import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.app.jade.dev.idhome.prasat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.jade.dev.idhome.prasat"
        minSdk = 28
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //load the values from .properties file
        val keystoreFile = project.rootProject.file("hostip.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        //return empty key in case something goes wrong
        val hostPythonIP = properties.getProperty("HOST_PYTHON_API") ?: ""
        buildConfigField(type = "String" , name = "HOST_PYTHON_API" , value = hostPythonIP)

        val hostNodeJsIP = properties.getProperty("HOST_NODEJS_API") ?: ""
        buildConfigField(type = "String" , name = "HOST_NODEJS_API" , value = hostNodeJsIP)

    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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


dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.fragment:fragment-ktx:1.6.2")


    //barcode
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.5.3")
    // okhttp3
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    implementation("com.google.android.material:material:1.11.0")

    implementation("it.xabaras.android:recyclerview-swipedecorator:1.4")

    implementation("com.google.android.play:integrity:1.3.0")

    implementation("org.bitbucket.b_c:jose4j:0.7.0")

    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")


}