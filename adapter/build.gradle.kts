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
}

publishAndroidMavenCentral("adapter")

dependencies {
    api(vcl.androidx.recyclerview)
}