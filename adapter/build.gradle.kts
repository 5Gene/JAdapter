import wing.publishMavenCentral

plugins {
    id("com.android.library")
    alias(vcl.plugins.gene.android)
}

group = "io.github.5gene"
version = "1.0"

android {
    namespace = "sparkj.adapter"
}

publishMavenCentral("adapter", withSource = true)

dependencies {
    implementation(vcl.androidx.recyclerview)
}