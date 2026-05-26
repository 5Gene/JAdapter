import june.wing.GroupIdMavenCentral
import june.wing.beijingTimeVersion
import june.wing.publishAndroidMavenCentral

plugins {
    id("com.android.library")
    alias(vcl.plugins.gene.android)
}

group = GroupIdMavenCentral
version = beijingTimeVersion

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
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    defaultConfig {
//          ✅ 消费者规则：只放 keep 规则，❌不允许 将混淆规则打包进发布的AAR中
//          consumerProguardFiles 中的规则会传递给使用该 library 的应用项目。
//          当应用项目构建时，ProGuard/R8 会在自己的构建环境中查找 proguard-dictionary.txt，
//          但这个文件并不存在于应用项目中，导致 File not found 错误。
//          consumerProguardFiles("proguard-rules.pro")
    }

}

publishAndroidMavenCentral("adapter")

dependencies {
    api(vcl.androidx.recyclerview)
    api(vcl.androidx.appcompat)
    api(vcl.androidx.transition.ktx)
}