import june.wing.GroupIdMavenCentral
import june.wing.publishAndroidMavenCentral

plugins {
    id("com.android.library")
    alias(vcl.plugins.gene.android)
}

group = GroupIdMavenCentral
version = libs.versions.gene.adapter.get()

android {
    namespace = "sparkj.adapter"
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    defaultConfig {
        // 将混淆规则打包进发布的AAR中
        consumerProguardFiles("proguard-rules.pro")
    }

}

publishAndroidMavenCentral("adapter")

dependencies {
    api(vcl.androidx.recyclerview)
    api(vcl.androidx.appcompat)
    api(vcl.androidx.transition.ktx)
}